package at.ac.tgm.linguinsspringboot;

import at.ac.tgm.linguinsspringboot.controller.LinguinsController;
import at.ac.tgm.linguinsspringboot.dto.*;
import at.ac.tgm.linguinsspringboot.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@WebMvcTest(LinguinsController.class)
class LinguinsSpringBootApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private LessonContentService lessonContentService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserProgressService userProgressService;

    @MockBean
    private UserLessonResultService userLessonResultService;

    private LessonDto testLesson;
    private UserDto testUser;
    private LessonContentDto testLessonContent;

    @BeforeEach
    void setUp() {
        // Test Lesson
        testLesson = new LessonDto();
        testLesson.setId(1L);
        testLesson.setTitle("German Basics");
        testLesson.setDescription("Learn basic German");
        testLesson.setDifficulty("beginner");

        // Test User
        testUser = new UserDto();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("USER");

        // Test LessonContent
        testLessonContent = new LessonContentDto();
        testLessonContent.setId(1L);
        testLessonContent.setLessonId(1L);
        testLessonContent.setContent("This is the lesson content");
    }

    /*
     * LESSON ENDPOINT TESTS
     */

    @Test
    void createLesson_shouldReturnCreated() throws Exception {
        LessonDto newLesson = new LessonDto();
        newLesson.setTitle("French Basics");
        newLesson.setDescription("Learn basic French");
        newLesson.setDifficulty("beginner");

        LessonDto savedLesson = new LessonDto();
        savedLesson.setId(2L);
        savedLesson.setTitle("French Basics");
        savedLesson.setDescription("Learn basic French");
        savedLesson.setDifficulty("beginner");

        when(lessonService.create(any(LessonDto.class))).thenReturn(savedLesson);

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newLesson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("French Basics"))
                .andExpect(jsonPath("$.difficulty").value("beginner"));

        verify(lessonService, times(1)).create(any(LessonDto.class));
    }

    @Test
    void getLessonById_shouldReturnLesson() throws Exception {
        when(lessonService.getById(1L)).thenReturn(Optional.of(testLesson));

        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("German Basics"))
                .andExpect(jsonPath("$.difficulty").value("beginner"));

        verify(lessonService, times(1)).getById(1L);
    }

    @Test
    void getLessonById_notFound_shouldThrowException() throws Exception {
        when(lessonService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/lessons/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bad request"))
                .andExpect(jsonPath("$.details[0]").value("Lesson mit id 999 nicht gefunden"));

        verify(lessonService, times(1)).getById(999L);
    }

    @Test
    void getAllLessons_shouldReturnList() throws Exception {
        LessonDto lesson2 = new LessonDto();
        lesson2.setId(2L);
        lesson2.setTitle("Spanish Basics");
        lesson2.setDescription("Learn basic Spanish");
        lesson2.setDifficulty("intermediate");

        List<LessonDto> lessons = Arrays.asList(testLesson, lesson2);
        when(lessonService.getAll()).thenReturn(lessons);

        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("German Basics"))
                .andExpect(jsonPath("$[1].title").value("Spanish Basics"));

        verify(lessonService, times(1)).getAll();
    }

    @Test
    void getLessonsByDifficulty_shouldReturnFilteredList() throws Exception {
        List<LessonDto> beginnerLessons = Arrays.asList(testLesson);
        when(lessonService.getByDifficulty("beginner")).thenReturn(beginnerLessons);

        mockMvc.perform(get("/api/lessons/difficulty/beginner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].difficulty").value("beginner"));

        verify(lessonService, times(1)).getByDifficulty("beginner");
    }

    @Test
    void updateLesson_shouldReturnUpdatedLesson() throws Exception {
        LessonDto updatedLesson = new LessonDto();
        updatedLesson.setId(1L);
        updatedLesson.setTitle("German Advanced");
        updatedLesson.setDescription("Advanced German");
        updatedLesson.setDifficulty("advanced");

        when(lessonService.update(any(LessonDto.class))).thenReturn(updatedLesson);

        mockMvc.perform(put("/api/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLesson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("German Advanced"))
                .andExpect(jsonPath("$.difficulty").value("advanced"));

        verify(lessonService, times(1)).update(any(LessonDto.class));
    }

    @Test
    void deleteLesson_shouldReturnNoContent() throws Exception {
        doNothing().when(lessonService).delete(1L);

        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).delete(1L);
    }

    @Test
    void deleteLesson_notFound_shouldThrowException() throws Exception {
        doThrow(new NoSuchElementException("Lesson mit id 999 nicht gefunden"))
                .when(lessonService).delete(999L);

        mockMvc.perform(delete("/api/lessons/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]").value("Lesson mit id 999 nicht gefunden"));

        verify(lessonService, times(1)).delete(999L);
    }

    /*
     * USER ENDPOINT TESTS
     */

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        UserDto newUser = new UserDto();
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("pass123");

        UserDto savedUser = new UserDto();
        savedUser.setId(2L);
        savedUser.setUsername("newuser");
        savedUser.setEmail("new@example.com");
        savedUser.setRole("USER");

        when(userService.create(any(UserDto.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("new@example.com"));

        verify(userService, times(1)).create(any(UserDto.class));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        when(userService.getById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).getById(1L);
    }

    @Test
    void getUserByEmail_shouldReturnUser() throws Exception {
        when(userService.getByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).getByEmail("test@example.com");
    }

    @Test
    void getUserByUsername_shouldReturnUser() throws Exception {
        when(userService.getByUsername("testuser")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).getByUsername("testuser");
    }

    /*
     * LESSON CONTENT ENDPOINT TESTS
     */

    @Test
    void createLessonContent_shouldReturnCreated() throws Exception {
        LessonContentDto newContent = new LessonContentDto();
        newContent.setLessonId(1L);
        newContent.setContent("New content");

        LessonContentDto savedContent = new LessonContentDto();
        savedContent.setId(2L);
        savedContent.setLessonId(1L);
        savedContent.setContent("New content");

        when(lessonContentService.create(any(LessonContentDto.class))).thenReturn(savedContent);

        mockMvc.perform(post("/api/lesson-contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.content").value("New content"));

        verify(lessonContentService, times(1)).create(any(LessonContentDto.class));
    }

    @Test
    void getLessonContentById_shouldReturnContent() throws Exception {
        when(lessonContentService.getById(1L)).thenReturn(Optional.of(testLessonContent));

        mockMvc.perform(get("/api/lesson-contents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("This is the lesson content"));

        verify(lessonContentService, times(1)).getById(1L);
    }

    @Test
    void getLessonContentByLessonId_shouldReturnContent() throws Exception {
        when(lessonContentService.getByLessonId(1L)).thenReturn(Optional.of(testLessonContent));

        mockMvc.perform(get("/api/lesson-contents/lesson/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lessonId").value(1))
                .andExpect(jsonPath("$.content").value("This is the lesson content"));

        verify(lessonContentService, times(1)).getByLessonId(1L);
    }

    /*
     * ERROR HANDLING TESTS
     */

    @Test
    void invalidJson_shouldReturnBadRequest() throws Exception {
        String invalidJson = "{invalid json}";

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request"));
    }

    @Test
    void invalidPathVariable_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/lessons/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Type mismatch"));
    }
}