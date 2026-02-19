package at.ac.tgm.linguinsspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * Entity präsentiert das Ergebnis eines Users für eine Lektion.
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Entity
@Table(name = "user_lesson_result")
@Data
public class UserLessonResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;
    private Boolean passed;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id") // FK-Spalte
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lesson_id") // FK-Spalte
    private LessonEntity lesson;
}
