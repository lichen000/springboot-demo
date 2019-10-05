package mangolost.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import mangolost.demo.common.helper.CommonResult;
import mangolost.demo.common.page.Page;
import mangolost.demo.common.utils.MyBeanUtils;
import mangolost.demo.entity.Student;
import mangolost.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by chen.li200 on 2018-03-19
 */
@RestController
@CrossOrigin
@RequestMapping("/api/students")
@Api(tags="学生接口")
public class StudentController {

	private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     *
     * @param id
     * @return
     */
	@ApiOperation(value="获取单个学生", notes = "根据id获取单个学生")
    @GetMapping("/{id}")
	public CommonResult get(@ApiParam(value = "学生id") @PathVariable int id) {
	    Student student = studentService.get(id);
		return new CommonResult().setData(student);
	}

	/**
     *
	 * @return
	 */
	@ApiOperation(value="分页获取所有学生", notes = "分页获取所有学生")
	@GetMapping("/")
	public CommonResult getAllPage(@ApiParam(value = "分页大小") @RequestParam(required = false, defaultValue = "10") int pageSize,
									@ApiParam(value = "分页页码") @RequestParam(required = false, defaultValue = "1") int pageNumber) {
		Page<Student> page = new Page<>(pageNumber, pageSize);
		page = studentService.getAllPage(page);
		return new CommonResult().setData(page);
	}

	/**
     *
	 * @param student
	 * @return
	 */
	@ApiOperation(value="新增学生", notes = "新增学生")
	@PostMapping("/")
	public CommonResult add(@RequestBody Student student) {
        Student entity = studentService.add(student);
        return new CommonResult().setData(entity);
    }

	/**
     *
	 * @param id
	 * @param map
	 * @return
	 */
	@ApiOperation(value="更新学生", notes = "更新学生")
	@PutMapping("/{id}")
	public CommonResult update(@ApiParam(value = "学生id") @PathVariable int id, @RequestBody Map<String,Object> map) {
		CommonResult commonResult = new CommonResult();
		Student entity = studentService.get(id); //从数据库中获取的原student
		if (entity == null) {
			return commonResult.setCodeAndMessage(404, "要更新的对象并不存在");
		}

		//以下字段不允许通过update接口更新
		map.remove("id");  //id不能更新
		map.remove("createTime"); //createTime在创建记录时由数据库自动生成
		map.remove("updateTime"); //updateTime在每次更新记录时由数据库自动生成
		if (!map.isEmpty()) {
			//根据map中设定的字段，修改entity中相应字段
			//利用反射，对map中存储的所有字段key，设置entity属性值为student中对应的属性值
			MyBeanUtils.changeProps(entity, map, Student.class);
			entity = studentService.update(entity); //更新entity并返回更新后的student
		}
		return commonResult.setData(entity);
	}

	/**
	 * 删除给定student
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除学生", notes = "删除学生")
	@DeleteMapping("/{id}")
	public CommonResult delete(@ApiParam(value = "学生id") @PathVariable int id) {
		studentService.delete(id);
		return new CommonResult();
	}
}
