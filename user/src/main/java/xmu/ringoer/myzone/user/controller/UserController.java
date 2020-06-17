package xmu.ringoer.myzone.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.service.UserService;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "/api", produces = {"application/json;charset=UTF-8"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody(required = false) User user) {
        return userService.login(user);
    }

    @GetMapping("/info")
    public Object getInfo(@RequestHeader Integer userId) {
        return userService.getInfo(userId);
    }

    @PostMapping("/register")
    public Object register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/info")
    public Object putInfo(@RequestBody User user, @RequestHeader Integer userId) {
        user.setId(userId);
        return userService.putInfo(user);
    }

    @PutMapping("/password")
    public Object putPassword(@RequestBody User user, @RequestHeader Integer userId) {
        user.setId(userId);
        return userService.putPassword(user);
    }

    @GetMapping("/code")
    public Object getCode() {
        return userService.getCode();
    }

    @PutMapping("/mobile")
    public Object putMobile(@RequestBody User user, @RequestBody String code, @RequestHeader Integer userId) {
        user.setId(userId);
        return userService.putMobile(user, code);
    }

    @GetMapping("/users")
    public Object getUsers(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsers(page, size);
    }

    @GetMapping("/info/{id}")
    public Object getUserById(@PathVariable("id") Integer userId) {
        return userService.getInfo(userId);
    }
}
