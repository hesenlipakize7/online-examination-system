package online_examination_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExamRequest {

    @NotBlank
    private String title;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer easyScore;

    @NotNull
    private Integer mediumScore;

    @NotNull
    private Integer hardScore;
}
