package rating.service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

	private static LocalTime nextTry;
	private final Logger log = LoggerFactory.getLogger(RatingService.class);

	@Value("${rating.service.url}")
	private String ratingServiceUrl;
	@Value("${rating.service.await}")
	private long await;
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public RatingService() {
	}

	public int userRating(int userId) {
		int rating;
		if (isServiceAvailable()) {
			// request service for rating by userId
			rating = 1337;
		} else {
			rating = 0;
		}
		return rating;
	}

	private boolean isServiceAvailable() {
		boolean available = false;
		if (nextTry == null || nextTry.isBefore(LocalTime.now())) {
			HttpGet request = new HttpGet(ratingServiceUrl);
			try (CloseableHttpResponse response = httpClient.execute(request)) {
				available = response.getStatusLine().getStatusCode() % 200 < 100;
			} catch (IOException e) {
				log.info("Rating service currently unavailable");
				log.debug("Error when ping Rating service ", e);
				nextTry = LocalTime.now().plus(await, ChronoUnit.MILLIS);
			}
		}
		return available;
	}
}
