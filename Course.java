import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseNo;
    private String grade;
    private int creditHours;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


}