package myselfChat.service;

import myselfChat.dao.UsrRepo;
import myselfChat.entity.Permession;
import myselfChat.entity.User;
import myselfChat.serveceInt.UserServeceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserServeceInterface {

    @Autowired
    UsrRepo usrRepo;

    public void addUser(User user)
    {
        usrRepo.save(user);
    }

    public User getUser(String identifier)
    {
        Optional<User> optional = usrRepo.findById(identifier);
        if(optional.isEmpty())
        {
            throw new RuntimeException("the user doesnt existe");
        }

        return optional.get();
    }

    public List<User> getAll()
    {
        return usrRepo.findAll();
    }

    public void deletUser(String identifier)
    {
        usrRepo.delete(getUser(identifier));
    }

    public User findByUsername(String username)
    {
        return usrRepo.findByUsername(username);
    }

    public User banned(String username) {
        User user = this.findByUsername(username);
        user.setBanned(true);
        this.addUser(user);
        return user;
    }
    public User changePermition(String username,int id) {
        User user = this.findByUsername(username);
        Permession p ;
        if(id == 1)
        {
            p = Permession.administrateur;
        }
        else if (id == 2)
        {
            p = Permession.moderateur;
        }
        else
        {
            p = Permession.normal;
        }
        user.setPermession(p);
        this.addUser(user);
        return user;
    }

}
