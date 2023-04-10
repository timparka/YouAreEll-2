package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Id;
import models.Message;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MessageController {

    private HashSet<Message> messagesSeen;
    // why a HashSet??
    private final String rootURL = "http://zipcode.rocks:8085";
    Message myMsg;

    public List<Message> getMessages() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(rootURL + "/messages");
        // httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet); //httpGet has zipcode url stored execute tries to grab a response from it
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody;
        Message[] msg = new Message[0];
        if (statusCode == 200) {
            try {
                responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                msg = objectMapper.readValue(responseBody, Message[].class); // Deserializing
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

        return Arrays.stream(msg)
                .collect(Collectors.toList());

    }

    public List<Message> getMessagesForId(Id id) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(rootURL + "/ids/" + id.getGithub() + "/messages");
        // httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet); //httpGet has zipcode url stored execute tries to grab a response from it
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody;
        Message[] msg = new Message[0];
        if (statusCode == 200) {
            try {
                responseBody = EntityUtils.toString(response.getEntity());
                if (responseBody.isEmpty()) {
                    return Collections.emptyList();
                }
                ObjectMapper objectMapper = new ObjectMapper();
                msg = objectMapper.readValue(responseBody, Message[].class); // Deserializing
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

        return Arrays.stream(msg)
                .collect(Collectors.toList());
    }

    public Message getMessageForSequence(String seq) {
        return null;
    }

    public List<Message> getMessagesFromFriend(Id myId, Id friendId) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(rootURL + "/ids/" + myId.getGithub() + "/from/" + friendId.getGithub());
        // httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet); //httpGet has zipcode url stored execute tries to grab a response from it
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody;
        Message[] msg = new Message[0];
        if (statusCode == 200) {
            try {
                responseBody = EntityUtils.toString(response.getEntity());
                if (responseBody.isEmpty()) {
                    return Collections.emptyList();
                }
                ObjectMapper objectMapper = new ObjectMapper();
                msg = objectMapper.readValue(responseBody, Message[].class); // Deserializing
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

        return Arrays.stream(msg)
                .collect(Collectors.toList());
    }

    public Message postMessage(Id myId, Id toId, Message message) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(rootURL + "/ids/" + myId.getGithub() + "/messages");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200 || statusCode == 201) {
                String responseBody = EntityUtils.toString(response.getEntity());
                message = objectMapper.readValue(responseBody, Message.class);
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

        return message;
    }

}
