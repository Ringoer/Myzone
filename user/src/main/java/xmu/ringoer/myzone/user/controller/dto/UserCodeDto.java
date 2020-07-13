package xmu.ringoer.myzone.user.controller.dto;

import xmu.ringoer.myzone.user.domain.User;

import java.util.Objects;

public class UserCodeDto {

    private User user;
    private String code;

    public UserCodeDto() {
    }

    public UserCodeDto(User user, String code) {
        this.user = user;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCodeDto)) {
            return false;
        }
        UserCodeDto that = (UserCodeDto) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, code);
    }

    @Override
    public String toString() {
        return "UserCodeDto{" +
                "user=" + user +
                ", code='" + code + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
