package online_examination_system.repository;

import online_examination_system.model.entity.Question;
import online_examination_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("""
            select distinct q from Question q
            left join fetch q.options
            where q.id in :ids
            """)
    List<Question> findAllWithOptions(@Param("ids") List<Long> ids);

    List<Question> findAllByCreatedBy(User teacher);

}
