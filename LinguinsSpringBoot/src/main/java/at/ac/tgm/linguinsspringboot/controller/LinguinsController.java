package at.ac.tgm.linguinsspringboot.controller;

import at.ac.tgm.linguinsspringboot.dto.*;
import at.ac.tgm.linguinsspringboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/api")
@RestController
public class LinguinsController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonContentService lessonContentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProgressService userProgressService;

    @Autowired
    private UserLessonResultService userLessonResultService;

    /*
     * LESSON ENDPOINTS
     */

    @PostMapping("/lessons")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.create(dto));
    }

    @PutMapping("/lessons/{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id, @RequestBody LessonDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(lessonService.update(dto));
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Lesson mit id " + id + " nicht gefunden")));
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @GetMapping("/lessons/difficulty/{difficulty}")
    public ResponseEntity<List<LessonDto>> getLessonsByDifficulty(@PathVariable String difficulty) {
        return ResponseEntity.ok(lessonService.getByDifficulty(difficulty));
    }

    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * LESSON CONTENT ENDPOINTS
     */

    @PostMapping("/lesson-contents")
    public ResponseEntity<LessonContentDto> createLessonContent(@RequestBody LessonContentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonContentService.create(dto));
    }

    @PutMapping("/lesson-contents/{id}")
    public ResponseEntity<LessonContentDto> updateLessonContent(@PathVariable Long id, @RequestBody LessonContentDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(lessonContentService.update(dto));
    }

    @GetMapping("/lesson-contents/{id}")
    public ResponseEntity<LessonContentDto> getLessonContentById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonContentService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("LessonContent mit id " + id + " nicht gefunden")));
    }

    @GetMapping("/lesson-contents/lesson/{lessonId}")
    public ResponseEntity<LessonContentDto> getLessonContentByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonContentService.getByLessonId(lessonId)
                .orElseThrow(() -> new NoSuchElementException("LessonContent für Lesson " + lessonId + " nicht gefunden")));
    }

    @DeleteMapping("/lesson-contents/{id}")
    public ResponseEntity<Void> deleteLessonContent(@PathVariable Long id) {
        lessonContentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * USER ENDPOINTS
     */

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(userService.update(dto));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("User mit id " + id + " nicht gefunden")));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User mit email " + email + " nicht gefunden")));
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User mit username " + username + " nicht gefunden")));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * USER PROGRESS ENDPOINTS
     */

    @PostMapping("/user-progress")
    public ResponseEntity<UserProgressDto> createUserProgress(@RequestBody UserProgressDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProgressService.create(dto));
    }

    @PutMapping("/user-progress/{id}")
    public ResponseEntity<UserProgressDto> updateUserProgress(@PathVariable Long id, @RequestBody UserProgressDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(userProgressService.update(dto));
    }

    @GetMapping("/user-progress/{id}")
    public ResponseEntity<UserProgressDto> getUserProgressById(@PathVariable Long id) {
        return ResponseEntity.ok(userProgressService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("UserProgress mit id " + id + " nicht gefunden")));
    }

    @GetMapping("/user-progress/user/{userId}")
    public ResponseEntity<List<UserProgressDto>> getUserProgressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getByUserId(userId));
    }

    @GetMapping("/user-progress/lesson/{lessonId}")
    public ResponseEntity<List<UserProgressDto>> getUserProgressByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(userProgressService.getByLessonId(lessonId));
    }

    @GetMapping("/user-progress/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<UserProgressDto> getUserProgressByUserAndLesson(
            @PathVariable Long userId, @PathVariable Long lessonId) {
        return ResponseEntity.ok(userProgressService.getByUserAndLesson(userId, lessonId)
                .orElseThrow(() -> new NoSuchElementException(
                        "UserProgress für User " + userId + " und Lesson " + lessonId + " nicht gefunden")));
    }

    @DeleteMapping("/user-progress/{id}")
    public ResponseEntity<Void> deleteUserProgress(@PathVariable Long id) {
        userProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * USER LESSON RESULT ENDPOINTS
     */

    @PostMapping("/user-lesson-results")
    public ResponseEntity<UserLessonResultDto> createUserLessonResult(@RequestBody UserLessonResultDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userLessonResultService.create(dto));
    }

    @PutMapping("/user-lesson-results/{id}")
    public ResponseEntity<UserLessonResultDto> updateUserLessonResult(@PathVariable Long id, @RequestBody UserLessonResultDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(userLessonResultService.update(dto));
    }

    @GetMapping("/user-lesson-results/{id}")
    public ResponseEntity<UserLessonResultDto> getUserLessonResultById(@PathVariable Long id) {
        return ResponseEntity.ok(userLessonResultService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("UserLessonResult mit id " + id + " nicht gefunden")));
    }

    @GetMapping("/user-lesson-results/user/{userId}")
    public ResponseEntity<List<UserLessonResultDto>> getUserLessonResultsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userLessonResultService.getByUserId(userId));
    }

    @GetMapping("/user-lesson-results/lesson/{lessonId}")
    public ResponseEntity<List<UserLessonResultDto>> getUserLessonResultsByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(userLessonResultService.getByLessonId(lessonId));
    }

    @GetMapping("/user-lesson-results/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<UserLessonResultDto> getUserLessonResultByUserAndLesson(
            @PathVariable Long userId, @PathVariable Long lessonId) {
        return ResponseEntity.ok(userLessonResultService.getByUserAndLesson(userId, lessonId)
                .orElseThrow(() -> new NoSuchElementException(
                        "UserLessonResult für User " + userId + " und Lesson " + lessonId + " nicht gefunden")));
    }

    @DeleteMapping("/user-lesson-results/{id}")
    public ResponseEntity<Void> deleteUserLessonResult(@PathVariable Long id) {
        userLessonResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}