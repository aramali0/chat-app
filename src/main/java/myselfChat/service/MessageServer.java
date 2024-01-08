package myselfChat.service;

import myselfChat.dao.MessageRepo;
import myselfChat.entity.Message;
import myselfChat.entity.User;
import myselfChat.serveceInt.MessageServeceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServer implements MessageServeceInter {
    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserService userService;

    public void addMessage(Message message)
    {
        List<User> users = userService.getAll();
        List<User> usersNotBanned = new ArrayList<>();
        users.forEach(user -> {
            if(!user.isBanned()){
                usersNotBanned.add(user);
            }
        });
        message.setUsers(usersNotBanned);
        messageRepo.save(message);
    }

    public Message addMessageandReturn(Message message)
    {
        messageRepo.save(message);

        return message;
    }

    public Message findMessage(Long idMessage)
    {
         Optional<Message> optional = messageRepo.findById(idMessage);
         if(optional.isEmpty())
         {
             throw new RuntimeException("message doesnt existe");
         }

         return optional.get();
    }

    public List<Message> messages()
    {
        return messageRepo.findAll();
    }

    public void delete(Long idMessage)
    {
        messageRepo.delete(this.findMessage(idMessage));
    }



}
