package xmu.ringoer.myzone.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.ringoer.myzone.user.domain.User;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface UserMapper {

    /**
     * 根据username查找user
     * @param username 用户名
     * @return 对应的用户，若找不到则为空
     */
    User selectUserByUsername(String username);

    /**
     * 根据id查找user
     * @param id 用户id
     * @return 对应的用户，若找不到则为空
     */
    User selectUserById(Integer id);

    /**
     * 根据mobile查找user
     * @param email 用户邮件
     * @return 对应的用户，若找不到则为空
     */
    User selectUserByEmail(String email);

    /**
     * 新建一条user记录
     * @param user 新用户
     * @return 行数
     */
    Integer insertUser(User user);

    /**
     * 更新一条user记录
     * @param user 用户
     * @return 行数
     */
    Integer updateUser(User user);

    /**
     * 修改密码
     * @param user 只有密码字段的user记录
     * @return 行数
     */
    Integer updateUserPassword(User user);

    /**
     * 修改手机号
     * @param user 只有手机号字段的user记录
     * @return 行数
     */
    Integer updateUserEmail(User user);

    /**
     * 查询用户列表
     * @return 用户列表
     */
    List<User> selectUsers();
}
