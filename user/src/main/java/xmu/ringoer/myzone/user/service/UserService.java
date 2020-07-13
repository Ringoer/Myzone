package xmu.ringoer.myzone.user.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.user.dao.UserDao;
import xmu.ringoer.myzone.user.domain.Message;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.feign.MessageService;
import xmu.ringoer.myzone.user.util.CommonUtil;
import xmu.ringoer.myzone.user.util.EmailUtil;
import xmu.ringoer.myzone.user.util.ResponseUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ringoer
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageService messageService;

    public Object login(User loginUser) {
        if(null == loginUser || null == loginUser.getUsername() || null == loginUser.getPassword()) {
            return ResponseUtil.badArgument();
        }

        logger.info("loginUser = " + loginUser.toString());

        User user = userDao.selectUserByUsername(loginUser.getUsername());
        if(null == user) {
            return ResponseUtil.withoutName();
        }
        if(!loginUser.getPassword().equals(user.getPassword())) {
            return ResponseUtil.wrongPassword();
        }

        JSONObject resp = new JSONObject();
        resp.put("userId", user.getId());
        resp.put("roleId", user.getRoleId());

        JSONObject message = new JSONObject();
        message.put("message", new Message("欢迎登录！", "本次登录时间为 " + LocalDateTime.now().toString(), "系统", true, 0, user.getId()));
        List<Integer> queryArray = new ArrayList<>();
        queryArray.add(user.getId());
        message.put("ids", queryArray);

        try {
            messageService.postMessage(0, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(resp);
    }

    public Object getInfo(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }
        logger.info("in getInfo userId = " + userId);

        User user;
        if(userId == 0) {
            user = new User("system", "系统", "233", "18000000000");
        } else {
            user = userDao.selectUserById(userId);
            if(null == user) {
                return ResponseUtil.badArgumentValue();
            }
        }

        return ResponseUtil.ok(user);
    }

    public Object register(User registerUser, String code) {
        if(null == registerUser.getNickname()
                || null == registerUser.getPassword()
                || null == registerUser.getEmail()
                || !registerUser.getEmail().matches(EMAIL_REGEX)
                || null == code) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByEmail(registerUser.getEmail());
        if(null != user) {
            return ResponseUtil.wrongEmail();
        }
        String verifyCode = userDao.selectVerifyCodeByEmail(registerUser.getEmail());
        if(!code.equals(verifyCode)) {
            return ResponseUtil.badArgument();
        }

        String username = CommonUtil.getRandomNum(9);
        while(userDao.isUsernameUsed(username)) {
            username = CommonUtil.getRandomNum(9);
        }
        userDao.updateUsernames(username);

        user = new User(username, registerUser.getNickname(), registerUser.getPassword(), registerUser.getEmail());
        logger.info("registering user = " + user.toString());

        Integer lines = userDao.insertUser(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(user);
    }

    public Object putInfo(User putUser) {
        if(null == putUser.getId()
                || null == putUser.getGender()
                || putUser.getGender() < 0
                || putUser.getGender() > 2) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserById(putUser.getId());
        if(null == user) {
            return ResponseUtil.withoutName();
        }

        if(null != putUser.getNickname()) {
            user.setNickname(putUser.getNickname());
        }
        if(null != putUser.getAvatar()) {
            user.setAvatar(putUser.getAvatar());
        }
        if(null != putUser.getSignature()) {
            user.setSignature(putUser.getSignature());
        }
        if(null != putUser.getGender()) {
            user.setGender(putUser.getGender());
        }
        if(null != putUser.getBirthday()) {
            user.setBirthday(putUser.getBirthday());
        }

        Integer lines = userDao.updateUser(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok();
    }

    public Object putPassword(User putUser, String code) {
        if(null == putUser.getId() || null == putUser.getPassword() || null == code) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserById(putUser.getId());
        if(null == user) {
            return ResponseUtil.withoutName();
        }
        String verifyCode = userDao.selectVerifyCodeByEmail(putUser.getEmail());
        if(!code.equals(verifyCode)) {
            return ResponseUtil.badArgument();
        }

        user.setPassword(putUser.getPassword());

        Integer lines = userDao.updateUserPassword(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok();
    }

    public Object getCode(String email) {
        if(null == email || !email.matches(EMAIL_REGEX)) {
            return ResponseUtil.badArgument();
        }

        final int lenOfCode = 4;
        String code = CommonUtil.getRandomNum(lenOfCode);

        userDao.insertVerifyCode(email, code, LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));

        String text = "您正在申请来自 Myzone 的验证码。<br><br>您的验证码是：<br><br>" + code + "<br><br>您的验证码有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
        EmailUtil.sendMail(email, text, "来自 Myzone 的验证码");

        return ResponseUtil.ok();
    }

    public Object putEmail(User putUser, String code) {
        if(null == putUser.getId() || null == putUser.getEmail() || !putUser.getEmail().matches(EMAIL_REGEX) || null == code) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByEmail(putUser.getEmail());
        if(null != user) {
            return ResponseUtil.wrongEmail();
        }
        user = userDao.selectUserById(putUser.getId());
        if(null == user) {
            return ResponseUtil.withoutName();
        }

        String verifyCode = userDao.selectVerifyCodeByEmail(putUser.getEmail());
        if(!code.equals(verifyCode)) {
            return ResponseUtil.badArgument();
        }

        user.setEmail(putUser.getEmail());

        Integer lines = userDao.updateUserEmail(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok();
    }

    public Object getUsers(Integer page, Integer size) {
        if(null == page
                || null == size
                || page < 1
                || size < 1) {
            return ResponseUtil.badArgument();
        }

        List<User> users = userDao.selectUsers();

        if(null == users) {
            return ResponseUtil.withoutName();
        }

        int floor = (page - 1) * size;
        int ceil = page * size;

        if(users.size() <= floor) {
            return ResponseUtil.fail(ResponseUtil.BAD_ARGUMENT, "页数越界！");
        }

        if(users.size() < ceil) {
            ceil = users.size();
        }

        return ResponseUtil.ok(users.subList(floor, ceil));
    }
}
