package com.mangolost.demo.controller;

import com.google.gson.*;
import com.mangolost.demo.common.helper.CommonResult;
import com.mangolost.demo.common.page.Page;
import com.mangolost.demo.common.utils.MyBeanUtils;
import com.mangolost.demo.entity.Employee;
import com.mangolost.demo.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mangolost on 2018-03-19
 */
@RestController
@CrossOrigin
@RequestMapping("/api/employee")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                if (src == src.longValue()) {
                    return new JsonPrimitive(src.longValue()); //去掉前端传过来的整数带.0
                }
                return new JsonPrimitive(src);
            })
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

	private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 获取单个
     * @param id
     * @return
     */
    @RequestMapping("get")
	public CommonResult get(@RequestParam int id) {
		CommonResult commonResult = new CommonResult();
	    Employee employee = employeeService.get(id);
		return commonResult.setData(employee);
	}

	/**
	 * 分页获取
	 * @param pageSize
	 * @param pageNumber
	 * @param employee_no
	 * @param employee_name
	 * @return
	 */
	@RequestMapping("getall")
	public CommonResult getAllPage(@RequestParam(required = false, defaultValue = "10") int pageSize,
									@RequestParam(required = false, defaultValue = "1") int pageNumber,
								   String employee_no,
								   String employee_name) {
		CommonResult commonResult = new CommonResult();
		Page<Employee> page = new Page<>(pageNumber, pageSize);
		page = employeeService.getAllPage(page, employee_no, employee_name);
		return commonResult.setData(page);
	}

	/**
     * 新增
	 * @param entity
	 * @return
	 */
	@RequestMapping("add")
	public CommonResult add(@Valid Employee entity) {

		CommonResult commonResult = new CommonResult();

		LOGGER.info("接口/api/employee/add被访问，entity= {}", GSON.toJson(entity));

		//检查employee_no是否被占用
		String employee_no = entity.getEmployee_no();
		if (employeeService.checkEmployeenoUsed(employee_no, null)) {
			return commonResult.setCodeAndMessage(430, "此工号已被占用");
		}

        Employee newEntity = employeeService.add(entity);
        return commonResult.setData(newEntity);
    }

	/**
     * 更新
	 * @param id
	 * @param updatedParams
	 * @return
	 */
	@RequestMapping("update")
	public CommonResult update(@RequestParam int id, @RequestParam String updatedParams) {
		CommonResult commonResult = new CommonResult();

		LOGGER.info("接口/api/employee/update被访问，参数：id= {}, updatedParams= {}", id, updatedParams);

		Employee entity = employeeService.get(id); //从数据库中获取的原entity
		if (entity == null) {
			return commonResult.setCodeAndMessage(404, "对象不存在");
		}

		//允许更新的字段
		String[] allowUpdateFields = {"employee_no", "employee_name", "age"};

		Map<String, Object> map0 = GSON.fromJson(updatedParams, Map.class);
        Map<String, Object> map = new HashMap<>();
		for (String field : allowUpdateFields) {
		    if (map0.containsKey(field)) {
		        map.put(field, map0.get(field));
            }
        }

		if (map.isEmpty()) {
			return commonResult.setData(entity);
		}

		//对字段格式做校验，这里我没有想到什么好的方法，都是对每个需要校验的字段写对应代码
		if (map.containsKey("employee_no")) {
			String employee_no = (String) map.get("employee_no");
			if (StringUtils.isBlank(employee_no)) {
				return commonResult.setCodeAndMessage(430, "employee_no不能设置为空");
			}
			//检验employee_no是否被占用
			if (employeeService.checkEmployeenoUsed(employee_no, entity)) {
				return commonResult.setCodeAndMessage(430, "此工号已被占用");
			}
		}
		if (map.containsKey("employee_name")) {
			String employee_name = (String) map.get("employee_name");
			if (StringUtils.isBlank(employee_name)) {
				return commonResult.setCodeAndMessage(430, "employee_name不能设置为空");
			}
		}
		if (map.containsKey("age")) {
			int age = (int) map.get("age");
			if (age < 0) {
				return commonResult.setCodeAndMessage(430, "age不能设置为负");
			}
		}

		//根据map中设定的字段，修改entity中相应字段
		//利用反射，对map中存储的所有字段key，设置entity属性值为entity中对应的属性值
		MyBeanUtils.changeProps(entity, map, Employee.class);
		Employee newEntity = employeeService.update(entity); //更新并返回更新后的newEntity
		return commonResult.setData(newEntity);

	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public CommonResult delete(@RequestParam int id) {
		CommonResult commonResult = new CommonResult();

		LOGGER.info("接口/api/employee/delete被访问，参数：id= {}", id);

		employeeService.delete(id);
		return commonResult;
	}
}
