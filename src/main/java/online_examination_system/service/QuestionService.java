package online_examination_system.service;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.CreateOptionRequest;
import online_examination_system.dto.request.CreateQuestionRequest;
import online_examination_system.dto.response.QuestionResponse;
import online_examination_system.mapper.QuestionMapper;
import online_examination_system.model.entity.Question;
import online_examination_system.model.entity.QuestionOptions;
import online_examination_system.model.entity.User;
import online_examination_system.model.enums.QuestionType;
import online_examination_system.repository.QuestionRepository;
import online_examination_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionResponse create(CreateQuestionRequest request, String email) {
        if (request.getType() == QuestionType.TEST &&
            (request.getOptions() == null ||
             request.getOptions().size() != 4)) {

            throw new RuntimeException(
                    "Test question must contain 4 options"
            );
        }
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Question question = new Question();
        question.setText(request.getText());
        question.setType(request.getType());
        question.setDifficulty(request.getDifficulty());
        question.setCreatedBy(teacher);

        List<QuestionOptions> options = new ArrayList<>();

        if (request.getType() == QuestionType.TEST) {
            for (CreateOptionRequest optionRequest : request.getOptions()) {
                QuestionOptions option = new QuestionOptions();
                option.setOptionKey(optionRequest.getOptionKey());
                option.setOptionText(optionRequest.getOptionText());
                option.setCorrect(optionRequest.isCorrect());
                option.setQuestion(question);
                options.add(option);
            }
        }
        question.setOptions(options);
        Question savedQuestion = questionRepository.save(question);
        return QuestionMapper.toResponse(savedQuestion);
    }

    public List<QuestionResponse> getMyQuestions(String email) {
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return questionRepository.findAllByCreatedBy(teacher)
                .stream()
                .map(QuestionMapper::toResponse)
                .toList();
    }

    public void delete(Long id, String email) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if (!question.getCreatedBy().getEmail().equals(email)) {
            throw new RuntimeException("You can delete only your own questions");
        }
        questionRepository.delete(question);
    }
}
