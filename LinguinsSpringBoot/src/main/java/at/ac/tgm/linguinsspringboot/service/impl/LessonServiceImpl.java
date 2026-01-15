package at.ac.tgm.linguinsspringboot.service.impl;

import at.ac.tgm.linguinsspringboot.converter.LinguinsMapper;
import at.ac.tgm.linguinsspringboot.dto.LessonDto;
import at.ac.tgm.linguinsspringboot.entity.LessonEntity;
import at.ac.tgm.linguinsspringboot.repository.LessonRepository;
import at.ac.tgm.linguinsspringboot.service.LessonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository repo;

    @Autowired
    private LinguinsMapper mapper;

    @Transactional
    @Override
    public LessonDto create(LessonDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id muss null sein");
        }

        LessonEntity entity = mapper.toEntity(dto);
        LessonEntity saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public LessonDto update(LessonDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id darf nicht null sein");
        }

        return repo.findById(dto.getId())
                .map(entity -> {
                    entity.setTitle(dto.getTitle());
                    entity.setDescription(dto.getDescription());
                    entity.setDifficulty(dto.getDifficulty());
                    return mapper.toDto(repo.save(entity));
                })
                .orElseThrow(() -> new NoSuchElementException(
                        "Lesson mit id " + dto.getId() + " nicht gefunden"));
    }

    @Transactional
    @Override
    public Optional<LessonDto> getById(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    @Override
    public List<LessonDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<LessonDto> getByDifficulty(String difficulty) {
        return repo.findByDifficulty(difficulty).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Lesson mit id " + id + " nicht gefunden");
        }
        repo.deleteById(id);
    }
}
