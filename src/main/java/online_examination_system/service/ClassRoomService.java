package online_examination_system.service;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.CreateClassRequest;
import online_examination_system.dto.request.UpdateClassRequest;
import online_examination_system.dto.response.ClassResponse;
import online_examination_system.exceptions.ClassAlreadyExistsException;
import online_examination_system.mapper.ClassMapper;
import online_examination_system.model.entity.ClassRoom;
import online_examination_system.model.entity.User;
import online_examination_system.model.enums.Role;
import online_examination_system.repository.ClassRoomRepository;
import online_examination_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final UserRepository userRepository;

    public ClassResponse create(
            CreateClassRequest request
    ) {
        if (classRoomRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Class already exists"
            );
        }
        ClassRoom classroom = ClassRoom.builder()
                .name(request.getName())
                .build();
        ClassRoom saved = classRoomRepository.save(classroom);
        ClassRoom fresh = classRoomRepository.findById(saved.getId()).orElseThrow();
        return ClassMapper.toResponse(fresh);
    }

    public List<ClassResponse> getAll() {

        return classRoomRepository.findAll()
                .stream()
                .map(ClassMapper::toResponse)
                .toList();
    }

    public ClassResponse update(
            Long id,
            UpdateClassRequest request
    ) {

        ClassRoom classroom =
                classRoomRepository.findById(id)
                        .orElseThrow();

        classroom.setName(request.getName());

        classRoomRepository.save(classroom);

        return ClassMapper.toResponse(classroom);
    }

    public void delete(Long id) {

        classRoomRepository.deleteById(id);
    }
    public ClassResponse addStudent(Long classId, Long studentId) {
        ClassRoom classRoom = classRoomRepository
                .findById(classId)
                .orElseThrow(() ->
                        new RuntimeException("Class not found"));

        User student = userRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException(
                    "User is not a student"
            );
        }
        classRoom.getStudents().add(student);
        classRoomRepository.save(classRoom);
        return ClassMapper.toResponse(classRoom);
    }
    public ClassResponse addTeacher(Long classId, Long teacherId) {
        ClassRoom classRoom = classRoomRepository
                .findById(classId)
                .orElseThrow(() ->
                        new RuntimeException("Class not found"));

        User teacher = userRepository
                .findById(teacherId)
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new RuntimeException(
                    "User is not a teacher"
            );
        }
        classRoom.getTeachers().add(teacher);
        classRoomRepository.save(classRoom);
        return ClassMapper.toResponse(classRoom);
    }
}
