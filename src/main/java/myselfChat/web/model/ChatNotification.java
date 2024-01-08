package myselfChat.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myselfChat.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private Long idChat;
    private User user_id;
    private String content;
    private Date timestamp;
}
