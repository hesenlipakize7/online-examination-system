package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SubmitExamRequest {
    private Long examId;

    private List<StudentAnswerRequest> answers;
}
