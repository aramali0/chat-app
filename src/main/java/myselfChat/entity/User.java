package myselfChat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Permession permession;
    private Date last_connection_time;
    private boolean isBanned;

    public User(String username, String first_name, String last_name, String email, String password,
                Permession permession, Date last_connection_time, boolean isBanned) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.permession = permession;
        this.last_connection_time = last_connection_time;
        this.isBanned = isBanned;
    }

   /* @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<ChatRoom> chatRooms;*/

}
