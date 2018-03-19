package mangolost.demo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by chen.li200 on 2018-03-19
 */
@Entity
@Table(name="t_student")
public class Student implements Serializable {

	public static final long serialVersionUID = 1L;

	private Integer id; //主键id
	private Timestamp createTime; //记录创建时间
	private Timestamp updateTime; //记录更新时间
	private String note; //备注

	private String number; //学号
	private String name;//姓名
	private Integer age; //年龄

	public Student() {
		super();
	}

	public Student(Integer id, Timestamp createTime, Timestamp updateTime, String note, String number, String name, Integer age) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.note = note;
		this.number = number;
		this.name = name;
		this.age = age;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "create_time", insertable = false, updatable = false)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", insertable = false, updatable = false)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "age")
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
