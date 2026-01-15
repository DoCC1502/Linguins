package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {

    List<UserProgressEntity> findByUserId(Long userId);

    List<UserProgressEntity> findByLessonId(Long lessonId);

    Optional<UserProgressEntity> findByUserIdAndLessonId(Long userId, Long lessonId);
}
