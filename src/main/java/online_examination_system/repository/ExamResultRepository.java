package online_examination_system.repository;

import online_examination_system.model.entity.Exam;
import online_examination_system.model.entity.ExamResult;
import online_examination_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    boolean existsByExamAndStudent(Exam exam, User student);
    Optional<ExamResult> findByExamAndStudent(Exam exam, User student);

    List<ExamResult> findByStudent(User student);
}
