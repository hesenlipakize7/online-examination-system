package online_examination_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import online_examination_system.model.enums.ExamStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExamResponse {

    private Long id;

    private String title;

    private ExamStatus status;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer easyScore;

    private Integer mediumScore;

    private Integer hardScore;

    private String createdBy;
}
