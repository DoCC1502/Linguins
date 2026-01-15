package at.ac.tgm.linguinsspringboot.service;

import at.ac.tgm.linguinsspringboot.dto.LessonContentDto;

import java.util.Optional;

public interface LessonContentService {

    /**
     * Erstellt neuen Content für eine Lesson.
     * Pro Lesson darf nur ein Content existieren.
     *
     * @param dto LessonContentDto ohne ID
     * @return gespeicherter LessonContentDto
     */
    LessonContentDto create(LessonContentDto dto);

    /**
     * Aktualisiert bestehenden Lesson Content.
     *
     * @param dto LessonContentDto mit ID
     * @return aktualisierter LessonContentDto
     */
    LessonContentDto update(LessonContentDto dto);

    /**
     * Liefert Lesson Content anhand der Content-ID.
     *
     * @param id Content-ID
     * @return Optional mit LessonContentDto
     */
    Optional<LessonContentDto> getById(Long id);

    /**
     * Liefert Lesson Content zu einer bestimmten Lesson.
     *
     * @param lessonId ID der Lesson
     * @return Optional mit LessonContentDto
     */
    Optional<LessonContentDto> getByLessonId(Long lessonId);

    /**
     * Löscht Lesson Content.
     *
     * @param id Content-ID
     */
    void delete(Long id);
}
