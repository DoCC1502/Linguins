import type {UserDto} from "@/types/types.ts";

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
    }
};