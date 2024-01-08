package myselfChat.web;

import myselfChat.entity.User;
import myselfChat.serveceInt.UserServeceInterface;
import myselfChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class UserRestController {

    @Autowired
    private UserServeceInterface userService;


   /* @GetMapping("/user/{identifier}")
    public User getUser( @PathVariable String identifier){
        return userService.getUser(identifier);
    } */

    @GetMapping("/user/{username}")
    public User getUser( @PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/user")
    public String getUset( @RequestBody User user){
         userService.addUser(user);
        return "add user succfully";
    }
    @GetMapping("/user")
    public List<User> getUsers()
    {
        return userService.getAll();
    }

    @GetMapping("/user/banned/{username}")
    public User getBanned(@PathVariable String username)
    {
        return userService.banned(username);
    }

    @GetMapping("/user/{username}/{id}")
    public User changePermition(@PathVariable String username,@PathVariable int id)
    {
        return userService.changePermition(username,id);
    }

 /*   @GetMapping("/users/{identifier}")
    public List<ChatRoom> getRooms(@PathVariable String identifier)
    {
        return userService.findChatRoomsById(identifier);
    }*/
}
