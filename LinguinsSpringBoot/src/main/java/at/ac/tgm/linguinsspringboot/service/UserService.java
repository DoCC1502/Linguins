package at.ac.tgm.linguinsspringboot.service;

import at.ac.tgm.linguinsspringboot.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Erstellt einen neuen User.
     * createdAt wird automatisch gesetzt.
     * role kann default „USER“ sein.
     *
     * @param dto UserDto ohne ID
     * @return gespeicherter UserDto
     */
    UserDto create(UserDto dto);

    /**
     * Aktualisiert einen bestehenden User.
     * ID darf nicht null sein.
     *
     * @param dto UserDto mit ID
     * @return aktualisierter UserDto
     */
    UserDto update(UserDto dto);

    /**
     * Liefert einen User anhand der ID.
     *
     * @param id User-ID
     * @return Optional mit UserDto
     */
    Optional<UserDto> getById(Long id);

    /**
     * Liefert einen User anhand der E-Mail.
     *
     * @param email E-Mail des Users
     * @return Optional mit UserDto
     */
    Optional<UserDto> getByEmail(String email);

    /**
     * Liefert einen User anhand des Usernames.
     *
     * @param username Username
     * @return Optional mit UserDto
     */
    Optional<UserDto> getByUsername(String username);

    /**
     * Liefert alle User.
     *
     * @return Liste aller UserDto
     */
    List<UserDto> getAll();

    /**
     * Löscht einen User anhand der ID.
     *
     * @param id User-ID
     */
    void delete(Long id);
}
