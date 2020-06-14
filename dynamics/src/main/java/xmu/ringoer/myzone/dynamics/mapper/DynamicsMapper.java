package xmu.ringoer.myzone.dynamics.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xmu.ringoer.myzone.dynamics.domain.Dynamics;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface DynamicsMapper {
    /**
     * 根据userId查找动态
     * @param userId 用户Id
     * @param base 下界
     * @return 动态列表
     */
    List<Dynamics> selectDynamicsByUserId(@Param("userId") Integer userId, @Param("base") Integer base);

    /**
     * 插入新动态
     * @param dynamics 动态信息体
     * @return 行数
     */
    Integer insertDynamics(Dynamics dynamics);

    /**
     * 根据动态id删除动态
     * @param id 动态id
     * @return 行数
     */
    Integer deleteDynamicsById(Integer id);

    /**
     * 根据动态id查找动态
     * @param id 动态id
     * @return 动态信息
     */
    Dynamics selectDynamicsById(Integer id);
}
