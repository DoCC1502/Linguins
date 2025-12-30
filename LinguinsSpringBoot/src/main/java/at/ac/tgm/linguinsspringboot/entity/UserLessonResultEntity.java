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
@Data
public class UserLessonResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer score;
    private Boolean passed;
    private LocalDateTime completedAt;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private LessonEntity lesson;
}