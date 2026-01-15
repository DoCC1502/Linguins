package at.ac.tgm.linguinsspringboot.service.impl;

import at.ac.tgm.linguinsspringboot.converter.LinguinsMapper;
import at.ac.tgm.linguinsspringboot.dto.UserDto;
import at.ac.tgm.linguinsspringboot.entity.UserEntity;
import at.ac.tgm.linguinsspringboot.repository.UserRepository;
import at.ac.tgm.linguinsspringboot.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinguinsMapper mapper;

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id muss null sein");
        }

        // Default-Werte
        if (dto.getCreatedAt() == null) {
            dto.setCreatedAt(LocalDateTime.now());
        }
        if (dto.getRole() == null) {
            dto.setRole("USER");
        }

        UserEntity entity = mapper.toEntity(dto);
        UserEntity saved = userRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public UserDto update(UserDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id darf nicht null sein");
        }

        return userRepository.findById(dto.getId())
                .map(entity -> {
                    entity.setUsername(dto.getUsername());
                    entity.setEmail(dto.getEmail());
                    entity.setPassword(dto.getPassword());
                    entity.setRole(dto.getRole() != null ? dto.getRole() : "USER");
                    entity.setLastLogin(dto.getLastLogin());
                    return mapper.toDto(userRepository.save(entity));
                })
                .orElseThrow(() -> new NoSuchElementException("User mit id " + dto.getId() + " nicht gefunden"));
    }

    @Transactional
    @Override
    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id).map(mapper::toDto);
    }

    @Transactional
    @Override
    public Optional<UserDto> getByEmail(String email) {
        return userRepository.findByEmail(email).map(mapper::toDto);
    }

    @Transactional
    @Override
    public Optional<UserDto> getByUsername(String username) {
        return userRepository.findByUsername(username).map(mapper::toDto);
    }

    @Transactional
    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User mit id " + id + " nicht gefunden");
        }
        userRepository.deleteById(id);
    }
}
