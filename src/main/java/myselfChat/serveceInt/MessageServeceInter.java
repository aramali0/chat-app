package myselfChat.serveceInt;

import myselfChat.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageServeceInter {

    public void addMessage(Message message);
    public Message addMessageandReturn(Message message);
    public Message findMessage(Long idMessage);
    public List<Message> messages();
    public void delete(Long idMessage);

}
