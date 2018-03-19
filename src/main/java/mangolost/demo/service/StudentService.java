package mangolost.demo.service;

import mangolost.demo.dao.StudentJpaDao;
import mangolost.demo.entity.Student;
import org.hibernate.Query;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by chen.li200 on 2018-03-19
 */
@Service
public class StudentService {

	@Autowired
	private StudentJpaDao studentJpaDao;

	@PersistenceContext
	private HibernateEntityManager em;

	/**
	 *
	 * @param id
	 * @return
	 */
	public Student get(Integer id) {
		return studentJpaDao.getOne(id);
	}

	public Page<Student> getAllPage(Pageable pageable) {
		return studentJpaDao.findAll(pageable);
	}

	/**
	 * @param map
	 * @param student
	 * @return
	 */
	public List<Student> queryByParameters(Map<String, Object> map, Student student) {

		StringBuilder hql = new StringBuilder("from Student student where 1 = 1 ");
		for (String key : map.keySet()) {
			String condition = " and student." + key + " = :" + key + " ";
			hql.append(condition);
		}
		Query query = em.getSession().createQuery(hql.toString());
		query.setProperties(student);
		return query.list();
	}

	/**
	 * @param entity
	 * @return
	 */
	public Student add(Student entity) {
		return studentJpaDao.save(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	public Student update(Student entity) {
		entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return studentJpaDao.save(entity);
	}

	/**
	 * @param id
	 */
	public void delete(Integer id) {
		studentJpaDao.deleteById(id);
	}

}
