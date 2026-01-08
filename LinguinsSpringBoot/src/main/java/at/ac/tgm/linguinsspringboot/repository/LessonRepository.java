package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
}
