package online_examination_system.mapper;

import online_examination_system.dto.response.ClassResponse;
import online_examination_system.model.entity.ClassRoom;

public class ClassMapper {
    public static ClassResponse toResponse(
            ClassRoom classroom
    ) {

        return ClassResponse.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .studentCount(
                        classroom.getStudents().size()
                )
                .teacherCount(
                        classroom.getTeachers().size()
                )
                .build();
    }
}
