package online_examination_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ExamResultResponse {
    private Long examId;
    private Integer totalScore;

    private List<StudentAnswerResultResponse> answers;
}
