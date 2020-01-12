package xmu.ringoer.myzone.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.course.dao.CourseDao;
import xmu.ringoer.myzone.course.domain.Course;
import xmu.ringoer.myzone.course.util.ResponseUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class CourseService {

    private static final String[] WEEKDAYS = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    @Autowired
    private CourseDao courseDao;

    public Object getCourses(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        List<Course> courses = courseDao.selectCourseByUserId(userId);
        return ResponseUtil.ok(courses);
    }

    public Object postCourse(Integer userId, Course course) {
        if(null == userId || null == course) {
            return ResponseUtil.badArgument();
        }

        if(!course.check()) {
            return ResponseUtil.badArgument();
        }

        course.setUserId(userId);

        Integer lines = courseDao.insertCourse(course);

        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        return ResponseUtil.ok(course.getId());
    }

    public Object deleteCourse(Integer userId, Integer id) {
        if(null == userId || null == id) {
            return ResponseUtil.badArgument();
        }

        Course course = courseDao.selectCourseById(id);

        if(null == course) {
            return ResponseUtil.badArgumentValue();
        }
        if(!userId.equals(course.getUserId())) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = courseDao.deleteCourseById(id);

        if(lines.equals(0)) {
            return ResponseUtil.badArgumentValue();
        }

        return ResponseUtil.ok();
    }

    public Object getTodayCourses(Integer userId) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        Course course = new Course(userId, WEEKDAYS[LocalDateTime.now().getDayOfWeek().getValue()]);
        List<Course> courses = courseDao.selectCourseByWeekday(course);

        return ResponseUtil.ok(courses);
    }
}
