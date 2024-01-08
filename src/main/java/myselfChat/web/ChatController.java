package myselfChat.web;

import myselfChat.entity.Message;
import myselfChat.serveceInt.MessageServeceInter;
import myselfChat.service.MessageServer;
import myselfChat.web.model.ChatNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageServeceInter messageServer;



    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        try {
            Message savedMsg = messageServer.addMessageandReturn(chatMessage);
            System.out.println("Received message: " + chatMessage);
            messagingTemplate.convertAndSendToUser(
                    "1", "/queue/messages",
                    new ChatNotification(
                            savedMsg.getId(),
                            savedMsg.getUser_id(),
                            savedMsg.getContent(),
                            savedMsg.getTimestamp()
                    )
            );
        } catch (Exception e) {
            System.out.println("error when hadling the message received ");
            e.printStackTrace();
        }
    }

}
