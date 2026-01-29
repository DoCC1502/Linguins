package at.ac.tgm.linguinsspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
/**
 * Entity präsentiert eine Lektion.
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Entity
@Data
@Table(name = "Lesson")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int difficulty;
}