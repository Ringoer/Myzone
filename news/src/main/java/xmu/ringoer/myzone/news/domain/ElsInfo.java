package xmu.ringoer.myzone.news.domain;

import java.util.Objects;

/**
 * @author Ringoer
 */
public class ElsInfo {

    private String date;
    private String type;
    private String url;
    private String title;

    public ElsInfo() {
    }

    public ElsInfo(String date, String type, String url, String title) {
        this.date = date;
        this.type = type;
        this.url = url;
        this.title = title;
    }

    @Override
    public String toString() {
        return "ElsInfo{" +
                "date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElsInfo)) {
            return false;
        }
        ElsInfo elsInfo = (ElsInfo) o;
        return Objects.equals(date, elsInfo.date) &&
                Objects.equals(type, elsInfo.type) &&
                Objects.equals(url, elsInfo.url) &&
                Objects.equals(title, elsInfo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, url, title);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
