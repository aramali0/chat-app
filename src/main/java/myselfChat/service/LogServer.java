package myselfChat.service;

import myselfChat.dao.LogRepo;
import myselfChat.entity.Log;
import myselfChat.entity.User;
import myselfChat.serveceInt.LogServeceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LogServer implements LogServeceInterface {

    @Autowired
    LogRepo logRepo;

    public void addLog(Log log)
    {
        logRepo.save(log);
    }

    public Log findLog(Long id)
    {
         Optional<Log> optional =  logRepo.findById(id);
         if(optional.isEmpty())
         {
             throw new RuntimeException("chat room doesnt existe");

         }

         return optional.get();
    }

    public List<Log> getLogs()
    {
        return logRepo.findAll();
    }
    public List<Log> getLogsByUser_id(Long user_id)
    {
        return logRepo.getByUser_id(user_id);
    }

   public void deleteLog(Long id)
   {
       logRepo.delete(this.findLog(id));
   }

}
