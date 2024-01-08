package myselfChat.dao;

import myselfChat.entity.Message;
import myselfChat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Long> {
    //public List<Message> findMessageByChatRoom(ChatRoom chatRoom);
}
