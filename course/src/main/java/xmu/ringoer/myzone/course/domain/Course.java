package xmu.ringoer.myzone.course.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Ringoer
 */
public class Course {

    private Integer id;
    private Integer userId;
    private String courseName;
    private String isOddWeek;
    private String weekday;
    private Integer beginTime;
    private Integer endTime;
    private String place;
    private String teacher;
    private BigDecimal credit;
    private String date;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtCreate;
    private boolean beDeleted;

    public boolean check() {
        if(null == courseName || "".equals(courseName) ||
                null == isOddWeek || "".equals(isOddWeek) ||
                null == weekday || "".equals(weekday) ||
                null == beginTime ||
                null == endTime ||
                null == place || "".equals(place) ||
                null == teacher || "".equals(teacher) ||
                null == credit ||
                null == date || "".equals(date)) {
            return false;
        }
        return true;
    }

    public Course() {
    }

    public Course(Integer userId, String courseName, String isOddWeek, String weekday, Integer beginTime, Integer endTime, String place, String teacher, BigDecimal credit, String date) {
        this.userId = userId;
        this.courseName = courseName;
        this.isOddWeek = isOddWeek;
        this.weekday = weekday;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
        this.teacher = teacher;
        this.credit = credit;
        this.date = date;
    }

    public Course(Integer userId, String weekday) {
        this.userId = userId;
        this.weekday = weekday;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseName='" + courseName + '\'' +
                ", isOddWeek='" + isOddWeek + '\'' +
                ", weekday='" + weekday + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", place='" + place + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credit=" + credit +
                ", date='" + date + '\'' +
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
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return beDeleted == course.beDeleted &&
                Objects.equals(id, course.id) &&
                Objects.equals(userId, course.userId) &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(isOddWeek, course.isOddWeek) &&
                Objects.equals(weekday, course.weekday) &&
                Objects.equals(beginTime, course.beginTime) &&
                Objects.equals(endTime, course.endTime) &&
                Objects.equals(place, course.place) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(credit, course.credit) &&
                Objects.equals(date, course.date) &&
                Objects.equals(gmtModified, course.gmtModified) &&
                Objects.equals(gmtCreate, course.gmtCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, courseName, isOddWeek, weekday, beginTime, endTime, place, teacher, credit, date, gmtModified, gmtCreate, beDeleted);
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getIsOddWeek() {
        return isOddWeek;
    }

    public void setIsOddWeek(String isOddWeek) {
        this.isOddWeek = isOddWeek;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
