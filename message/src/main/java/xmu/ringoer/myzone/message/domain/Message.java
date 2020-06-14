package xmu.ringoer.myzone.message.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Ringoer
 */
public class Message {

    private Integer id;
    private LocalDateTime sendTime;
    private String topic;
    private String content;
    private String type;
    private boolean beRead;
    private Integer fromId;
    private Integer toId;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtCreate;
    private boolean beDeleted;

    public Message() {
    }

    public Message(LocalDateTime sendTime, String topic, String content, String type, boolean beRead, Integer fromId, Integer toId) {
        this.sendTime = sendTime;
        this.topic = topic;
        this.content = content;
        this.type = type;
        this.beRead = beRead;
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return beRead == message.beRead &&
                beDeleted == message.beDeleted &&
                Objects.equals(id, message.id) &&
                Objects.equals(sendTime, message.sendTime) &&
                Objects.equals(topic, message.topic) &&
                Objects.equals(content, message.content) &&
                Objects.equals(type, message.type) &&
                Objects.equals(fromId, message.fromId) &&
                Objects.equals(toId, message.toId) &&
                Objects.equals(gmtModified, message.gmtModified) &&
                Objects.equals(gmtCreate, message.gmtCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sendTime, topic, content, type, beRead, fromId, toId, gmtModified, gmtCreate, beDeleted);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sendTime=" + sendTime +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", beRead=" + beRead +
                ", fromId=" + fromId +
                ", toId=" + toId +
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

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBeRead() {
        return beRead;
    }

    public void setBeRead(boolean beRead) {
        this.beRead = beRead;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
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
