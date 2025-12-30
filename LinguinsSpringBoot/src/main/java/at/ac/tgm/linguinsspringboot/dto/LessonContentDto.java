package at.ac.tgm.linguinsspringboot.dto;

import lombok.Data;

/**
 * DTO für Lesson content
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Data
public class LessonContentDto {
    private Long id;
    private Long lessonId;
    private String content;
}