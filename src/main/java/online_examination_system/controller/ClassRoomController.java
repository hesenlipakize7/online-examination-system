package online_examination_system.controller;

import lombok.RequiredArgsConstructor;
import online_examination_system.dto.request.AddStudentRequest;
import online_examination_system.dto.request.AddTeacherRequest;
import online_examination_system.dto.request.CreateClassRequest;
import online_examination_system.dto.request.UpdateClassRequest;
import online_examination_system.dto.response.ClassResponse;
import online_examination_system.service.ClassRoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    @GetMapping
    @PreAuthorize(
            "hasRole('ADMIN') or hasRole('TEACHER')"
    )
    public List<ClassResponse> getAll() {

        return classRoomService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ClassResponse create(
            @RequestBody CreateClassRequest request
    ) {

        return classRoomService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ClassResponse update(@PathVariable Long id, @RequestBody UpdateClassRequest request) {
        return classRoomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        classRoomService.delete(id);
    }

    @PostMapping("/{id}/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ClassResponse addStudent(@PathVariable Long id,@RequestBody AddStudentRequest request) {
        return classRoomService.addStudent(id,request.getStudentId());
    }

    @PostMapping("/{id}/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public ClassResponse addTeacher(@PathVariable Long id, @RequestBody AddTeacherRequest request) {
        return classRoomService.addTeacher(id, request.getTeacherId()
        );
    }
}
