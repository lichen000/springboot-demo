package mangolost.demo.service;

import mangolost.demo.common.page.Page;
import mangolost.demo.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chen.li200 on 2018-03-19
 */
@Service
public class StudentService {

	private final JdbcTemplate mysqlJdbcTemplate;

	public StudentService(@Qualifier("primaryJdbcTemplate") JdbcTemplate mysqlJdbcTemplate) {
		this.mysqlJdbcTemplate = mysqlJdbcTemplate;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public Student get(int id) {
		String sql = "select * from t_student where record_status = 1 and id = ?";
		List<Student> list = mysqlJdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
		if (list != null && list.size()> 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 *
	 * @param page
	 * @return
	 */
	public Page<Student> getAllPage(Page<Student> page) {
		String sqlCount = "select count(*) from t_student where record_status = 1";
		int count = mysqlJdbcTemplate.queryForObject(sqlCount, Integer.class);
		if (count > 0) {
			String sqlData = "select * from t_student where record_status = 1 order by id asc limit ?, ?";
			int pageSize = page.getPageSize(), pageNumber = page.getPageNumber();
			int offset = (pageNumber - 1) * pageSize;
			List<Student> list = mysqlJdbcTemplate.query(sqlData, new Object[]{offset, pageSize}, new BeanPropertyRowMapper<>(Student.class));
			int totalPages = (count - 1) / pageSize + 1;
			page.setTotalCount(count);
			page.setTotalPages(totalPages);
			page.setResult(list);
		}
		return page;
	}

	/**
	 * @param entity
	 * @return
	 */
	public Student add(Student entity) {
		String sql = "insert into t_student (`number`, `name`, `age`) values (?, ?, ?)";
		mysqlJdbcTemplate.update(sql, entity.getNumber(), entity.getName(), entity.getAge());
		int id = mysqlJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);//获得刚刚插入的id
		return get(id);
	}

	/**
	 * @param entity
	 * @return
	 */
	public Student update(Student entity) {
		int id = entity.getId();
		String sql = "update t_student set `number` = ?, `name` = ?, `age` = ? where record_status = 1 and id = ?";
		mysqlJdbcTemplate.update(sql, entity.getNumber(), entity.getName(), entity.getAge(), id);
		return get(id);
	}

	/**
	 * @param id
	 */
	public void delete(int id) {
		String sql = "update t_student set record_status = 0 where id = ?";
		mysqlJdbcTemplate.update(sql, id);
	}

}
