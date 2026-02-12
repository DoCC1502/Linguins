//package at.ac.tgm.linguinsspringboot.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
///**
// * Entity präsentiert den Inhalt einer Lektion.
// *
// * @author LinguinsTeam
// * @version 18-12-2025
// */
//@Entity
//@Data
//@Table(name = "lesson_content")
//public class LessonContentEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String content;
//
//    @OneToOne
//    private LessonEntity lesson;
//}


package at.ac.tgm.linguinsspringboot.entity;

import jakarta.persistence.*;
        import lombok.Data;

/**
 * Entity präsentiert den Inhalt einer Lektion.
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Entity
@Data
@Table(name = "lesson_content")
public class LessonContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id") // <- hier wird die Spalte gemappt
    private LessonEntity lesson;
}
