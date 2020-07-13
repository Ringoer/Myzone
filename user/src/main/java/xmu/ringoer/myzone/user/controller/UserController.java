package xmu.ringoer.myzone.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.user.controller.dto.UserCodeDto;
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
    public Object register(@RequestBody UserCodeDto userCodeDto) {
        return userService.register(userCodeDto.getUser(), userCodeDto.getCode());
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
    public Object putPassword(@RequestBody UserCodeDto userCodeDto, @RequestHeader Integer userId) {
        User user = userCodeDto.getUser();
        user.setId(userId);
        return userService.putPassword(user, userCodeDto.getCode());
    }

    @PutMapping("/email")
    public Object putEmail(@RequestBody UserCodeDto userCodeDto, @RequestHeader Integer userId) {
        User user = userCodeDto.getUser();
        user.setId(userId);
        return userService.putEmail(user, userCodeDto.getCode());
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
