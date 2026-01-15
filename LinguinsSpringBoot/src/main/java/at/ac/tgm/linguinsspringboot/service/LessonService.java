package at.ac.tgm.linguinsspringboot.service;

import at.ac.tgm.linguinsspringboot.dto.LessonDto;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    /**
     * Erstellt eine neue Lesson.
     *
     * @param dto LessonDto ohne ID
     * @return gespeicherte LessonDto
     */
    LessonDto create(LessonDto dto);

    /**
     * Aktualisiert eine bestehende Lesson.
     *
     * @param dto LessonDto mit ID
     * @return aktualisierte LessonDto
     */
    LessonDto update(LessonDto dto);

    /**
     * Liefert eine Lesson anhand der ID.
     *
     * @param id Lesson-ID
     * @return Optional mit LessonDto
     */
    Optional<LessonDto> getById(Long id);

    /**
     * Liefert alle Lessons.
     *
     * @return Liste der LessonDto
     */
    List<LessonDto> getAll();

    /**
     * Liefert alle Lessons mit einer bestimmten Schwierigkeit.
     *
     * @param difficulty Schwierigkeit als String
     * @return Liste der LessonDto
     */
    List<LessonDto> getByDifficulty(String difficulty);

    /**
     * Löscht eine Lesson anhand der ID.
     *
     * @param id Lesson-ID
     */
    void delete(Long id);
}
