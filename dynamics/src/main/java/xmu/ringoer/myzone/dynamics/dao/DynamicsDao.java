package xmu.ringoer.myzone.dynamics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.dynamics.domain.Dynamics;
import xmu.ringoer.myzone.dynamics.mapper.DynamicsMapper;

import java.util.List;

/**
 * @author Ringoer
 */
@Repository
public class DynamicsDao {

    @Autowired
    private DynamicsMapper dynamicsMapper;

    public List<Dynamics> selectDynamicsByUserId(Integer userId) {
        return dynamicsMapper.selectDynamicsByUserId(userId);
    }

    public Integer insertDynamics(Dynamics course) {
        return dynamicsMapper.insertDynamics(course);
    }

    public Integer deleteDynamicsById(Integer id) {
        return dynamicsMapper.deleteDynamicsById(id);
    }

    public Dynamics selectDynamicsById(Integer id) {
        return dynamicsMapper.selectDynamicsById(id);
    }
}
