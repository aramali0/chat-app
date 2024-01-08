package myselfChat.web;


import myselfChat.entity.Log;
import myselfChat.serveceInt.LogServeceInterface;
import myselfChat.service.LogServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class LogRestController {

    @Autowired
    private LogServeceInterface logServer;

    @GetMapping("/log/{id}")
    public Log getLog(@PathVariable Long id) {
        return logServer.findLog(id);
    }

    @PostMapping("/log")
    public String getUset(@RequestBody Log log) {
        System.out.println(log);
        logServer.addLog(log);
        return "add user succfully";
    }

    @GetMapping("/log")
    public List<Log> getLogs() {
        return logServer.getLogs();
    }

    @GetMapping("/logs/{user_id}")
    public List<Log> getLogsByUser_id(@PathVariable Long user_id){
        return logServer.getLogsByUser_id(user_id);
    }

}