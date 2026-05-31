package online_examination_system.controller;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.CreateQuestionRequest;
import online_examination_system.dto.response.QuestionResponse;
import online_examination_system.service.QuestionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public List<QuestionResponse> getMyQuestions(Authentication authentication) {
        return questionService.getMyQuestions(authentication.getName());
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public QuestionResponse create(@RequestBody CreateQuestionRequest request, Authentication authentication) {
        return questionService.create(request, authentication.getName());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public void delete(@PathVariable Long id, Authentication authentication) {
        questionService.delete(id, authentication.getName());
    }
}
