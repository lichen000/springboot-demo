package mangolost.demo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by chen.li200 on 2018-03-19
 */
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id; //主键id
	private Timestamp createTime; //记录创建时间
	private Timestamp updateTime; //记录更新时间
	private String number; //学号
	private String name;//姓名
	private Integer age; //年龄

	public Student() {
		super();
	}

	public Student(Integer id, Timestamp createTime, Timestamp updateTime, String number, String name, Integer age) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.number = number;
		this.name = name;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
