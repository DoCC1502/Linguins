import type {
    LessonDto,
    LessonContentDto,
    LessonContent
} from "@/types/types";
import { lessonProvider } from "@/data/lessonProvider";

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
     * Lädt den spezifischen Inhalt einer Lektion anhand ihrer ID
     * (Legacy - verwendet Backend API)
     * @deprecated Verwende stattdessen getStaticContent()
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
     * NEU: Lädt statische Lektionsinhalte vom lokalen Frontend-Provider
     * Statt /content API zu verwenden
     */
    getStaticContent(lessonId: number): LessonContent | null {
        const content = lessonProvider.getLessonContent(lessonId);
        if (!content) {
            console.warn(`Kein statischer Inhalt für Lektion ${lessonId} gefunden`);
            return null;
        }

        // Deep clone damit jede Lektion ihren eigenen State hat
        return {
            ...content,
            tasks: content.tasks.map(task => ({ ...task }))
        };
    },

    /**
     * Beispiel: Lädt eine einzelne Lektion (Metadaten) anhand der ID
     * Lädt alle Lektionen für das Dashboard
     */
    async getAllLessons(): Promise<LessonDto[]> {
        const response = await fetch(`${API_BASE_URL}/lessons`);

        if (!response.ok) {
            throw new Error('Fehler beim Laden aller Lektionen');
        }

        return await response.json();
    },

    /**
     * SYNC: Aktualisiert den User-Fortschritt für eine Lektion
     * PUT /api/user-progress
     */
    async updateUserProgress(
        userId: number,
        lessonId: number,
        percentage: number,
        status: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED'
    ): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/user-progress`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userId,
                lessonId,
                percentage,
                status
            })
        });

        if (!response.ok) {
            throw new Error('Fehler beim Aktualisieren des Fortschritts');
        }
    },

    /**
     * SYNC: Speichert das Ergebnis einer abgeschlossenen Lektion
     * POST /api/user-lesson-result
     */
    async saveLessonResult(
        userId: number,
        lessonId: number,
        score: number,
        passed: boolean
    ): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/user-lesson-result`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userId,
                lessonId,
                score,
                passed,
                completedAt: passed ? new Date().toISOString() : null
            })
        });

        if (!response.ok) {
            throw new Error('Fehler beim Speichern des Lektionsergebnisses');
        }
    },

    /**
     * Hilfsmethode: Berechnet Score und speichert Ergebnis falls bestanden
     * Score = (korrekte Antworten / Gesamtaufgaben) * 100
     */
    async completeLesson(
        userId: number,
        lessonId: number,
        correctAnswers: number,
        totalTasks: number
    ): Promise<{ score: number; passed: boolean }> {
        const score = totalTasks > 0 ? Math.round((correctAnswers / totalTasks) * 100) : 0;
        const passed = score >= 60; // Mindestens 60% zum Bestehen

        if (passed) {
            await this.saveLessonResult(userId, lessonId, score, true);
            await this.updateUserProgress(userId, lessonId, 100, 'COMPLETED');
        } else {
            const percentage = Math.round((correctAnswers / totalTasks) * 100);
            await this.updateUserProgress(userId, lessonId, percentage, 'IN_PROGRESS');
        }

        return { score, passed };
    }
};