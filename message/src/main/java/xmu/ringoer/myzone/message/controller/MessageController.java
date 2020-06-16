package xmu.ringoer.myzone.message.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.message.domain.Message;
import xmu.ringoer.myzone.message.service.MessageService;

import java.util.List;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public Object getMessageByUserId(@RequestHeader Integer userId, @RequestParam String q, @RequestParam String p) {
        return messageService.getMessageByUserId(userId, q, p);
    }

    @GetMapping("/latest")
    public Object getLatestMessageByUserId(@RequestHeader Integer userId) {
        return messageService.getLatestMessageByUserId(userId);
    }

    @GetMapping("/{id}")
    public Object getMessageById(@RequestHeader Integer userId, @PathVariable Integer id) {
        return messageService.getMessageById(userId, id);
    }

    @PostMapping("/")
    public Object postMessage(@RequestHeader Integer userId, @RequestBody JSONObject data) {
        return messageService.postMessage(userId, new Message(data.getJSONObject("message")), (List<Integer>)data.get("ids"));
    }

    @PutMapping("/")
    public Object putMessage(@RequestHeader Integer userId, @RequestBody JSONObject data) {
        return messageService.readMessage(userId, (List<Integer>)data.get("ids"), data.getBooleanValue("isRead"));
    }

    @DeleteMapping("/")
    public Object deleteMessage(@RequestHeader Integer userId, @RequestBody Message message) {
        return messageService.deleteMessage(userId, message.getId());
    }

    @DeleteMapping("/all")
    public Object deleteMessageByIds(@RequestHeader Integer userId, @RequestBody List<Integer> ids) {
        return messageService.deleteMessageByIds(userId, ids);
    }
}
