package online_examination_system.mapper;


import online_examination_system.dto.response.QuestionResponse;
import online_examination_system.model.entity.Question;


import java.util.List;

public class QuestionMapper {

    public static QuestionResponse toResponse(Question question) {

                return QuestionResponse.builder()
                        .id(question.getId())
                        .text(question.getText())
                        .options(
                                question.getOptions() == null
                                        ? List.of()
                                        : question.getOptions().stream()
                                        .map(opt -> opt.getOptionText())
                                        .toList()
                        )
                        .build();
            }
}
