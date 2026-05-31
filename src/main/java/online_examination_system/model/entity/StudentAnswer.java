package online_examination_system.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_answers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ExamResult examResult;

    @ManyToOne
    private Question question;

    @ManyToOne
    private QuestionOptions selectedOption;
}
