package myselfChat.dao;

import myselfChat.entity.Log;
import myselfChat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepo extends JpaRepository<Log,Long> {
    public List<Log> getByUser_id(Long user_id);
}
