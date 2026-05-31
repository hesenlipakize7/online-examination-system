package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateExamRequest {

    private String title;

    private Integer duration;

    private Integer easyScore;

    private Integer mediumScore;

    private Integer hardScore;
}
