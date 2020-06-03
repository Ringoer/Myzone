package xmu.ringoer.myzone.dynamics.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Ringoer
 */
public class Dynamics {

    private Integer id;
    private Integer userId;
    private String dynamics;
    private Integer favorited;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtCreate;
    private boolean beDeleted;

    public Dynamics() {
    }

    public Dynamics(Integer userId, String dynamics) {
        this.userId = userId;
        this.dynamics = dynamics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dynamics)) {
            return false;
        }
        Dynamics dynamics1 = (Dynamics) o;
        return beDeleted == dynamics1.beDeleted &&
                Objects.equals(id, dynamics1.id) &&
                Objects.equals(userId, dynamics1.userId) &&
                Objects.equals(dynamics, dynamics1.dynamics) &&
                Objects.equals(favorited, dynamics1.favorited) &&
                Objects.equals(gmtModified, dynamics1.gmtModified) &&
                Objects.equals(gmtCreate, dynamics1.gmtCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, dynamics, favorited, gmtModified, gmtCreate, beDeleted);
    }

    @Override
    public String toString() {
        return "Dynamics{" +
                "id=" + id +
                ", userId=" + userId +
                ", dynamics='" + dynamics + '\'' +
                ", favorited=" + favorited +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", beDeleted=" + beDeleted +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDynamics() {
        return dynamics;
    }

    public void setDynamics(String dynamics) {
        this.dynamics = dynamics;
    }

    public Integer getFavorited() {
        return favorited;
    }

    public void setFavorited(Integer favorited) {
        this.favorited = favorited;
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
