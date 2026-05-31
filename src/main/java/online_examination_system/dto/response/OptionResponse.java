package online_examination_system.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionResponse {

    private Long id;

    private String optionKey;

    private String optionText;

}
