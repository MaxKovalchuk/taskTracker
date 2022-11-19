package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import services.model.EstimatorRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class EstimatorService {

    @Value("${estimator.url}")
    private String url;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public EstimatorService() {
    }

    public void estimate(
            String text,
            int complexity
    ) {
        EstimatorRequest estimatorRequest = new EstimatorRequest(text, complexity);
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writer().writeValueAsString(estimatorRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPut.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        httpPut.setEntity(entity);

        try(CloseableHttpResponse response = httpClient.execute(httpPut)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
