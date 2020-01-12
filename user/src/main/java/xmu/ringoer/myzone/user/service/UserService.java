package xmu.ringoer.myzone.user.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.user.dao.UserDao;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.util.CommonUtil;
import xmu.ringoer.myzone.user.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

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
        return ResponseUtil.ok(resp);
    }

    public Object getInfo(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }
        logger.info("in getInfo userId = " + userId);

        User user = userDao.selectUserById(userId);
        if(null == user) {
            return ResponseUtil.withoutName();
        }

        return ResponseUtil.ok(user);
    }

    public Object register(User registerUser) {
        final Integer lenOfMoblie = 11;

        if(null == registerUser.getUsername()
                || null == registerUser.getPassword()
                || null == registerUser.getMobile()
                || !lenOfMoblie.equals(registerUser.getMobile().length())) {
            return ResponseUtil.badArgument();
        }
        try {
            Long.parseLong(registerUser.getMobile());
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserByUsername(registerUser.getUsername());
        if(null != user) {
            return ResponseUtil.noName();
        }
        user = userDao.selectUserByMobile(registerUser.getMobile());
        if(null != user) {
            return ResponseUtil.wrongMobile();
        }

        user = new User(registerUser.getUsername(), registerUser.getPassword(), registerUser.getMobile());
        logger.info(user.toString());

        Integer lines = userDao.insertUser(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(user);
    }

    public Object putInfo(User putUser) {
        if(null == putUser.getId()
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
            return ResponseUtil.withoutName();
        }

        user.setPassword(putUser.getPassword());

        Integer lines = userDao.updateUserPassword(user);
        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok();
    }

    public Object getCode() {
        final int lenOfCode = 4;
        return ResponseUtil.ok(CommonUtil.getRandomNum(lenOfCode));
    }

    public Object putMobile(User putUser, String code) {
        if(null == putUser.getId() || null == putUser.getMobile()) {
            return ResponseUtil.badArgument();
        }

        User user = userDao.selectUserById(putUser.getId());
        if(null == user) {
            return ResponseUtil.withoutName();
        }

        //TODO 校验验证码

        user.setMobile(putUser.getMobile());

        Integer lines = userDao.updateUserMobile(user);
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
