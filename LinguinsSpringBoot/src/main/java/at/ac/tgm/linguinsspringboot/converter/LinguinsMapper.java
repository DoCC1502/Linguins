package at.ac.tgm.linguinsspringboot.converter;

import at.ac.tgm.linguinsspringboot.dto.*;
import at.ac.tgm.linguinsspringboot.entity.*;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper für die Umwandlung zwischen DTOs und Entities
 *
 * @author LinguinsTeam
 * @version 18-12-2025
 */
@Mapper(componentModel = "spring")
public interface LinguinsMapper {
    // User Mappings
    UserDto toDto(UserEntity user);
    UserEntity toEntity(UserDto userDto);
    List<UserDto> toUserDtoList(List<UserEntity> users);

    // Lesson Mappings
    LessonDto toDto(LessonEntity lesson);
    LessonEntity toEntity(LessonDto lessonDto);
    List<LessonDto> toLessonDtoList(List<LessonEntity> lessons);

    // LessonContent Mappings
    LessonContentDto toDto(LessonContentEntity lessonContent);
    LessonContentEntity toEntity(LessonContentDto lessonContentDto);

    // UserProgress Mappings
    UserProgressDto toDto(UserProgressEntity userProgress);
    UserProgressEntity toEntity(UserProgressDto userProgressDto);
    List<UserProgressDto> toUserProgressDtoList(List<UserProgressEntity> userProgresses);

    // UserLessonResult Mappings
    UserLessonResultDto toDto(UserLessonResultEntity userLessonResult);
    UserLessonResultEntity toEntity(UserLessonResultDto userLessonResultDto);
    List<UserLessonResultDto> toUserLessonResultDtoList(List<UserLessonResultEntity> results);
}