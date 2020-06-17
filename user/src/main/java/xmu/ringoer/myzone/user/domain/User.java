package xmu.ringoer.myzone.user.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Ringoer
 */
public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private String nickname;
    private String avatar;
    private String signature;
    private String mobile;
    private Integer gender;
    private LocalDateTime birthday;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtCreate;
    private boolean beDeleted;

    public User() {
    }

    public User(String username, String nickname, String password, String mobile) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.roleId = 1;
        this.nickname = nickname;
        this.avatar = "default.png";
        this.signature = "这个人很懒，什么都没写...";
        this.gender = 0;
        this.birthday = LocalDateTime.of(1970, 1, 1, 0, 0);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", signature='" + signature + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return beDeleted == user.beDeleted &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roleId, user.roleId) &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(signature, user.signature) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(gmtModified, user.gmtModified) &&
                Objects.equals(gmtCreate, user.gmtCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roleId, nickname, avatar, signature, mobile, gender, birthday, gmtModified, gmtCreate, beDeleted);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public boolean isBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(boolean beDeleted) {
        this.beDeleted = beDeleted;
    }
}
