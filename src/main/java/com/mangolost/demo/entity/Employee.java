package com.mangolost.demo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mangolost on 2018-03-19
 */
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id; //主键id
	private Timestamp create_time; //记录创建时间
	private Timestamp update_time; //记录更新时间
	@NotBlank
	private String employee_no; //工号
	@NotBlank
	private String employee_name;//姓名
	@Min(value = 0)
	private Integer age; //年龄

	public Employee() {
		super();
	}

	public Employee(Integer id, Timestamp create_time, Timestamp update_time, String employee_no, String employee_name, Integer age) {
		super();
		this.id = id;
		this.create_time = create_time;
		this.update_time = update_time;
		this.employee_no = employee_no;
		this.employee_name = employee_name;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
