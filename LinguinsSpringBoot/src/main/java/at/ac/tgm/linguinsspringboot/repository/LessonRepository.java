package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByDifficulty(String difficulty);
}
