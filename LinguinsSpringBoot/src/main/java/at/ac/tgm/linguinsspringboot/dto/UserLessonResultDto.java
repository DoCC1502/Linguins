package at.ac.tgm.linguinsspringboot.dto;

import lombok.Data;
import java.time.LocalDateTime;
/**
 * DTO für User lessons
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Data
public class UserLessonResultDto {
    private Long id;
    private Long userId;
    private Long lessonId;
    private Integer score;
    private Boolean passed;
    private LocalDateTime completedAt;
}