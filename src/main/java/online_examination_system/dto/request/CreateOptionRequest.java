package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOptionRequest {
    private String optionKey;

    private String optionText;

    private boolean correct;

}
