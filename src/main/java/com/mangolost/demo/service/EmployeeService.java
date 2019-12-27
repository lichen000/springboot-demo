package com.mangolost.demo.service;

import com.mangolost.demo.common.page.Page;
import com.mangolost.demo.entity.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mangolost on 2018-03-19
 */
@Service
public class EmployeeService {

	private final JdbcTemplate mysqlJdbcTemplate;

	public EmployeeService(@Qualifier("primaryJdbcTemplate") JdbcTemplate mysqlJdbcTemplate) {
		this.mysqlJdbcTemplate = mysqlJdbcTemplate;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public Employee get(int id) {
		String sql = "select * from t_employee where record_status = 1 and id = ? limit 1";
		List<Employee> list = mysqlJdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 *
	 * @param page
	 * @param employee_no
	 * @param employee_name
	 * @return
	 */
	public Page<Employee> getAllPage(Page<Employee> page, String employee_no, String employee_name) {
		String selectSqlCount = "select count(*) from t_employee ";
		String selectSqlData = "select * from t_employee ";
		String whereSql = " where record_status = 1 ";
		List<Object> objectList = new ArrayList<>();
		if (StringUtils.isNotBlank(employee_no)) {
			whereSql += " and employee_no = ? ";
			objectList.add(employee_no);
		}
		if (StringUtils.isNotBlank(employee_name)) {
			whereSql += " and employee_name like ? ";
			objectList.add("%" + employee_name + "%");
		}
		String sqlCount = selectSqlCount + whereSql;
		int count = mysqlJdbcTemplate.queryForObject(sqlCount, objectList.toArray(), Integer.class);
		if (count > 0) {
			String limitSql = " order by employee_no asc limit ?, ? ";
			String sqlData = selectSqlData + whereSql + limitSql;
			int pageSize = page.getPageSize(), pageNumber = page.getPageNumber();
			int offset = (pageNumber - 1) * pageSize;
			objectList.add(offset);
			objectList.add(pageSize);
			List<Employee> list = mysqlJdbcTemplate.query(sqlData, objectList.toArray(), new BeanPropertyRowMapper<>(Employee.class));
			int totalPages = (count - 1) / pageSize + 1;
			page.setTotalCount(count);
			page.setTotalPages(totalPages);
			page.setResult(list);
		}
		return page;
	}

	/**
	 *
	 * @param employee_no
	 * @param entity
	 * @return
	 */
	public boolean checkEmployeenoUsed(String employee_no, Employee entity) {
		String sql = "";
		Object[] objects = null;
		if (entity == null) {
			//新增
			sql = "select count(*) from t_employee where record_status = 1 and employee_no = ? ";
			objects = new Object[]{employee_no};
		} else {
			//更新
			sql = "select count(*) from t_employee where record_status = 1 and employee_no = ? and id != ?";
			objects = new Object[]{employee_no, entity.getId()};
		}
		int count = mysqlJdbcTemplate.queryForObject(sql, objects, Integer.class);
		return count > 0;
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public Employee add(Employee entity) {
		String sql = "insert into t_employee (`employee_no`, `employee_name`, `age`) values (?, ?, ?)";
		mysqlJdbcTemplate.update(sql, entity.getEmployee_no(), entity.getEmployee_name(), entity.getAge());
		int id = mysqlJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);//获得刚刚插入的id
		return get(id);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public Employee update(Employee entity) {
		int id = entity.getId();
		String sql = "update t_employee set `employee_no` = ?, `employee_name` = ?, `age` = ? where record_status = 1 and id = ?";
		mysqlJdbcTemplate.update(sql, entity.getEmployee_no(), entity.getEmployee_name(), entity.getAge(), id);
		return get(id);
	}

	/**
	 *
	 * @param id
	 */
	public void delete(int id) {
		String sql = "update t_employee set record_status = 0 where id = ?"; //软删除
		mysqlJdbcTemplate.update(sql, id);
	}

}
