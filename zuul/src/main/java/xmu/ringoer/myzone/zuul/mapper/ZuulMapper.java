package xmu.ringoer.myzone.zuul.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.ringoer.myzone.zuul.domain.Role;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface ZuulMapper {
    /**
     *根据用户角色id返回URI列表
     * @param id 角色id
     * @return URI列表
     */
    List<Role> findUrisByRoleId(Integer id);
}
