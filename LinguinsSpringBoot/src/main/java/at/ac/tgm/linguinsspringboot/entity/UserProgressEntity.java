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
@Data
public class UserProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Integer percentage;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private LessonEntity lesson;
}