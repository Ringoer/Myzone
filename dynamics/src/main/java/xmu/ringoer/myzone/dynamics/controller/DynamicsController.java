package xmu.ringoer.myzone.dynamics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.dynamics.domain.Dynamics;
import xmu.ringoer.myzone.dynamics.service.DynamicsService;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
public class DynamicsController {

    @Autowired
    private DynamicsService dynamicsService;

    @GetMapping("/")
    public Object getDynamicsByUserId(@RequestHeader Integer userId) {
        return dynamicsService.getDynamicsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Object getDynamicsById(@PathVariable Integer id) {
        return dynamicsService.getDynamicsById(id);
    }

    @PostMapping("/")
    public Object postDynamics(@RequestHeader Integer userId, @RequestBody Dynamics dynamics) {
        return dynamicsService.postDynamics(userId, dynamics);
    }

    @DeleteMapping("/")
    public Object deleteDynamics(@RequestHeader Integer userId, @RequestBody Dynamics dynamics) {
        return dynamicsService.deleteDynamics(userId, dynamics.getId());
    }
}
