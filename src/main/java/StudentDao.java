import java.util.List;

public interface StudentDao {
	void addStudent(Student student);

	void updateStudent(int rollNo, double marks);

	void deleteStudent(int rollNo);

	void listStudents();
	
	void failedStudents();
	
	void asendingList();
	
	void studentsName();
	
	void addProject(List<Employee> employess, List<Project> projects);
}
