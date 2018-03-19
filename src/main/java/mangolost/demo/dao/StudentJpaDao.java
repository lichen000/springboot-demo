package mangolost.demo.dao;

import mangolost.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by chen.li200 on 2018-03-19
 */
@Transactional
public interface StudentJpaDao extends JpaRepository<Student, Integer> {

}
