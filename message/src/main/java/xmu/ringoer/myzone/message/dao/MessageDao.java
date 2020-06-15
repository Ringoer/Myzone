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

    public List<Message> selectMessagesByUserId(Integer userId, String queryString, Integer base) {
        return messageMapper.selectMessagesByUserId(userId, "%" + queryString + "%", base);
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

    public Integer readMessageById(Integer id) {
        return messageMapper.readMessageById(id);
    }
}
