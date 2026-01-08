package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.UserLessonResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLessonResultRepository extends JpaRepository<UserLessonResultEntity, Long> {
}
