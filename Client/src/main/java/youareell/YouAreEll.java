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

    // Example format is : "post-id
    // "
    public String makecall(String userInput, String param, String param2, String param3) {
        String[] request = userInput.split("-");
        String controller = request[1];
        switch(request[0].toUpperCase()) {
            case "GET":
                if (controller.contains("id")) {
                    List<Id> ids = idController.getIds();
                    return ids.toString();

                }
                else if (controller.contains("messages") && !request[2].contains("byName") && !request[3].contains("toName")) {
                    List<Message> msg = messageController.getMessages();
                    return msg.toString();
                }
                else if (controller.contains("messages") && request[2].contains("byName") && !request[3].contains("toName")) {
                    List<Message> msgByName = transactionController.createMessageObj(param);
                    return msgByName.toString();
                }
                else if (controller.contains("messages") && request[3].contains("toName")) {
                    List<Message> msgLink = transactionController.createMessageObj2(param, param2);
                    return msgLink.toString();
                }

                break;
            case "POST":
                if (controller.contains("id")) {
                    transactionController.createIdObj(param, param2);
                }else if (controller.contains("messages")) {
                    Message msgLink = transactionController.createMessageObj3(param, param2, param3);
                    return msgLink.toString();
                }
                break;
            case "PUT":
                if (controller.contains("id")) {
                    idController.putId(param2, param);
                }
                break;
            default:
                break;
        }
        return controller;
    }

}
