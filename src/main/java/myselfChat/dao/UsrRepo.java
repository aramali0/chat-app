package myselfChat.dao;

import myselfChat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrRepo extends JpaRepository<User,String> {

    /*@Query("select u.chatRooms from User u where u.identifier = :x")
    public List<ChatRoom> findChatRoomsByIdentifier(@Param("x") String ideString);*/
    public User findByUsername(String username);
}
