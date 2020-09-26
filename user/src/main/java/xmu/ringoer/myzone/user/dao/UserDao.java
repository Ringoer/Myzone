package xmu.ringoer.myzone.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ringoer
 */
@Repository
public class UserDao {

    private static List<String> usernames;

    private static Map<String, String> verifyCode = new HashMap<>();
    private static Map<String, Long> timestamp = new HashMap<>();

    @Autowired
    private UserMapper userMapper;

    private void initUsernames() {
        List<User> users = userMapper.selectUsers();
        usernames = new ArrayList<>();
        for(User user : users) {
            usernames.add(user.getUsername());
        }
    }

    public Integer selectUserCount() {
        return userMapper.selectUserCount();
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    public User selectDisabledUserByEmail(String email) {
        return userMapper.selectDisabledUserByEmail(email);
    }

    public User selectAllUserByEmail(String email) {
        return userMapper.selectAllUserByEmail(email);
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

    public Integer updateUserEmail(User user) {
        return userMapper.updateUserEmail(user);
    }

    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

    public void insertVerifyCode(String email, String code, long second) {
        verifyCode.put(email, code);
        timestamp.put(email, second);
    }

    public String selectVerifyCodeByEmail(String email) {
        Long ts = timestamp.get(email);
        if(null == ts || ts + 300 < LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))) {
            return null;
        }
        return verifyCode.get(email);
    }

    /**
     * 每隔10秒执行一次
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void refreshMap() {
        for(String key : verifyCode.keySet()) {
            Long ts = timestamp.get(key);
            if(null == ts) {
                verifyCode.remove(key);
            } else if(ts + 300 < LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))) {
                verifyCode.remove(key);
                timestamp.remove(key);
            }
        }
    }

    public boolean isUsernameUsed(String username) {
        if(null == usernames) {
            initUsernames();
        }
        return usernames.contains(username);
    }

    public void updateUsernames(String username) {
        usernames.add(username);
    }

    public Integer enableUser(User user) {
        return userMapper.enableUser(user);
    }

    public Integer disableUser(User user) {
        return userMapper.disableUser(user);
    }
}
