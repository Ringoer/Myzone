package xmu.ringoer.myzone.message.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.message.dao.MessageDao;
import xmu.ringoer.myzone.message.domain.Message;
import xmu.ringoer.myzone.message.feign.UserService;
import xmu.ringoer.myzone.message.util.JacksonUtil;
import xmu.ringoer.myzone.message.util.ResponseUtil;

import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserService userService;

    private Integer getBase(String p) {
        int perPage = 10;

        int page = Integer.parseInt(p) - 1;
        return perPage * page;
    }

    public Object getMessageByUserId(Integer userId, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        try {
            int p = Integer.parseInt(page);
            if(p < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectMessagesByUserId(userId, getBase(page));
        if (null == messages || messages.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        JSONObject ans = new JSONObject();
        ans.put("data", messages);
        ans.put("count", messageDao.selectMessageCount(userId));

        return ResponseUtil.ok(ans);
    }

    public Object getLatestMessageByUserId(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectLatestMessagesByUserId(userId);
        return ResponseUtil.ok(messages);
    }

    public Object getMessageById(Integer userId, Integer id) {
        if(null == userId || null == id) {
            return ResponseUtil.badArgument();
        }

        Message message = messageDao.selectMessageById(id);
        if(null == message || !userId.equals(message.getToId())) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = messageDao.updateMessageById(id, true);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(message);
    }

    public Object postMessage(Integer userId, Message message, List<Integer> toIds) {
        if(null == userId || null == message || null == toIds || toIds.size() == 0) {
            return ResponseUtil.badArgument();
        }

        if(!userId.equals(message.getFromId())) {
            return ResponseUtil.badArgument();
        }

        if(!message.isBeRead()) {
            message.setBeRead(false);
        }

        String value = "0", key = "errno";

        JSONObject fromUser = JSONObject.parseObject(JacksonUtil.toJson(userService.getInfo(message.getFromId())));
        if(!value.equals(fromUser.getString(key))) {
            return ResponseUtil.badArgument();
        }
        message.setFromUsername(fromUser.getJSONObject("data").getString("username"));
        message.setFromNickname(fromUser.getJSONObject("data").getString("nickname"));

        for(Integer toId : toIds) {
            message.setToId(toId);

            JSONObject toUser = JSONObject.parseObject(JacksonUtil.toJson(userService.getInfo(message.getToId())));

            if(!value.equals(toUser.getString(key))) {
                return ResponseUtil.badArgument();
            }
            message.setToUsername(toUser.getJSONObject("data").getString("username"));
            message.setToNickname(toUser.getJSONObject("data").getString("nickname"));

            Integer lines = messageDao.insertMessage(message);

            if(lines.equals(0)) {
                return ResponseUtil.serious();
            }
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

    public Object readMessage(Integer userId, List<Integer> ids, boolean isRead) {
        if(null == userId || null == ids) {
            return ResponseUtil.badArgument();
        }

        for (Integer id : ids) {
            Integer lines = messageDao.updateMessageById(id, isRead);

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

    public Object getMessageByType(Integer userId, String queryString, String page) {
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

        List<Message> messages = messageDao.selectMessagesByType(userId, queryString, getBase(page));
        if (null == messages || messages.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        JSONObject ans = new JSONObject();
        ans.put("data", messages);
        ans.put("count", messageDao.selectMessageCountByType(userId, queryString));

        return ResponseUtil.ok(ans);
    }

    public Object getMessageByFromId(Integer userId, String queryString, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        try {
            int q = Integer.parseInt(queryString);
            if(q < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        try {
            int p = Integer.parseInt(page);
            if(p < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectMessagesByFromId(userId, Integer.parseInt(queryString), getBase(page));
        if (null == messages || messages.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        JSONObject ans = new JSONObject();
        ans.put("data", messages);
        ans.put("count", messageDao.selectMessageCountByFromId(userId, Integer.parseInt(queryString)));

        return ResponseUtil.ok(ans);
    }

    public Object getMessageByBeRead(Integer userId, String queryString, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        try {
            Boolean b = Boolean.parseBoolean(queryString);
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        try {
            int p = Integer.parseInt(page);
            if(p < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        List<Message> messages = messageDao.selectMessagesByBeRead(userId, Boolean.parseBoolean(queryString), getBase(page));
        if (null == messages || messages.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        JSONObject ans = new JSONObject();
        ans.put("data", messages);
        ans.put("count", messageDao.selectMessageCountByBeRead(userId, Boolean.parseBoolean(queryString)));

        return ResponseUtil.ok(ans);
    }
}
