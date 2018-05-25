package mangolost.demo.service;

import mangolost.demo.dao.StudentJpaDao;
import mangolost.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by chen.li200 on 2018-03-19
 */
@Service
public class StudentService {

	private final StudentJpaDao studentJpaDao;

	@PersistenceContext
	private EntityManager entityManager;

    @Autowired
    public StudentService(StudentJpaDao studentJpaDao) {
        System.out.println("aaaa");
        this.studentJpaDao = studentJpaDao;
    }

    /**
	 *
	 * @param id
	 * @return
	 */
	public Optional<Student> get(Integer id) {
		return studentJpaDao.findById(id);
	}

	public Page<Student> getAllPage(Pageable pageable) {
		return studentJpaDao.findAll(pageable);
	}

	/**
	 * @param map
	 * @return
	 */
	public List<Student> queryByParameters(Map<String, Object> map) {

		StringBuilder hql = new StringBuilder("from Student student where 1 = 1 ");
		for (String key : map.keySet()) {
			String condition = " and student." + key + " = :" + key + " ";
			hql.append(condition);
		}
		Query query = entityManager.createQuery(hql.toString());
        for (String key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }

        return query.getResultList();
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
