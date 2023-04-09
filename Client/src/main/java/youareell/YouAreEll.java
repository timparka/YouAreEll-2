package youareell;

import controllers.IdController;
import controllers.MessageController;
import controllers.TransactionController;
import models.Id;
import models.Message;

import java.util.List;

public class YouAreEll {

    private TransactionController transactionController;
    private IdController idController;
    private MessageController messageController;

    public YouAreEll (TransactionController transactionController,
                      MessageController messageController,
                      IdController idController) {
        this.transactionController = transactionController;
        this.messageController = messageController;
        this.idController = idController;
    }

    // Todo delete?
    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
                new TransactionController(),
                new MessageController(), new IdController()
        );
    }

    // Example format is : "post-id"
    public String makecall(String userInput, String idtoRegister, String githubName) {
        String[] request = userInput.split("-");
        String controller = request[1];
        switch(request[0].toUpperCase()) {
            case "GET":
                if (controller.contains("id")) {
                    List<Id> ids = idController.getIds();
                    return ids.toString();
                } else if (controller.contains("messages")) {
                    List<Message> msg = messageController.getMessages();
                    return msg.toString();
                }
                break;
            case "POST":
                if (controller.contains("id")) {
                    transactionController.postId(idtoRegister, githubName);
                }else if (controller.contains("messages")) {
//                    messageController.postMessage();
                }
                break;
            case "PUT":
                if (controller.contains("id")) {
                    idController.putId(githubName, idtoRegister);
                } else if (controller.contains("messages")) {
                    messageController.getMessages();
                }
                break;
            default:
                break;
        }
        return controller;
    }

}
