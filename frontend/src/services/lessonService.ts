import type {
    LessonDto,
    LessonContentDto
} from "@/types/types";

const API_BASE_URL = 'http://localhost:8080/api';

export const lessonService = {

    /**
     * Lädt den Text-Inhalt (aus der Tabelle lesson_content)
     * Pfad angepasst an deinen Java-Controller: /api/lesson-contents/lesson/{id}
     */
    async getLessonContent(lessonId: number): Promise<LessonContentDto> {
        // Pfad korrigiert!
        const response = await fetch(`${API_BASE_URL}/lesson-contents/lesson/${lessonId}`);

        if (!response.ok) {
            throw new Error(`Fehler beim Laden des Inhalts für Lektion ${lessonId}`);
        }

        return await response.json();
    },


    /**
     * Lädt die Basis-Infos der Lektion (Titel, Schwierigkeit)
     */
    async getLessonById(id: number): Promise<LessonDto> {
        // Variable 'id' korrigiert und .json() statt .data verwendet
        const response = await fetch(`${API_BASE_URL}/lessons/${id}`);

        if (!response.ok) {
            throw new Error('Lektion nicht gefunden');
        }

        return await response.json();
    },

    /**
     * Lädt alle Lektionen für das Dashboard
     */
    async getAllLessons(): Promise<LessonDto[]> {
        const response = await fetch(`${API_BASE_URL}/lessons`);

        if (!response.ok) {
            throw new Error('Fehler beim Laden aller Lektionen');
        }

        return await response.json();
    }
};