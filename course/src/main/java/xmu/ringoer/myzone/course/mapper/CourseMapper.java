package xmu.ringoer.myzone.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xmu.ringoer.myzone.course.domain.Course;

import java.util.List;

/**
 * @author Ringoer
 */
@Mapper
public interface CourseMapper {
    /**
     * 根据userId查找课程
     * @param userId userId
     * @param queryString 搜索字符串
     * @param base 下界
     * @return 课程列表
     */
    List<Course> selectCourseByUserId(@Param("userId") Integer userId, @Param("queryString") String queryString, @Param("base") Integer base);

    /**
     * 插入新课程
     * @param course 课程信息体
     * @return 行数
     */
    Integer insertCourse(Course course);

    /**
     * 根据课程id删除课程
     * @param id 课程id
     * @return 行数
     */
    Integer deleteCourseById(Integer id);

    /**
     * 根据课程id查找课程
     * @param id 课程id
     * @return 课程信息
     */
    Course selectCourseById(Integer id);

    /**
     * 根据日期和用户id查找课程
     * @param course 包含用户id和日期的课程信息
     * @return 课程列表
     */
    List<Course> selectCourseByWeekday(Course course);

    /**
     * 更新课程
     * @param course 课程信息体
     * @return 行数
     */
    Integer updateCourse(Course course);
}
