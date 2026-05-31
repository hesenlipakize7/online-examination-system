package online_examination_system.model.entity;

import jakarta.persistence.*;
import lombok.*;
import online_examination_system.model.enums.ExamStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Enumerated(EnumType.STRING)
    private ExamStatus status;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer easyScore;

    private Integer mediumScore;

    private Integer hardScore;

    @ManyToOne
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "exam_questions",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question>  questions=new HashSet<>();

}
