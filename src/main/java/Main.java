import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static Scanner sc;
	private static final Logger log = Logger.getLogger(Main.class);
	private static AbstractApplicationContext context;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		sc = new Scanner(System.in);
		context = new ClassPathXmlApplicationContext("spring.xml");
		StudentDao dao = context.getBean("studentDao", StudentDao.class);
		int choice;
		do {
			log.info("Enter 1 to add a student");
			log.info("Enter 2 to update a student marks");
			log.info("Enter 3 to delete a student");
			log.info("Enter 4 to view all students");
			log.info("Enter 5 to see failed students");
			log.info("Enter 6 to see students in ascending order of their marks");
			log.info("Enter 7 to see all students name");
			log.info("Enter 8 to exit");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				Student student = context.getBean("student", Student.class);
				log.info("Enter name of student");
				student.setName(sc.next());
				log.info("Enter marks of student: ");
				student.setMarks(sc.nextDouble());
				log.info("Enter the number of addresses of student:");
				int n = sc.nextInt();
				List<Address> addresses = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					Address address = new Address();
					log.info("Enter street: ");
					address.setStreet(sc.next());
					log.info("Enter city: ");
					address.setCity(sc.next());
					log.info("Enter State: ");
					address.setState(sc.next());
					address.setStudent(student);
					addresses.add(address);
				}
				student.setAddresses(addresses);
				dao.addStudent(student);
				break;
			case 2:
				log.info("Enter Roll number of student: ");
				int rollNo = sc.nextInt();
				log.info("Enter marks of student: ");
				double marks = sc.nextDouble();
				dao.updateStudent(rollNo, marks);
				break;
			case 3:
				log.info("Enter Roll number of student: ");
				int delRollNo = sc.nextInt();
				dao.deleteStudent(delRollNo);
				break;
			case 4:
				log.info("All student data:");
				dao.listStudents();
				break;
			case 5:
				log.info("Failed students:");
				dao.failedStudents();
				break;
			case 6:
				log.info("Descending order list of students:");
				dao.asendingList();
				break;
			case 7:
				log.info("All students name:");
				dao.studentsName();
				break;
			case 8:
				break;
			default:
				log.info("Please enter a valid number(1-5)");
			}
		} while (choice != 8);
//		List<Employee> employees = new ArrayList<>();
//		Employee emp1 = context.getBean("employee", Employee.class);
//		emp1.setName("Jay");
//		Employee emp2 = context.getBean("employee", Employee.class);
//		emp2.setName("Raj");
//		Employee emp3 = context.getBean("employee", Employee.class);
//		emp3.setName("Ram");
//		employees.add(emp1);
//		employees.add(emp2);
//		employees.add(emp3);
//		List<Project> projects = new ArrayList<>();
//		Project p1 = context.getBean("project", Project.class);
//		p1.setName("Demo Project");
//		Project p2 = context.getBean("project", Project.class);
//		p2.setName("CRUD Project");
//		projects.add(p1);
//		projects.add(p2);
//		dao.addProject(employees, projects);
		context.close();
	}
}
