import type {
    LessonDto,
    LessonContentDto
} from "@/types/types";

const API_BASE_URL = 'http://localhost:8080/api';

export const lessonService = {
    /**
     * Lädt alle verfügbaren Lektionen vom Server
     */
    async getAllLessons(): Promise<LessonDto[]> {
        const response = await fetch(`${API_BASE_URL}/lessons`);

        if (!response.ok) {
            throw new Error('Fehler beim Laden der Lektionen');
        }

        return await response.json();
    },

    /**
     * Lädt den spezifischen Inhalt einer Lektion anhand ihrer ID
     */
    async getLessonContent(lessonId: number): Promise<LessonContentDto> {
        const response = await fetch(`${API_BASE_URL}/lessons/${lessonId}/content`);

        if (!response.ok) {
            throw new Error(`Fehler beim Laden des Inhalts für Lektion ${lessonId}`);
        }

        return await response.json();
    },

    /**
     * Beispiel: Lädt eine einzelne Lektion (Metadaten) anhand der ID
     */
    async getLessonById(lessonId: number): Promise<LessonDto> {
        const response = await fetch(`${API_BASE_URL}/lessons/${lessonId}`);

        if (!response.ok) {
            throw new Error('Lektion wurde nicht gefunden');
        }

        return await response.json();
    }
};