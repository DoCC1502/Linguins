package at.ac.tgm.linguinsspringboot.converter;

import at.ac.tgm.linguinsspringboot.dto.*;
import at.ac.tgm.linguinsspringboot.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    @Mapping(source = "lesson.id", target = "lessonId")
    LessonContentDto toDto(LessonContentEntity lessonContent);
    @Mapping(source = "lessonId", target = "lesson.id")
    LessonContentEntity toEntity(LessonContentDto lessonContentDto);

    // UserProgress Mappings
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    UserProgressDto toDto(UserProgressEntity userProgress);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    UserProgressEntity toEntity(UserProgressDto userProgressDto);

    List<UserProgressDto> toUserProgressDtoList(List<UserProgressEntity> userProgresses);

    // UserLessonResult Mappings
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    UserLessonResultDto toDto(UserLessonResultEntity userLessonResult);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    UserLessonResultEntity toEntity(UserLessonResultDto userLessonResultDto);

    List<UserLessonResultDto> toUserLessonResultDtoList(List<UserLessonResultEntity> results);
}