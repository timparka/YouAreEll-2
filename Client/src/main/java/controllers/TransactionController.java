package controllers;

import models.Id;
import models.Message;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
//    private MessageController msgCtrl;
//    private IdController idCtrl;

    IdController idController = new IdController();
    MessageController msgController = new MessageController();

    public TransactionController() {

    }

//    public TransactionController(MessageController m, IdController j) {
//        msgCtrl = m;
//        idCtrl = j;
//    }

//    public TransactionController(TransactionController transactionController) {
//    }

    public List<Id> getIds() {

        return new ArrayList<Id>();
    }

    public void createIdObj(String idtoRegister, String githubName) {
        Id tid = new Id("", idtoRegister, githubName); // Todo
        // tid = idCtrl.postId(tid);
        idController.postId(tid);
        //return tid;
    }

    public List<Message> createMessageObj(String gitHubId) {
        Id tid = new Id("", "", gitHubId); // Todo
        // tid = idCtrl.postId(tid);
        return msgController.getMessagesForId(tid);
        //return tid;
    }

    public List<Message> createMessageObj2(String gitHubId, String githubId2) {
        Id tid = new Id("", "", gitHubId); // Todo
        Id tid2 = new Id("", "", githubId2); // Todo
        // tid = idCtrl.postId(tid);
        return msgController.getMessagesFromFriend(tid, tid2);
        //return tid;
    }

    public Message createMessageObj3(String gitHubId, String githubId2, String msg) {
        Id tid = new Id("", "", gitHubId); // Todo
        Id tid2 = new Id("", "", githubId2); // Todo
        Message msger = new Message(msg, gitHubId, githubId2, "2023-04-09T23:15:27.096349871Z", "-");
        // tid = idCtrl.postId(tid);
        return msgController.postMessage(tid, tid2, msger);
        //return tid;
    }

    public void putId(String github, String githubName) {
        Id id = new Id("", githubName, github);
        idController.putId(id);
    }
}
