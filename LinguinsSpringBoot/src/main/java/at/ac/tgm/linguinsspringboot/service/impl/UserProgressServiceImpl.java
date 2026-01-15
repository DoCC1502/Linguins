package at.ac.tgm.linguinsspringboot.service.impl;

import at.ac.tgm.linguinsspringboot.converter.LinguinsMapper;
import at.ac.tgm.linguinsspringboot.dto.UserProgressDto;
import at.ac.tgm.linguinsspringboot.entity.UserProgressEntity;
import at.ac.tgm.linguinsspringboot.repository.UserProgressRepository;
import at.ac.tgm.linguinsspringboot.service.UserProgressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgressServiceImpl implements UserProgressService {

    @Autowired
    private UserProgressRepository repo;

    @Autowired
    private LinguinsMapper mapper;

    @Transactional
    @Override
    public UserProgressDto create(UserProgressDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id muss null sein");
        }

        UserProgressEntity entity = mapper.toEntity(dto);
        UserProgressEntity saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public UserProgressDto update(UserProgressDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id darf nicht null sein");
        }

        return repo.findById(dto.getId())
                .map(entity -> {
                    entity.setStatus(dto.getStatus());
                    entity.setPercentage(dto.getPercentage() != null ? dto.getPercentage().intValue() : null);
                    // User und Lesson können nur beim Erstellen gesetzt werden
                    return mapper.toDto(repo.save(entity));
                })
                .orElseThrow(() -> new NoSuchElementException(
                        "UserProgress mit id " + dto.getId() + " nicht gefunden"));
    }


    @Transactional
    @Override
    public Optional<UserProgressDto> getById(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    @Override
    public List<UserProgressDto> getByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<UserProgressDto> getByLessonId(Long lessonId) {
        return repo.findByLessonId(lessonId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<UserProgressDto> getByUserAndLesson(Long userId, Long lessonId) {
        return repo.findByUserIdAndLessonId(userId, lessonId)
                .map(mapper::toDto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("UserProgress mit id " + id + " nicht gefunden");
        }
        repo.deleteById(id);
    }
}
