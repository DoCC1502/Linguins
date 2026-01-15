package at.ac.tgm.linguinsspringboot.service;

import at.ac.tgm.linguinsspringboot.dto.UserProgressDto;

import java.util.List;
import java.util.Optional;

public interface UserProgressService {

    /**
     * Erstellt einen neuen Fortschritt für einen User in einer Lesson.
     *
     * @param dto UserProgressDto ohne ID
     * @return gespeicherter UserProgressDto
     */
    UserProgressDto create(UserProgressDto dto);

    /**
     * Aktualisiert bestehenden Fortschritt.
     * ID muss vorhanden sein.
     *
     * @param dto UserProgressDto mit ID
     * @return aktualisierter UserProgressDto
     */
    UserProgressDto update(UserProgressDto dto);

    /**
     * Liefert Fortschritt anhand der Progress-ID.
     *
     * @param id Progress-ID
     * @return Optional mit UserProgressDto
     */
    Optional<UserProgressDto> getById(Long id);

    /**
     * Liefert Fortschritt für einen bestimmten User.
     *
     * @param userId ID des Users
     * @return Liste der UserProgressDto
     */
    List<UserProgressDto> getByUserId(Long userId);

    /**
     * Liefert Fortschritt für eine bestimmte Lesson.
     *
     * @param lessonId ID der Lesson
     * @return Liste der UserProgressDto
     */
    List<UserProgressDto> getByLessonId(Long lessonId);

    /**
     * Liefert Fortschritt eines Users für eine bestimmte Lesson.
     *
     * @param userId ID des Users
     * @param lessonId ID der Lesson
     * @return Optional mit UserProgressDto
     */
    Optional<UserProgressDto> getByUserAndLesson(Long userId, Long lessonId);

    /**
     * Löscht Fortschritt anhand der ID.
     *
     * @param id Progress-ID
     */
    void delete(Long id);
}
