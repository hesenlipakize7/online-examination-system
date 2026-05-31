package online_examination_system.dto.request;

import lombok.Getter;
import lombok.Setter;
import online_examination_system.model.enums.Role;

@Getter
@Setter
public class UserCreateRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
