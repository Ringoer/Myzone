package xmu.ringoer.myzone.dynamics.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.ringoer.myzone.dynamics.domain.Dynamics;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface DynamicsMapper {
    /**
     * 根据userId查找动态
     * @param userId userId
     * @return 动态列表
     */
    List<Dynamics> selectDynamicsByUserId(Integer userId);

    /**
     * 插入新课程
     * @param dynamics 动态信息体
     * @return 行数
     */
    Integer insertDynamics(Dynamics dynamics);

    /**
     * 根据课程id删除动态
     * @param id 动态id
     * @return 行数
     */
    Integer deleteDynamicsById(Integer id);

    /**
     * 根据课程id查找动态
     * @param id 动态id
     * @return 动态信息
     */
    Dynamics selectDynamicsById(Integer id);
}
