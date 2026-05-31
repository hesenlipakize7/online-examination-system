package online_examination_system.repository;

import online_examination_system.model.entity.Exam;
import online_examination_system.model.entity.User;
import online_examination_system.model.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByTeacher(User teacher);
    List<Exam> findAllByStatus(ExamStatus status);

}
