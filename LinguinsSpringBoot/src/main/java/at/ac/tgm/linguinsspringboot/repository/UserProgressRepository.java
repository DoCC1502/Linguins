package at.ac.tgm.linguinsspringboot.repository;

import at.ac.tgm.linguinsspringboot.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {
}
