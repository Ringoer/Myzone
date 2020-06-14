package xmu.ringoer.myzone.dynamics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.dynamics.dao.DynamicsDao;
import xmu.ringoer.myzone.dynamics.domain.Dynamics;
import xmu.ringoer.myzone.dynamics.util.ResponseUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class DynamicsService {

    @Autowired
    private DynamicsDao dynamicsDao;

    private Integer getBase(String p) {
        int perPage = 10;

        int page = Integer.parseInt(p) - 1;
        return perPage * page;
    }

    public Object getDynamicsByUserId(Integer userId, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        try {
            int p = Integer.parseInt(page);
            if(p < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            return ResponseUtil.badArgument();
        }

        List<Dynamics> dynamics = dynamicsDao.selectDynamicsByUserId(userId, getBase(page));
        if (null == dynamics || dynamics.size() == 0) {
            return ResponseUtil.wrongPage();
        }

        return ResponseUtil.ok(dynamics);
    }

    public Object postDynamics(Integer userId, Dynamics dynamics) {
        if(null == userId || null == dynamics) {
            return ResponseUtil.badArgument();
        }

        dynamics.setUserId(userId);

        Integer lines = dynamicsDao.insertDynamics(dynamics);

        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(dynamics.getId());
    }

    public Object deleteDynamics(Integer userId, Integer id) {
        if(null == userId || null == id) {
            return ResponseUtil.badArgument();
        }

        Dynamics dynamics = dynamicsDao.selectDynamicsById(id);

        if(null == dynamics) {
            return ResponseUtil.badArgumentValue();
        }
        if(!userId.equals(dynamics.getUserId())) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = dynamicsDao.deleteDynamicsById(id);

        if(lines.equals(0)) {
            return ResponseUtil.badArgumentValue();
        }

        return ResponseUtil.ok();
    }

    public Object getDynamicsById(Integer id) {
        if(null == id) {
            return ResponseUtil.badArgument();
        }

        Dynamics dynamics = dynamicsDao.selectDynamicsById(id);
        return ResponseUtil.ok(dynamics);
    }
}
