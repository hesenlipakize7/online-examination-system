package online_examination_system.service;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.StudentAnswerRequest;
import online_examination_system.dto.request.SubmitExamRequest;
import online_examination_system.dto.response.ExamResponse;
import online_examination_system.dto.response.ExamResultResponse;
import online_examination_system.dto.response.QuestionResponse;
import online_examination_system.dto.response.StudentAnswerResultResponse;
import online_examination_system.mapper.ExamMapper;
import online_examination_system.mapper.QuestionMapper;
import online_examination_system.model.entity.*;
import online_examination_system.model.enums.ExamStatus;
import online_examination_system.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentExamService {
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final StudentAnswerRepository studentAnswerRepository;

    public List<ExamResponse> getAvailableExams() {
        return examRepository.findAllByStatus(ExamStatus.ACTIVE)
                .stream()
                .map(ExamMapper::toResponse)
                .toList();
    }

    public String startExam(Long examId, String email) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (exam.getStatus() != ExamStatus.ACTIVE) {
            throw new RuntimeException("Exam is not active");
        }
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        boolean alreadyStarted = examResultRepository
                .findByExamAndStudent(exam, student).isPresent();
        if (alreadyStarted) {
            throw new RuntimeException("You have already started this exam");
        }
        ExamResult examResult = ExamResult.builder()
                .exam(exam)
                .student(student)
                .startedAt(LocalDateTime.now())
                .submitted(false)
                .completed(false)
                .totalScore(0)
                .build();
        examResultRepository.save(examResult);
        return "Exam started successfully";
    }

    public List<QuestionResponse> getExamQuestions(Long examId, String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        examResultRepository.findByExamAndStudent(exam, student)
                .orElseThrow(() -> new RuntimeException("Exam not started yet"));

        List<Long> ids = exam.getQuestions()
                .stream()
                .map(Question::getId)
                .toList();

        List<Question> questions = questionRepository.findAllWithOptions(ids);

        return questions.stream()
                .map(QuestionMapper::toResponse)
                .toList();
    }

    public ExamResultResponse submitExam(SubmitExamRequest request, String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        ExamResult examResult = examResultRepository
                .findByExamAndStudent(exam, student)
                .orElseThrow(() -> new RuntimeException("Exam not started"));

        int score = 0;

        List<StudentAnswerResultResponse> responseList = new ArrayList<>();

        for (StudentAnswerRequest req : request.getAnswers()) {

            Question question = questionRepository.findById(req.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            QuestionOptions selected = question.getOptions().stream()
                    .filter(o -> o.getId().equals(req.getOptionId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Option not found"));

            QuestionOptions correct = question.getOptions().stream()
                    .filter(QuestionOptions::isCorrect)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Correct option not found"));

            boolean isCorrect = selected.getId().equals(correct.getId());

            if (isCorrect) {
                score++;
            }
            StudentAnswer studentAnswer = StudentAnswer.builder()
                    .examResult(examResult)
                    .question(question)
                    .selectedOption(selected)
                    .build();

            studentAnswerRepository.save(studentAnswer);

            responseList.add(StudentAnswerResultResponse.builder()
                    .questionId(question.getId())
                    .questionText(question.getText())
                    .selectedOptionId(selected.getId())
                    .selectedOptionText(selected.getOptionText())
                    .correctOptionId(correct.getId())
                    .correctOptionText(correct.getOptionText())
                    .correct(isCorrect)
                    .build());
        }

        examResult.setTotalScore(score);
        examResult.setSubmitted(true);
        examResult.setSubmittedAt(LocalDateTime.now());

        examResultRepository.save(examResult);

        return ExamResultResponse.builder()
                .examId(exam.getId())
                .totalScore(score)
                .answers(responseList)
                .build();
    }

    public ExamResultResponse getExamResult(Long examId, String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        ExamResult examResult = examResultRepository
                .findByExamAndStudent(exam, student)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        List<StudentAnswerResultResponse> resultList = new ArrayList<>();

        for (StudentAnswer ans : examResult.getAnswers()) {

            Question question = ans.getQuestion();
            QuestionOptions selected = ans.getSelectedOption();

            QuestionOptions correct = question.getOptions()
                    .stream()
                    .filter(QuestionOptions::isCorrect)
                    .findFirst()
                    .orElse(null);

            resultList.add(StudentAnswerResultResponse.builder()
                    .questionId(question.getId())
                    .questionText(question.getText())

                    .selectedOptionId(selected.getId())
                    .selectedOptionText(selected.getOptionText())

                    .correctOptionId(correct != null ? correct.getId() : null)
                    .correctOptionText(correct != null ? correct.getOptionText() : null)

                    .correct(selected.getId().equals(correct.getId()))
                    .build());
        }

        return ExamResultResponse.builder()
                .examId(exam.getId())
                .totalScore(examResult.getTotalScore())
                .answers(resultList)
                .build();
    }

}
