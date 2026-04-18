package at.ac.tgm.linguinsspringboot.service.impl;

import at.ac.tgm.linguinsspringboot.converter.LinguinsMapper;
import at.ac.tgm.linguinsspringboot.dto.LessonContentDto;
import at.ac.tgm.linguinsspringboot.entity.LessonEntity;
import at.ac.tgm.linguinsspringboot.exception.ResourceNotFoundException;
import at.ac.tgm.linguinsspringboot.repository.LessonContentRepository;
import at.ac.tgm.linguinsspringboot.repository.LessonRepository;
import at.ac.tgm.linguinsspringboot.service.LessonContentService;
import at.ac.tgm.linguinsspringboot.entity.LessonContentEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class LessonContentServiceImpl implements LessonContentService {

    private final LessonRepository lessonRepo;
    private final LessonContentRepository repo;
    private final LinguinsMapper mapper;

    public LessonContentServiceImpl(LessonContentRepository repo, LinguinsMapper mapper, LessonRepository lessonRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.lessonRepo = lessonRepo;
    }


    // LessonContentServiceImpl.java
    @Override
    public LessonContentDto create(LessonContentDto dto) {
        // 1. Die dazugehörige Lektion finden
        LessonEntity lesson = lessonRepo.findById(dto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lektion mit ID " + dto.getLessonId() + " nicht gefunden"));

        // 2. Entity erstellen und JSON-String setzen
        LessonContentEntity entity = new LessonContentEntity();
        entity.setLesson(lesson);
        entity.setContent(dto.getContent()); // Das ist dein JSON.stringify(tasks)

        // 3. Speichern und zurückgeben
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public LessonContentDto update(LessonContentDto dto) {
        return repo.findById(dto.getId())
                .map(entity -> {
                    entity.setContent(dto.getContent());

                    if (dto.getLessonId() != null) {
                        LessonEntity lessonEntity = lessonRepo.findById(dto.getLessonId())
                                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + dto.getLessonId()));
                        entity.setLesson(lessonEntity);
                    }

                    LessonContentEntity saved = repo.save(entity);

                    LessonContentDto result = new LessonContentDto();
                    result.setLessonId(dto.getLessonId());
                    result.setContent(dto.getContent());

                    return result;

                })
                .orElseThrow(() -> new RuntimeException("LessonContent not found with id: " + dto.getId()));
    }

    @Override
    public Optional<LessonContentDto> getById(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Override
    public Optional<LessonContentDto> getByLessonId(Long lessonId) {
        return repo.findByLessonId(lessonId).map(mapper::toDto);
    }

    public LessonContentDto getContentByLessonId(Long lessonId) {
        LessonContentEntity entity = repo.findByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Inhalt für Lektion " + lessonId + " nicht gefunden"));
        return mapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
