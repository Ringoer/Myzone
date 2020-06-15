package xmu.ringoer.myzone.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.message.dao.MessageDao;
import xmu.ringoer.myzone.message.domain.Message;
import xmu.ringoer.myzone.message.util.ResponseUtil;

import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    private Integer getBase(String p) {
        int perPage = 10;

        int page = Integer.parseInt(p) - 1;
        return perPage * page;
    }

    public Object getMessageByUserId(Integer userId, String queryString, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        if(null == queryString) {
            queryString = "";
        }

        try {
            int p = Integer.parseInt(page);
            if(p < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectMessagesByUserId(userId, queryString, getBase(page));
        if (null == messages || messages.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        return ResponseUtil.ok(messages);
    }

    public Object getLatestMessageByUserId(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectLatestMessagesByUserId(userId);
        return ResponseUtil.ok(messages);
    }

    public Object getMessageById(Integer id) {
        if(null == id) {
            return ResponseUtil.badArgument();
        }

        Message message = messageDao.selectMessageById(id);
        if(null == message) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = messageDao.readMessageById(id);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(message);
    }

    public Object postMessage(Integer userId, Message message) {
        if(null == userId || null == message) {
            return ResponseUtil.badArgument();
        }

        if(!userId.equals(message.getFromId())) {
            return ResponseUtil.badArgument();
        }

        System.out.println(message);

        if(!message.isBeRead()) {
            message.setBeRead(false);
        }

        Integer lines = messageDao.insertMessage(message);

        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(message.getId());
    }

    public Object deleteMessage(Integer userId, Integer id) {
        if(null == userId || null == id) {
            return ResponseUtil.badArgument();
        }

        Message message = messageDao.selectMessageById(id);

        if(null == message) {
            return ResponseUtil.badArgumentValue();
        }
        if(!userId.equals(message.getToId())) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = messageDao.deleteMessageById(id);

        if(lines.equals(0)) {
            return ResponseUtil.badArgumentValue();
        }

        return ResponseUtil.ok();
    }

    public Object readMessage(Integer userId, List<Integer> ids) {
        if(null == userId || null == ids) {
            return ResponseUtil.badArgument();
        }

        for (Integer id : ids) {
            Integer lines = messageDao.readMessageById(id);

            if(lines.equals(0)) {
                return ResponseUtil.serious();
            }
        }

        return ResponseUtil.ok();
    }

    public Object deleteMessageByIds(Integer userId, List<Integer> ids) {
        if(null == userId || null == ids) {
            return ResponseUtil.badArgument();
        }

        for(int id : ids) {
            Message message = messageDao.selectMessageById(id);

            if(null == message) {
                return ResponseUtil.badArgumentValue();
            }
            if(!userId.equals(message.getToId())) {
                return ResponseUtil.badArgumentValue();
            }

            Integer lines = messageDao.deleteMessageById(id);

            if(lines.equals(0)) {
                return ResponseUtil.badArgumentValue();
            }
        }

        return ResponseUtil.ok();
    }
}
