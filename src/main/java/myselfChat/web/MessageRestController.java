package myselfChat.web;

import myselfChat.entity.Message;
import myselfChat.serveceInt.MessageServeceInter;
import myselfChat.service.MessageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class MessageRestController {
    @Autowired
    private MessageServeceInter messageServer;
    @PostMapping("/massage")
    public String addMessage(@RequestBody Message message)
    {
        messageServer.addMessage(message);
        return "save succfully ok";
    }
    @GetMapping("/message/{idMessage}")
    public Message getMessage( @PathVariable Long idMessage)
    {
        Message message = messageServer.findMessage(idMessage);
        return message;
    }

    @GetMapping("/message")
    public List<Message> messages()
    {
        return messageServer.messages();
    }

/*    @GetMapping("/messages/{idChatroom}")
    public List<Message> messages(@PathVariable Long idChatroom)
    {
        ChatRoom chatRoom = chatServer.findRoom(idChatroom);
        return messageServer.messages(chatRoom);
    }*/
}
