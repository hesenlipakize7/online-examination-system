package online_examination_system.service;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.AddQuestionRequest;
import online_examination_system.dto.request.CreateExamRequest;
import online_examination_system.dto.request.UpdateExamRequest;
import online_examination_system.dto.response.ExamResponse;
import online_examination_system.mapper.ExamMapper;
import online_examination_system.model.entity.Exam;
import online_examination_system.model.entity.Question;
import online_examination_system.model.entity.User;
import online_examination_system.model.enums.ExamStatus;
import online_examination_system.repository.ExamRepository;
import online_examination_system.repository.QuestionRepository;
import online_examination_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public ExamResponse create(CreateExamRequest request, String email) {
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .duration(request.getDuration())
                .easyScore(request.getEasyScore())
                .mediumScore(request.getMediumScore())
                .hardScore(request.getHardScore())
                .status(ExamStatus.DRAFT)
                .teacher(teacher)
                .build();

        examRepository.save(exam);
        return ExamMapper.toResponse(exam);
    }

    public List<ExamResponse> getMyExams(String email) {
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return examRepository.findAllByTeacher(teacher)
                .stream()
                .map(ExamMapper::toResponse)
                .toList();
    }

    public ExamResponse update(Long id, UpdateExamRequest request, String email) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (!exam.getTeacher().getEmail().equals(email)) {
            throw new RuntimeException("You can update only your own exams");
        }
        if (exam.getStatus() != ExamStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT exams can be updated");
        }
        exam.setTitle(request.getTitle());
        exam.setDuration(request.getDuration());
        exam.setEasyScore(request.getEasyScore());
        exam.setMediumScore(request.getMediumScore());
        exam.setHardScore(request.getHardScore());
        examRepository.save(exam);
        return ExamMapper.toResponse(exam);
    }

    public void delete(Long id, String email) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (!exam.getTeacher().getEmail().equals(email)) {
            throw new RuntimeException("You can delete only your own exams");
        }
        if (exam.getStatus() != ExamStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT exams can be deleted");
        }
        examRepository.delete(exam);
    }

    public ExamResponse addQuestion(Long examId, AddQuestionRequest request, String email) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (!exam.getTeacher().getEmail().equals(email)) {
            throw new RuntimeException("You can modify only your own exams");
        }
        if (exam.getStatus() != ExamStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT exams can be modified");
        }
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        exam.getQuestions().add(question);
        examRepository.save(exam);
        return ExamMapper.toResponse(exam);
    }

    public ExamResponse startExam(Long examId, String email) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (!exam.getTeacher().getEmail().equals(email)) {
            throw new RuntimeException("You can start only your own exams");
        }
        if (exam.getStatus() != ExamStatus.DRAFT) {
            throw new RuntimeException("Exam is already started");
        }
        exam.setStatus(ExamStatus.ACTIVE);
        exam.setStartTime(LocalDateTime.now());
        examRepository.save(exam);
        return ExamMapper.toResponse(exam);
    }
}
