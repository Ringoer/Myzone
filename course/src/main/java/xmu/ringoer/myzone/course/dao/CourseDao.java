package xmu.ringoer.myzone.course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.course.domain.Course;
import xmu.ringoer.myzone.course.mapper.CourseMapper;

import java.util.List;

/**
 * @author Ringoer
 */
@Repository
public class CourseDao {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> selectCourseByUserId(Integer userId) {
        return courseMapper.selectCourseByUserId(userId);
    }

    public Integer insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    public Integer deleteCourseById(Integer id) {
        return courseMapper.deleteCourseById(id);
    }

    public Course selectCourseById(Integer id) {
        return courseMapper.selectCourseById(id);
    }

    public List<Course> selectCourseByWeekday(Course course) {
        return courseMapper.selectCourseByWeekday(course);
    }
}
