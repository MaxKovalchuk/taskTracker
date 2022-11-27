package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import services.model.EstimatorRequest;
import services.model.EstimatorResponse;
import services.model.SaveKeyRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class EstimatorService {

    @Value("${estimator.url}")
    private String url;
    @Value("${estimator.api.save-key}")
    private String saveKeyPath;
    @Value("${estimator.api.estimate}")
    private String estimatePath;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public EstimatorService() {
    }

    public int estimate(
            String title,
            String description
    ) throws IOException {
        EstimatorRequest estimatorRequest = new EstimatorRequest(title, description);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writer().writeValueAsString(estimatorRequest);

        HttpPost httpPost = new HttpPost(url + estimatePath);
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        StringEntity requestEntity = new StringEntity(json);
        httpPost.setEntity(requestEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseJson = EntityUtils.toString(responseEntity);
        EstimatorResponse estimatorResponse = objectMapper.readValue(
                responseJson,
                EstimatorResponse.class
        );
        return estimatorResponse.getEstimate();
    }

    public void saveKey(
            String text,
            int complexity
    ) {
        SaveKeyRequest saveKeyRequest = new SaveKeyRequest(text, complexity);
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writer().writeValueAsString(saveKeyRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        HttpPut httpPut = new HttpPut(url + saveKeyPath);
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

        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
