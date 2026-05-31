package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAnswerRequest {
    private Long questionId;
    private Long optionId;
}
