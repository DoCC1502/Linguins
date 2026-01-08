package at.ac.tgm.linguinsspringboot.dto;

import lombok.Data;
import java.time.LocalDateTime;
/**
 * DTO für User
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String role;
}