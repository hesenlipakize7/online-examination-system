package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;
import online_examination_system.model.enums.Difficulty;
import online_examination_system.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
public class CreateQuestionRequest {
    private String text;

    private QuestionType type;

    private Difficulty difficulty;

    private List<CreateOptionRequest> options;

}
