package xmu.ringoer.myzone.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.service.UserService;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
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

    @PutMapping("/register/verify")
    public Object registerVerify(@RequestBody String code) {
        return userService.registerVerify(code);
    }

    @PutMapping("/info")
    public Object putInfo(@RequestBody User user, @RequestHeader Integer userId) {
        user.setId(userId);
        return userService.putInfo(user);
    }

    @PostMapping("/code")
    public Object getCode(@RequestBody String email) {
        return userService.getCode(email);
    }

    @PutMapping("/password")
    public Object putPassword(@RequestBody User user) {
        return userService.putPassword(user);
    }

    @PutMapping("/password/verify")
    public Object putPasswordVerify(@RequestBody String code) {
        return userService.putPasswordVerify(code);
    }

    @PutMapping("/email")
    public Object putEmail(@RequestBody User user) {
        return userService.putEmail(user);
    }

    @PutMapping("/email/verify")
    public Object putEmailVerify(@RequestBody String code) {
        return userService.putEmailVerify(code);
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
