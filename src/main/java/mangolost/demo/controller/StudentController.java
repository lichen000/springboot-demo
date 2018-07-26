package mangolost.demo.controller;

import com.google.gson.*;
import mangolost.demo.common.CommonConstant;
import mangolost.demo.common.CommonResult;
import mangolost.demo.common.helper.ApiStatusCode;
import mangolost.demo.common.message.CommonMessage;
import mangolost.demo.common.utils.JsonPUtils;
import mangolost.demo.common.utils.MyBeanUtils;
import mangolost.demo.entity.Student;
import mangolost.demo.service.StudentService;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chen.li200 on 2018-03-19
 */
@RestController
@CrossOrigin
@Validated
@RequestMapping("api/student")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     *
     * @param id
     * @param commonResult
     * @return
     */
    @RequestMapping("get")
	public CommonResult get(@Range @RequestParam int id, CommonResult commonResult) {

	    Optional<Student> student = studentService.get(id);
		if (!student.isPresent()) {
			commonResult.setMessage(CommonMessage.NO_RECORD_FOUND);
		}
		commonResult.setData(student);
        return commonResult;
	}

	/**
     *
	 * @param pageable
	 * @param commonResult
	 * @return
	 */
	@RequestMapping("getall")
	public CommonResult getAllPage(@PageableDefault(value = CommonConstant.DEFAULT_PAGE_SIZE, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
							 CommonResult commonResult) {
		Page<Student> page = studentService.getAllPage(pageable);

		List<Student> students = page.getContent();
		if (students.size() == 0) {
			commonResult.setMessage(CommonMessage.NO_RECORD_FOUND);
		}
		commonResult.setData(page);
		return commonResult;
	}

	/**
	 * 按照请求中指定的任意属性查找对应的student
	 *
	 * @param request
	 * @param queryParams
	 * @param commonResult
	 * @return
	 */
	@RequestMapping("query")
	public Object query(HttpServletRequest request, @RequestParam String queryParams, CommonResult commonResult) {

        Map<String, Object> map = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .fromJson(queryParams, Map.class);
		if (map.isEmpty()) {
			commonResult.setCodeAndMessage(ApiStatusCode.PARAM_ERROR, CommonMessage.PARAM_ERROR);
			return commonResult;
		}

		for (String key: map.keySet()) {


		    Object param = map.get(key);
		    if (param instanceof Double) {
		        if (((Double) param).longValue() == (Double) param) {
		            map.put(key, ((Double) param).intValue());
                }
            }
        }



		List<Student> students;
		try {
			students = studentService.queryByParameters(map);
		} catch (Exception e) {
			LOGGER.error("student query error", e);
			commonResult.setCodeAndMessage(ApiStatusCode.INTERNAL_SERVER_ERROR, "查询错误");
			commonResult.setData(null);
			return commonResult;
		}
		if (students == null || students.size() == 0) {
			commonResult.setMessage(CommonMessage.NO_RECORD_FOUND);
		}
		commonResult.setData(students);
		return commonResult;
	}

	/**
     *
	 * @param student
	 * @param commonResult
	 * @return
	 */
	@RequestMapping(value = "add")
	public Object add(Student student, CommonResult commonResult) {

        Student entity = studentService.add(student);
        commonResult.setData(entity);
        return commonResult;
    }

	/**
     *
	 * @param id
	 * @param updatedParams
	 * @param commonResult
	 * @return
	 */
	@RequestMapping(value = "update")
	public Object update(@Range @RequestParam int id,
						 @RequestParam String updatedParams,
						 CommonResult commonResult) {

		Optional<Student> optionalStudent = studentService.get(id); //从数据库中获取的原student
		if (!optionalStudent.isPresent()) {
			commonResult.setCodeAndMessage(ApiStatusCode.INTERNAL_SERVER_ERROR, "要更新的对象并不存在");
		} else {

		    Student entity = optionalStudent.get();

			//通过参数构建的一个student，只有给定参数有值，其他都是null
			Student student = new GsonBuilder()
					.serializeNulls()
					.setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create()
					.fromJson(updatedParams, Student.class);

			//把参数转为一个map
			Map map = new GsonBuilder()
					.serializeNulls()
					.setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create()
					.fromJson(updatedParams, Map.class);
			//以下字段不允许通过update接口更新
			map.remove("id");  //id不能更新
			map.remove("createTime"); //createTime在创建记录时由数据库自动生成
			map.remove("updateTime"); //updateTime在每次更新记录时由数据库自动生成

			//根据map中设定的字段，修改entity中相应字段
			//利用反射，对map中存储的所有字段key，设置entity属性值为student中对应的属性值
			MyBeanUtils.changeProps(entity, student, map, Student.class);

			entity = studentService.update(entity); //更新entity并返回更新后的student
			commonResult.setData(entity);

		}
		return commonResult;
	}

	/**
	 * 删除给定student
	 *
	 * @param id
	 * @param commonResult
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "delete")
	public Object delete(@Range @RequestParam int id, CommonResult commonResult, String callback) {

		Optional<Student> entity = studentService.get(id);
		if (entity.isPresent()) {
			studentService.delete(id);
		} else {
			commonResult.setCodeAndMessage(ApiStatusCode.INTERNAL_SERVER_ERROR, "要删除的对象并不存在");
		}

		return JsonPUtils.doJsonP(commonResult, callback);
	}
}
