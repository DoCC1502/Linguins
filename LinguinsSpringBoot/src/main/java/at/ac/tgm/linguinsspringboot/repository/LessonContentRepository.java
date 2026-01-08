package at.ac.tgm.linguinsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import at.ac.tgm.linguinsspringboot.entity.LessonContentEntity;

public interface LessonContentRepository extends JpaRepository<LessonContentEntity, Long> {
}
