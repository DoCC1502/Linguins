package at.ac.tgm.linguinsspringboot.dto;

import lombok.Data;
/**
 * DTO für Lesson
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Data
public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
}