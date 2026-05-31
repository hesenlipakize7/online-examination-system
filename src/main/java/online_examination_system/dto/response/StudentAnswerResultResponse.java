package online_examination_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentAnswerResultResponse {
    private Long questionId;
    private String questionText;

    private Long selectedOptionId;
    private String selectedOptionText;

    private Long correctOptionId;
    private String correctOptionText;

    private boolean correct;
}
