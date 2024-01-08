package myselfChat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="sender")
    private User user_id;
    @ManyToMany
    @JoinTable(
            name = "user_message",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users;
    private String content;
    private Date timestamp;

    public Message(User user_id, String content, Date timestamp) {
        this.user_id = user_id;
        this.content = content;
        this.timestamp = timestamp;
    }
}
