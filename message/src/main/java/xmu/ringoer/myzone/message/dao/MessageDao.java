package xmu.ringoer.myzone.message.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.message.domain.Message;
import xmu.ringoer.myzone.message.mapper.MessageMapper;

import java.util.List;

/**
 * @author Ringoer
 */
@Repository
public class MessageDao {

    @Autowired
    private MessageMapper messageMapper;

    public Integer selectMessageCount(Integer userId) {
        return messageMapper.selectMessageCount(userId);
    }

    public List<Message> selectMessagesByUserId(Integer userId, Integer base) {
        return messageMapper.selectMessagesByUserId(userId, base);
    }

    public Integer insertMessage(Message course) {
        return messageMapper.insertMessage(course);
    }

    public Integer deleteMessageById(Integer id) {
        return messageMapper.deleteMessageById(id);
    }

    public Message selectMessageById(Integer id) {
        return messageMapper.selectMessageById(id);
    }

    public List<Message> selectLatestMessagesByUserId(Integer userId) {
        return messageMapper.selectLatestMessagesByUserId(userId);
    }

    public Integer updateMessageById(Integer id, boolean isRead) {
        return messageMapper.updateMessageById(id, isRead);
    }

    public Integer selectMessageCountByType(Integer userId, String type) {
        return messageMapper.selectMessageCountByType(userId, type);
    }

    public List<Message> selectMessagesByType(Integer userId, String type, Integer base) {
        return messageMapper.selectMessagesByType(userId, "%" + type + "%", base);
    }

    public Integer selectMessageCountByFromId(Integer userId, Integer fromId) {
        return messageMapper.selectMessageCountByFromId(userId, fromId);
    }

    public List<Message> selectMessagesByFromId(Integer userId, Integer fromId, Integer base) {
        return messageMapper.selectMessagesByFromId(userId, fromId, base);
    }

    public Integer selectMessageCountByBeRead(Integer userId, Boolean beRead) {
        return messageMapper.selectMessageCountByBeRead(userId, beRead);
    }

    public List<Message> selectMessagesByBeRead(Integer userId, Boolean beRead, Integer base) {
        return messageMapper.selectMessagesByBeRead(userId, beRead, base);
    }
}
