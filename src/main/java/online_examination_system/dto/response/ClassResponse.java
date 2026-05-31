package online_examination_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassResponse {
    private Long id;

    private String name;

    private Integer studentCount;

    private Integer teacherCount;
}
