import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StudentDaoImpl implements StudentDao {
	private SessionFactory factory;
	private EntityManagerFactory emfactory;

	public EntityManagerFactory getEmfactory() {
		return emfactory;
	}

	public void setEmfactory(EntityManagerFactory emfactory) {
		this.emfactory = emfactory;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	private static final Logger log = Logger.getLogger(StudentDaoImpl.class);

	@Override
	public void addStudent(Student student) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(student);
			transaction.commit();
			log.info("Student added successfully");
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteStudent(int rollNo) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Student delStudent = session.get(Student.class, rollNo);
			session.delete(delStudent);
			transaction.commit();
			log.info("Student deleted successfully");
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void listStudents() {
		Session session = factory.openSession();
		try {
			// List<Student> students = session.createQuery("FROM Student").list(); *Student
			// is Entity
			CriteriaQuery<Student> criteriaQuery = session.getCriteriaBuilder().createQuery(Student.class);
			criteriaQuery.from(Student.class);
			List<Student> students = session.createQuery(criteriaQuery).getResultList();
			log.info("Student Details: ");
			for (Student student : students) {
				log.info(student.getName() + " " + student.getRollNo() + " " + student.getMarks());
				List<Address> addresses = student.getAddresses();
				for (Address address : addresses)
					log.info("Address: " + address.getStreet() + " " + address.getCity() + " " + address.getState());
			}
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void updateStudent(int rollNo, double marks) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Student updateStudent = session.get(Student.class, rollNo);
			updateStudent.setMarks(marks);
			session.update(updateStudent);
			transaction.commit();
			log.info("Student data updated");
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void failedStudents() {
		Session session = factory.openSession();
		try {
//			Criteria criteria = session.createCriteria(Student.class);
//			criteria.add(Restrictions.le("marks", 35.0));
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			CriteriaBuilder cbuilder = em.getCriteriaBuilder();
			AbstractQuery<Student> cquery = cbuilder.createQuery(Student.class);
			Root<Student> root = cquery.from(Student.class);
			cquery.where(cbuilder.lessThan(root.get("marks"), 35));
			CriteriaQuery<Student> select = ((CriteriaQuery<Student>) cquery).select(root);
			TypedQuery<Student> criteria = em.createQuery(select);
			List<Student> students = criteria.getResultList();
			log.info(students.size());
			log.info("Student Details");
			for (Student student : students) {
				log.info(student.getName() + " " + student.getRollNo() + " " + student.getMarks());
			}
		} catch (HibernateException e) {

		} finally {
			session.close();
		}
	}

	@Override
	public void asendingList() {
		Session session = factory.openSession();
		try {
//			Criteria criteria = session.createCriteria(Student.class);
//			criteria.addOrder(Order.desc("marks"));
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			CriteriaBuilder cbuilder = em.getCriteriaBuilder();
			CriteriaQuery<Student> cquery = cbuilder.createQuery(Student.class);
			Root<Student> root = cquery.from(Student.class);
			cquery.orderBy(cbuilder.desc(root.get("marks")));
			CriteriaQuery<Student> select = cquery.select(root);
			TypedQuery<Student> criteria = em.createQuery(select);
			List<Student> students = criteria.getResultList();
			log.info("Student Details");
			for (Student student : students) {
				log.info(student.getName() + " " + student.getRollNo() + " " + student.getMarks());
			}
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void studentsName() {
		Session session = factory.openSession();
		try {
//			Criteria criteria = session.createCriteria(Student.class);
//			criteria.setProjection(Projections.property("name"));
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			CriteriaQuery<Student> cquery = em.getCriteriaBuilder().createQuery(Student.class);
			Root<Student> root = cquery.from(Student.class);
			cquery.select(root.get("name"));
			CriteriaQuery<Student> select = cquery.select(root);
			TypedQuery<Student> criteria = em.createQuery(select);
			List<Student> students = criteria.getResultList();
			log.info("Student Details");
			for (Student student : students) {
				log.info(student.getName());
			}
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void addProject(List<Employee> employees, List<Project> projects) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (Employee employee : employees) {
				employee.setProjects(projects);
				session.save(employee);
			}
			for (Project project : projects) {
				session.save(project);
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
	}
}
