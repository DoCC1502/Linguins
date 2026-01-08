package at.ac.tgm.linguinsspringboot.repository;


import at.ac.tgm.linguinsspringboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
