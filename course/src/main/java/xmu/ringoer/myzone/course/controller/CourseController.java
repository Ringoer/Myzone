package xmu.ringoer.myzone.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ringoer.myzone.course.domain.Course;
import xmu.ringoer.myzone.course.service.CourseService;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public Object getCourses(@RequestHeader Integer userId, @RequestParam String q, @RequestParam String p) {
        return courseService.getCourses(userId, q, p);
    }

    @PostMapping("/")
    public Object postCourse(@RequestHeader Integer userId, @RequestBody Course course) {
        return courseService.postCourse(userId, course);
    }

    @DeleteMapping("/")
    public Object deleteCourse(@RequestHeader Integer userId, @RequestBody Course course) {
        return courseService.deleteCourse(userId, course.getId());
    }

    @PutMapping("/")
    public Object putCourse(@RequestHeader Integer userId, @RequestBody Course course) {
        return courseService.putCourse(userId, course);
    }

    @GetMapping("/today")
    public Object getTodayCourses(@RequestHeader Integer userId) {
        return courseService.getTodayCourses(userId);
    }
}
