package at.ac.tgm.linguinsspringboot.service;

import at.ac.tgm.linguinsspringboot.dto.UserLessonResultDto;

import java.util.List;
import java.util.Optional;

public interface UserLessonResultService {

    /**
     * Erstellt ein neues Ergebnis für eine Lesson eines Users.
     *
     * @param dto UserLessonResultDto ohne ID
     * @return gespeicherter UserLessonResultDto
     */
    UserLessonResultDto create(UserLessonResultDto dto);

    /**
     * Aktualisiert ein bestehendes Ergebnis.
     * ID muss vorhanden sein.
     *
     * @param dto UserLessonResultDto mit ID
     * @return aktualisierter UserLessonResultDto
     */
    UserLessonResultDto update(UserLessonResultDto dto);

    /**
     * Liefert ein Ergebnis anhand der Ergebnis-ID.
     *
     * @param id Ergebnis-ID
     * @return Optional mit UserLessonResultDto
     */
    Optional<UserLessonResultDto> getById(Long id);

    /**
     * Liefert alle Ergebnisse eines bestimmten Users.
     *
     * @param userId ID des Users
     * @return Liste der UserLessonResultDto
     */
    List<UserLessonResultDto> getByUserId(Long userId);

    /**
     * Liefert alle Ergebnisse einer bestimmten Lesson.
     *
     * @param lessonId ID der Lesson
     * @return Liste der UserLessonResultDto
     */
    List<UserLessonResultDto> getByLessonId(Long lessonId);

    /**
     * Liefert das Ergebnis eines Users für eine bestimmte Lesson.
     *
     * @param userId ID des Users
     * @param lessonId ID der Lesson
     * @return Optional mit UserLessonResultDto
     */
    Optional<UserLessonResultDto> getByUserAndLesson(Long userId, Long lessonId);

    /**
     * Löscht ein Ergebnis anhand der ID.
     *
     * @param id Ergebnis-ID
     */
    void delete(Long id);
}
