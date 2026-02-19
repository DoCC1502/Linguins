package at.ac.tgm.linguinsspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
/**
 * Entity präsentiert den Fortschritt eines Users in einer Lektion.
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Entity
@Table(name = "user_progress")
@Data
public class UserProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private Integer percentage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;
}
