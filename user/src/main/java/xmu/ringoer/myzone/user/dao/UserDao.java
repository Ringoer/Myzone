package xmu.ringoer.myzone.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.mapper.UserMapper;

import java.util.List;

/**
 * @author Ringoer
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User selectUserByMobile(String mobile) {
        return userMapper.selectUserByMobile(mobile);
    }

    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public Integer updateUserPassword(User user) {
        return userMapper.updateUserPassword(user);
    }

    public Integer updateUserMobile(User user) {
        return userMapper.updateUserMobile(user);
    }

    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }
}
