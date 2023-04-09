package youareell;

import controllers.*;

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
                if (controller.contains("ids")) {
                    idController.getIds();
                } else if (controller.contains("messages")) {
                    messageController.getMessages();
                }
                break;
            case "POST":
                if (controller.contains("post-id")) {
                    transactionController.postId(idtoRegister, githubName);
                }else if (controller.contains("messages")) {
                    messageController.postMessage();
                }
                break;
            default:
                break;
        }
        return controller;
    }

}
