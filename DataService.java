import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void saveDataFromUrl(String url) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String url = "https://hccs-advancejava.s3.amazonaws.com/student_course.json";
            List<Student> students = objectMapper.readValue(new URL(url), new TypeReference<List<Student>>() {});

            for (Student student : students) {
                List<Course> courses = student.getCourse();
                student.setCourse(null); // To prevent saving courses along with student (handled separately)
                Student savedStudent = studentRepository.save(student);

                if (courses != null) {
                    for (Course course : courses) {
                        course.setStudent(savedStudent);
                        courseRepository.save(course);
                    }
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
