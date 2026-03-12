import type {
    UserDto,
    UserProgressDto,
    UserLessonResultDto
    } from "@/types/types.ts";

const API_BASE_URL = 'http://localhost:8080/api';


export const userService = {
    async register(userData: UserDto): Promise<UserDto> {
        const response = await fetch('http://localhost:8080/api/users', { // Pfad prüfen!
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            throw new Error('Fehler beim Erstellen des Users');
        }

        return await response.json(); // Gibt das vom Server erstellte UserDto zurück
    },

    /**
     * Beispiel: Lädt den Fortschritt eines bestimmten Users
     */
    async getUserProgress(userId: number): Promise<UserProgressDto[]> {
        const response = await fetch(`${API_BASE_URL}/users/${userId}/progress`);
        if (!response.ok) throw new Error('Fehler beim Laden des Fortschritts');
        return await response.json();
    },

    /**
     * Beispiel: Speichert ein Lektionsergebnis
     */
    async saveLessonResult(result: UserLessonResultDto): Promise<UserLessonResultDto> {
        const response = await fetch(`${API_BASE_URL}/results`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(result)
        });
        if (!response.ok) throw new Error('Fehler beim Speichern des Ergebnisses');
        return await response.json();
    }
};