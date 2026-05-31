package online_examination_system.repository;

import online_examination_system.model.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    boolean existsByName(String name);

}
