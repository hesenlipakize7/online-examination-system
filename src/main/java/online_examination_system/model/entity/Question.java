package online_examination_system.model.entity;

import jakarta.persistence.*;
import lombok.*;
import online_examination_system.model.enums.Difficulty;
import online_examination_system.model.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String text;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User createdBy;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionOptions> options = new ArrayList<>();
}
