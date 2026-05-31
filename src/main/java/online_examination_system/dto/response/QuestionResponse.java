package online_examination_system.dto.response;

import lombok.*;
import online_examination_system.model.enums.Difficulty;
import online_examination_system.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionResponse {
    private Long id;
    private String text;
    private List<String> options;
}
