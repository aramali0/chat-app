package myselfChat.serveceInt;

import myselfChat.entity.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogServeceInterface {

    public void addLog(Log log);

    public Log findLog(Long id);

    public List<Log> getLogs();
    public List<Log> getLogsByUser_id(Long user_id);

    public void deleteLog(Long id);
}
