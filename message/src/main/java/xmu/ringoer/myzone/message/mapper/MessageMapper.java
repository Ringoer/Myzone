package xmu.ringoer.myzone.message.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xmu.ringoer.myzone.message.domain.Message;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface MessageMapper {

    /**
     * 根据userId和类型查找消息
     * @param userId 用户id
     * @param base 下界
     * @return 消息列表
     */
    List<Message> selectMessagesByUserId(@Param("userId") Integer userId, @Param("base") Integer base);

    /**
     * 插入新消息
     * @param message 消息信息体
     * @return 行数
     */
    Integer insertMessage(Message message);

    /**
     * 根据消息id删除消息
     * @param id 消息id
     * @return 行数
     */
    Integer deleteMessageById(Integer id);

    /**
     * 根据消息id查找消息
     * @param id 消息id
     * @return 消息信息
     */
    Message selectMessageById(Integer id);

    /**
     * 获取最新的5条消息
     * @param userId 用户id
     * @return 消息列表
     */
    List<Message> selectLatestMessagesByUserId(Integer userId);

    /**
     * 设置消息已读
     * @param id 消息id
     * @param isRead 是否已读
     * @return 行数
     */
    Integer updateMessageById(@Param("id") Integer id, @Param("isRead") boolean isRead);

    /**
     * 根据userId和类型查找消息
     * @param userId 用户id
     * @param type 类型
     * @param base 下界
     * @return 消息列表
     */
    List<Message> selectMessagesByType(@Param("userId") Integer userId, @Param("type") String type, @Param("base") Integer base);

    /**
     * 根据userId和类型查找消息
     * @param userId 用户id
     * @param fromId 发件人id
     * @param base 下界
     * @return 消息列表
     */
    List<Message> selectMessagesByFromId(@Param("userId") Integer userId, @Param("fromId") Integer fromId, @Param("base") Integer base);

    /**
     * 根据userId和类型查找消息
     * @param userId 用户id
     * @param beRead 是否已读
     * @param base 下界
     * @return 消息列表
     */
    List<Message> selectMessagesByBeRead(@Param("userId") Integer userId, @Param("beRead") Boolean beRead,@Param("base")  Integer base);
}
