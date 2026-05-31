package online_examination_system.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online_examination_system.dto.response.ExamResponse;
import online_examination_system.model.entity.Exam;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExamMapper {

    public static ExamResponse toResponse(Exam exam) {

        return ExamResponse.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .status(exam.getStatus())
                .startTime(exam.getStartTime())
                .duration(exam.getDuration())
                .easyScore(exam.getEasyScore())
                .mediumScore(exam.getMediumScore())
                .hardScore(exam.getHardScore())
                .createdBy(exam.getTeacher().getEmail())
                .build();
    }
}
