package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Id;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IdController {
    private HashMap<String, Id> allIds;
    private final String rootURL = "http://zipcode.rocks:8085";
    Id myId;

    public List<Id> getIds() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(rootURL + "/ids");
        // httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet); //httpGet has zipcode url stored execute tries to grab a response from it
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody;
        Id[] ids = new Id[0];
        if (statusCode == 200) {
            try {
                responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                ids = objectMapper.readValue(responseBody, Id[].class); // Deserializing
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Process the response body
        } else {
            // Handle the error
        }

        try {
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.stream(ids)
                .collect(Collectors.toList());

    }

    public Id postId(Id id) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(rootURL + "/ids");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(id); //serializer
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200 || statusCode == 201) {
                String responseBody = EntityUtils.toString(response.getEntity());
                id = objectMapper.readValue(responseBody, Id.class);
            } else {
                // Handle the error
            }

            response.close(); // Close the response
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close(); // Close the httpClient
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return id;
    }
    public Id putId(Id id) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut httpPut = new HttpPut(rootURL + "/ids");

        String userid = id.getUserid();
        String github = id.getGithub();
        Id updatedId = null;

        try {
            List<Id> ids = this.getIds();
            for (Id i : ids) {
                if (i.getGithub().equals(github)) {
                    userid = i.getUserid();
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (userid.isEmpty() || userid.equals("")) {
            postId(id);
        } else {
            try {
                Id idToUpdate = getIdByGithub(github);
                if (idToUpdate != null) {
                    idToUpdate.setName(id.getName());
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(idToUpdate);
                    HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                    httpPut.setEntity(entity);

                    CloseableHttpResponse response = httpClient.execute(httpPut);
                    int statusCode = response.getStatusLine().getStatusCode();

                    if (statusCode == 200 || statusCode == 201) {
                        String responseBody = EntityUtils.toString(response.getEntity());
                       updatedId = objectMapper.readValue(responseBody, Id.class);
                    } else {
                        // Handle the error
                    }

                    response.close(); // Close the response
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    httpClient.close(); // Close the httpClient
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            return updatedId;
        }
        return null;
    }


    public Id getIdByGithub(String github) {
        List<Id> ids = getIds();
        for (Id id : ids) {
            if (id.getGithub().equalsIgnoreCase(github)) {
                return id;
            }
        }
        return null;
    }
}