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
    public TransactionController(){

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
}
