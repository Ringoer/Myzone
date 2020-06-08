package xmu.ringoer.myzone.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.course.dao.CourseDao;
import xmu.ringoer.myzone.course.domain.Course;
import xmu.ringoer.myzone.course.util.ResponseUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class CourseService {

    private static final String[] WEEKDAYS = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private List<Course> sqlCourses = null;

    @Autowired
    private CourseDao courseDao;

    public Object getCourses(Integer userId, String queryString, String page) {
        if(null == userId) {
            return ResponseUtil.badArgument();
        }

        if(null == queryString) {
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

        List<Course> courses = sqlCourses;
        if(null == courses) {
            courses = courseDao.selectCourseByUserId(userId);
        }

        if("".equals(queryString)) {
            return ResponseUtil.ok(splitByPage(courses, page));
        }

        String[] tags = queryString.split(" ");

        List<Course> ans = new ArrayList<>();

        for(Course course : courses) {
            List<String> values = course.values();
            boolean flag = true;
            for(String tag : tags) {
                if("".equals(tag)) {
                    continue;
                }

                if(!values.contains(tag)) {
                    flag = false;
                    break;
                }
            }

            if(flag) {
                ans.add(course);
            }
        }

        return ResponseUtil.ok(ans);
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

        if(null != sqlCourses) {
            sqlCourses.add(course);
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

        if(null != sqlCourses) {
            sqlCourses.remove(course);
        }

        return ResponseUtil.ok();
    }

    public Object putCourse(Integer userId, Course course) {
        if(null == userId || null == course) {
            return ResponseUtil.badArgument();
        }

        if(!course.check()) {
            return ResponseUtil.badArgument();
        }
        if(!userId.equals(course.getUserId())) {
            return ResponseUtil.badArgumentValue();
        }

        Integer lines = courseDao.updateCourse(course);

        if(lines.equals(0)) {
            return ResponseUtil.serious();
        }

        sqlCourses = null;

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

    private List<Course> splitByPage(List<Course> courses, String p) {
        int page = Integer.parseInt(p);

        int ceil = page * 10;
        int floor = ceil - 10;

        if(courses.size() < ceil) {
            ceil = courses.size();
            floor = ceil - ceil % 10;
        }
        return courses.subList(floor, ceil);
    }
}
