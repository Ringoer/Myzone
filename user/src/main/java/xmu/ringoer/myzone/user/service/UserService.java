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
import xmu.ringoer.myzone.user.util.VerifyCodeUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Object register(User registerUser) {
        if(null == registerUser.getPassword()
                || null == registerUser.getEmail()
                || !registerUser.getEmail().matches(EMAIL_REGEX)) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectAllUserByEmail(registerUser.getEmail());
        if(null != user) {
            return ResponseUtil.wrongEmail();
        }

        user = userDao.selectUserByUsername(registerUser.getUsername());
        if(null != user) {
            return ResponseUtil.wrongUsername();
        }

        user = new User(registerUser.getUsername(), registerUser.getPassword(), registerUser.getEmail());
        logger.info("registering user = " + user.toString());

        String text = "亲爱的 "+ registerUser.getUsername() + "：<br><br>您正在注册 Myzone 用户。<br><br>您的注册链接是：<br><br>http://zone.ringoer.com/api/user/register/verify?code=" + VerifyCodeUtil.createVerifyCode(registerUser.getUsername(), registerUser.getPassword(), registerUser.getEmail(), "register") + "<br><br>您的注册链接有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
        EmailUtil.sendMail(registerUser.getEmail(), text, "【Myzone】欢迎注册 Myzone");

        Integer lines = userDao.insertUser(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        lines = userDao.disableUser(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok();
    }

    public Object registerVerify(String verifyCode) {
        Map<String, String> verifyData = VerifyCodeUtil.parseVerifyCode(verifyCode);
        if(!checkVerifyData(verifyData) || !"register".equals(verifyData.get("action"))) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByEmail(verifyData.get("email"));
        if(user != null) {
            return ResponseUtil.wrongEmail();
        }

        user = userDao.selectDisabledUserByEmail(verifyData.get("email"));
        if (user == null
                || !user.getUsername().equals(verifyData.get("username"))
                || !user.getPassword().equals(verifyData.get("password"))) {
            return ResponseUtil.badArgument();
        }

        Integer lines = userDao.enableUser(user);
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

    public Object putPassword(User putUser) {
        if(null == putUser.getId() || null == putUser.getPassword()) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserById(putUser.getId());
        if(null == user) {
            return ResponseUtil.badArgument();
        }

        String text = "亲爱的 "+ user.getUsername() + "：<br><br>您正在修改您在 Myzone 的密码。<br><br>您的修改密码链接是：<br><br>http://zone.ringoer.com/api/user/password/verify?code=" + VerifyCodeUtil.createVerifyCode(user.getUsername(), putUser.getPassword(), user.getEmail(), "password") + "<br><br>您的修改密码链接有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
        EmailUtil.sendMail(user.getEmail(), text, "【Myzone】修改您在 Myzone 的密码");

        return ResponseUtil.ok();
    }

    public Object putPasswordVerify(String verifyCode) {
        Map<String, String> verifyData = VerifyCodeUtil.parseVerifyCode(verifyCode);
        if(!checkVerifyData(verifyData) || !"password".equals(verifyData.get("action"))) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByEmail(verifyData.get("email"));
        if(user == null) {
            return ResponseUtil.badArgument();
        }

        user.setPassword(verifyData.get("password"));

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
        EmailUtil.sendMail(email, text, "【Myzone】来自 Myzone 的验证码");

        return ResponseUtil.ok();
    }

    public Object putEmail(User putUser) {
        if(null == putUser.getId() || null == putUser.getEmail() || !putUser.getEmail().matches(EMAIL_REGEX)) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByEmail(putUser.getEmail());
        if(null != user) {
            return ResponseUtil.wrongEmail();
        }

        user = userDao.selectUserByUsername(putUser.getUsername());
        if(null == user) {
            return ResponseUtil.badArgument();
        }

        String text = "亲爱的 "+ user.getUsername() + "：<br><br>您正在修改您在 Myzone 的邮箱。<br><br>您的修改邮箱链接是：<br><br>http://zone.ringoer.com/api/user/email/verify?code=" + VerifyCodeUtil.createVerifyCode(user.getUsername(), user.getPassword(), putUser.getEmail(), "oldEmail") + "<br><br>您的修改邮箱链接有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
        EmailUtil.sendMail(user.getEmail(), text, "【Myzone】修改您在 Myzone 的邮箱");

        return ResponseUtil.ok();
    }

    public Object putEmailVerify(String verifyCode) {
        Map<String, String> verifyData = VerifyCodeUtil.parseVerifyCode(verifyCode);
        if(!checkVerifyData(verifyData)) {
            return ResponseUtil.badArgument();
        }

        if("oldEmail".equals(verifyData.get("action"))) {
            User user = userDao.selectUserByEmail(verifyData.get("email"));
            if(null != user) {
                return ResponseUtil.wrongEmail();
            }

            user = userDao.selectUserByUsername(verifyData.get("username"));
            if(user == null) {
                return ResponseUtil.badArgument();
            }

            String text = "亲爱的 "+ user.getUsername() + "：<br><br>您正在验证您在 Myzone 的邮箱。<br><br>您的验证邮箱链接是：<br><br>http://zone.ringoer.com/api/user/email/verify?code=" + VerifyCodeUtil.createVerifyCode(user.getUsername(), user.getPassword(), verifyData.get("email"), "newEmail") + "<br><br>您的验证邮箱链接有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
            EmailUtil.sendMail(verifyData.get("email"), text, "【Myzone】验证您在 Myzone 的邮箱");

        } else if("newEmail".equals(verifyData.get("action"))) {
            User user = userDao.selectUserByEmail(verifyData.get("email"));
            if(null != user) {
                return ResponseUtil.wrongEmail();
            }

            user = userDao.selectUserByUsername(verifyData.get("username"));
            if(user == null) {
                return ResponseUtil.badArgument();
            }

            user.setEmail(verifyData.get("email"));

            Integer lines = userDao.updateUserEmail(user);
            if(lines.equals(0)) {
                return ResponseUtil.serious();
            }
        } else {
            return ResponseUtil.badArgument();
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

        JSONObject ans = new JSONObject();
        ans.put("data", users.subList(floor, ceil));
        ans.put("count", userDao.selectUserCount());
        return ResponseUtil.ok(ans);
    }

    private boolean checkVerifyData(Map<String, String> verifyData) {
        return verifyData.keySet().contains("action")
                && verifyData.keySet().contains("username") && verifyData.keySet().contains("password") && verifyData.keySet().contains("email");
    }
}
