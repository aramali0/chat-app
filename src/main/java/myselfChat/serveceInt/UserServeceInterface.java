package myselfChat.serveceInt;

import myselfChat.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServeceInterface {
    public void addUser(User user);

    public User getUser(String identifier);

    public List<User> getAll();

    public void deletUser(String identifier);

    public User findByUsername(String username);

    public User banned(String username) ;
    public User changePermition(String username,int id);

}
