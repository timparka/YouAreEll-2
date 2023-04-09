package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Id;
import models.Message;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
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
    public ArrayList<Message> getMessagesForId(Id Id) {
        return null;
    }
    public Message getMessageForSequence(String seq) {
        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        return null;
    }
 
}