package online_examination_system.controller;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.AddQuestionRequest;
import online_examination_system.dto.request.CreateExamRequest;
import online_examination_system.dto.request.UpdateExamRequest;
import online_examination_system.dto.response.ExamResponse;
import online_examination_system.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ExamResponse create(@RequestBody CreateExamRequest request, Authentication authentication) {
        return examService.create(request, authentication.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public List<ExamResponse> getMyExams(Authentication authentication) {
        return examService.getMyExams(authentication.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ExamResponse update(@PathVariable Long id, @RequestBody UpdateExamRequest request, Authentication authentication) {
        return examService.update(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        examService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/questions")
    @PreAuthorize("hasRole('TEACHER')")
    public ExamResponse addQuestion(@PathVariable Long id, @RequestBody AddQuestionRequest request, Authentication authentication) {
        System.out.println("ADD QUESTION HIT");
        return examService.addQuestion(id, request, authentication.getName());
    }

    @PostMapping("/{id}/start")
    @PreAuthorize("hasRole('TEACHER')")
    public ExamResponse startExam(@PathVariable Long id, Authentication authentication) {
        return examService.startExam(id, authentication.getName());
    }
}
