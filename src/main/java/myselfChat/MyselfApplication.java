package myselfChat;

import myselfChat.entity.Message;
import myselfChat.entity.Permession;
import myselfChat.entity.User;
import myselfChat.service.MessageServer;
import myselfChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class MyselfApplication implements CommandLineRunner {
	@Autowired
	UserService userService;

	@Autowired
	MessageServer messageServer;


	public static void main(String[] args) {
		SpringApplication.run(MyselfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user1 = new User("mohammed","mohammed","aramali","email.com","123", Permession.normal,new Date(),false);
		User user2 = new User("hamza","hamza","aramali","email.com","123", Permession.normal,new Date(),false);
		User user3 = new User("youssef","youssef","aramali","email.com","123", Permession.moderateur,new Date(),false);
		User user4 = new User("hassan","hassan","aramali","email.com","123", Permession.administrateur,new Date(),false);


		userService.addUser(user4);
		userService.addUser(user1);
		userService.addUser(user2);
		userService.addUser(user3);


		Message message1 = new Message(user1,"bonjour",new Date());
		Message message2 = new Message(user2,"salut",new Date());


		messageServer.addMessage(message1);
		messageServer.addMessage(message2);

	}
}
