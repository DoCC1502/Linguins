import axios from 'axios';
import type { LessonContentDto } from '../types/types';

// Pfad zu den Inhalten (Tabelle lesson_content)
const API_URL = 'http://localhost:8080/api/lesson-contents';

export const lessonContentService = {
    /**
     * Holt den Text-Inhalt einer Lektion
     * Entspricht: @GetMapping("/lesson-contents/lesson/{lessonId}")
     */
    async getByLessonId(lessonId: number): Promise<LessonContentDto> {
        const response = await axios.get(`${API_URL}/lesson/${lessonId}`);
        return response.data;
    }
};