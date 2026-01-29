-- ===========================
-- USER
-- ===========================
CREATE TABLE User
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    username   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    role       VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE "user"  -- user ist reserviertes Wort, daher Anführungszeichen
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    role       VARCHAR(50)
);


-- ===========================
-- LESSON
-- (m:n zu User; 1:1 zu LessonContent)
-- ===========================
CREATE TABLE Lesson
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    difficulty  INT,
    PRIMARY KEY (id)
);


-- ===========================
-- LESSON CONTENT
-- ===========================
CREATE TABLE lesson_content
(
    id        BIGSERIAL PRIMARY KEY,
    lesson_id BIGINT NOT NULL,
    content   TEXT   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_lc_lesson
        FOREIGN KEY (lesson_id) REFERENCES Lesson (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


-- ===========================
-- USER <-> LESSON  (m:n: „bearbeitet“)
-- Junction Table
-- ===========================
CREATE TABLE UserLesson
(
    user_id   BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, lesson_id),
    CONSTRAINT fk_ul_user
        FOREIGN KEY (user_id) REFERENCES User (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_ul_lesson
        FOREIGN KEY (lesson_id) REFERENCES Lesson (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


-- ===========================
-- USER PROGRESS
-- (User 1:n UserProgress, Lesson 1:n UserProgress)
-- ===========================
CREATE TABLE UserProgress
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    user_id    BIGINT      NOT NULL,
    lesson_id  BIGINT      NOT NULL,
    percentage INT         NOT NULL,
    status     VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_up_user
        FOREIGN KEY (user_id) REFERENCES User (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_up_lesson
        FOREIGN KEY (lesson_id) REFERENCES Lesson (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


-- ===========================
-- USER LESSON RESULT
-- (n:1 zu User, n:1 zu Lesson)
-- ===========================
CREATE TABLE UserLessonResult
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    user_id      BIGINT NOT NULL,
    lesson_id    BIGINT NOT NULL,
    score        INT,
    passed       BOOLEAN,
    completed_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_ulr_user
        FOREIGN KEY (user_id) REFERENCES User (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_ulr_lesson
        FOREIGN KEY (lesson_id) REFERENCES Lesson (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
