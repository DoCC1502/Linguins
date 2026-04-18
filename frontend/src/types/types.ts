export interface UserDto {
    id?: number;
    username: string;
    email: string;
    password?: string;
    createdAt?: string;
    lastLogin?: string;
    role?: string;
}

export interface LessonDto {
    id?: number;
    title: string;
    description: string;
    difficulty: number;
}

export interface LessonContentDto {
    id?: number;
    lessonId: number;
    content: string;
}

export interface UserLessonResultDto {
    id?: number;
    userId: number;
    lessonId: number;
    score?: number;
    passed?: boolean;
    completedAt?: string;
}

export interface UserProgressDto {
    id?: number;
    userId: number;
    lessonId: number;
    status: string;
    percentage: number;
}

/**
 * Eine einzelne Aufgabe, wie sie im JSON-String der DB gespeichert wird.
 * Nur die reinen Daten: Frage (q) und korrekte Antwort (c).
 */
export interface QuizTask {
    q: string; // Question
    c: string; // Correct Answer
}

/**
 * Der Zustand einer Aufgabe während der User sie bearbeitet.
 * Erweitert QuizTask um flüchtige UI-Felder.
 */
export interface ActiveTask extends QuizTask {
    a: string;          // Aktuelle Eingabe des Users
    r: boolean | null;  // Ergebnis (null = ungeprüft)
}

/**
 * Das komplette Objekt, das dein LessonView.vue konsumiert.
 */
export interface ActiveLesson {
    id: number;
    title: string;
    description: string;
    tasks: ActiveTask[];
}
