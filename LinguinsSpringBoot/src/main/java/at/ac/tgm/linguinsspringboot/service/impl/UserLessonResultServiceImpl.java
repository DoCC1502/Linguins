package at.ac.tgm.linguinsspringboot.service.impl;

import at.ac.tgm.linguinsspringboot.converter.LinguinsMapper;
import at.ac.tgm.linguinsspringboot.dto.UserLessonResultDto;
import at.ac.tgm.linguinsspringboot.entity.UserLessonResultEntity;
import at.ac.tgm.linguinsspringboot.repository.UserLessonResultRepository;
import at.ac.tgm.linguinsspringboot.service.UserLessonResultService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLessonResultServiceImpl implements UserLessonResultService {

    @Autowired
    private UserLessonResultRepository repo;

    @Autowired
    private LinguinsMapper mapper;

    @Transactional
    @Override
    public UserLessonResultDto create(UserLessonResultDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id muss null sein");
        }

        UserLessonResultEntity entity = mapper.toEntity(dto);
        UserLessonResultEntity saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public UserLessonResultDto update(UserLessonResultDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id darf nicht null sein");
        }

        return repo.findById(dto.getId())
                .map(entity -> {
                    entity.setScore(dto.getScore());
                    entity.setPassed(dto.getPassed());
                    entity.setCompletedAt(dto.getCompletedAt());
                    // User und Lesson nur beim Erstellen gesetzt
                    return mapper.toDto(repo.save(entity));
                })
                .orElseThrow(() -> new NoSuchElementException(
                        "UserLessonResult mit id " + dto.getId() + " nicht gefunden"));
    }

    @Transactional
    @Override
    public Optional<UserLessonResultDto> getById(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    @Override
    public List<UserLessonResultDto> getByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<UserLessonResultDto> getByLessonId(Long lessonId) {
        return repo.findByLessonId(lessonId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<UserLessonResultDto> getByUserAndLesson(Long userId, Long lessonId) {
        return repo.findByUserIdAndLessonId(userId, lessonId)
                .map(mapper::toDto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("UserLessonResult mit id " + id + " nicht gefunden");
        }
        repo.deleteById(id);
    }
}
