package at.ac.tgm.linguinsspringboot.dto;

import lombok.Data;
/**
 * DTO für User progress
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Data
public class UserProgressDto {
    private Long id;
    private Long userId;   // Fremdschlüssel-ID
    private Long lessonId; // Fremdschlüssel-ID
    private String status;
    private Double percentage;
}