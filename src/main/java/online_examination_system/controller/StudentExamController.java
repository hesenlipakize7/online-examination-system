package online_examination_system.controller;

import lombok.RequiredArgsConstructor;

import online_examination_system.dto.request.SubmitExamRequest;
import online_examination_system.dto.response.ExamResponse;
import online_examination_system.dto.response.ExamResultResponse;
import online_examination_system.dto.response.QuestionResponse;

import online_examination_system.service.StudentExamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/exams")
@RequiredArgsConstructor
public class StudentExamController {
    private final StudentExamService studentExamService;


    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<ExamResponse> getAvailableExams() {
        return studentExamService.getAvailableExams();
    }

    @PostMapping("/{id}/start")
    @PreAuthorize("hasRole('STUDENT')")
    public String startExam(@PathVariable Long id, Authentication authentication) {
        return studentExamService.startExam(id, authentication.getName());
    }

    @GetMapping("/{id}/questions")
    public List<QuestionResponse> getExamQuestions(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return studentExamService.getExamQuestions(id, userDetails.getUsername());
    }

    @PostMapping("/submit")
    public ExamResultResponse submit(
            @RequestBody SubmitExamRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        return studentExamService.submitExam(request, user.getUsername());
    }

    @GetMapping("/{examId}/result")
    public ExamResultResponse getResult(@PathVariable Long examId, @AuthenticationPrincipal UserDetails user) {
        return studentExamService.getExamResult(examId, user.getUsername());
    }



}
