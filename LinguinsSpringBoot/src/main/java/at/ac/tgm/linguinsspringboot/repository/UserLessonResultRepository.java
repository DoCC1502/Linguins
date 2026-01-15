package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.UserLessonResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLessonResultRepository extends JpaRepository<UserLessonResultEntity, Long> {

    List<UserLessonResultEntity> findByUserId(Long userId);

    List<UserLessonResultEntity> findByLessonId(Long lessonId);

    Optional<UserLessonResultEntity> findByUserIdAndLessonId(Long userId, Long lessonId);
}
