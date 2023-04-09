package controllers;

import models.Id;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
//    private MessageController msgCtrl;
//    private IdController idCtrl;

    IdController idController = new IdController();
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

    public void postId(String idtoRegister, String githubName) {
        Id tid = new Id("", idtoRegister, githubName); // Todo
       // tid = idCtrl.postId(tid);
        idController.postId(tid);
        //return tid;
    }
}
