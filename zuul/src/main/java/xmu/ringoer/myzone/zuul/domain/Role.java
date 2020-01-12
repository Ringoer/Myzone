package xmu.ringoer.myzone.zuul.domain;

import java.util.Objects;

/**
 * @author Ringoer
 */
public class Role {

    private Integer id;

    private Integer roleId;

    private String url;

    private String method;

    private String gmtCreate;
    private String gmtModified;
    private Boolean beDeleted;

    public Role(){}

    public Role(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(roleId, role.roleId) &&
                url.equals(role.url) &&
                method.equals(role.method) &&
                Objects.equals(gmtCreate, role.gmtCreate) &&
                Objects.equals(gmtModified, role.gmtModified) &&
                Objects.equals(beDeleted, role.beDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, url, method, gmtCreate, gmtModified, beDeleted);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", gmtModified='" + gmtModified + '\'' +
                ", beDeleted=" + beDeleted +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }
}
