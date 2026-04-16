import type { LessonContent } from "@/types/types";

/**
 * Statische Lektionsinhalte - gemappt auf Backend LessonDto.id
 * Jede Lektion hat eine eindeutige ID die zum Backend passt
 */
export const lessonContents: Record<number, LessonContent> = {
    1: {
        id: 1,
        title: "Linux Fundamentals",
        description: "Learn core Linux concepts and directory structure.",
        difficulty: 1,
        tasks: [
            { q: "List files", c: "ls", a: "", r: null },
            { q: "Show current dir", c: "pwd", a: "", r: null },
            { q: "Change directory", c: "cd", a: "", r: null },
            { q: "Clear terminal", c: "clear", a: "", r: null },
            { q: "Root directory symbol", c: "/", a: "", r: null }
        ]
    },
    2: {
        id: 2,
        title: "Essential Commands",
        description: "Master the most commonly used Linux commands for daily operations.",
        difficulty: 1,
        tasks: [
            { q: "Copy files", c: "cp", a: "", r: null },
            { q: "Move files", c: "mv", a: "", r: null },
            { q: "Remove files", c: "rm", a: "", r: null },
            { q: "Show file content", c: "cat", a: "", r: null },
            { q: "Create file", c: "touch", a: "", r: null }
        ]
    },
    3: {
        id: 3,
        title: "File Permissions",
        description: "Understand and manage Linux file permissions and ownership.",
        difficulty: 2,
        tasks: [
            { q: "Change permissions", c: "chmod", a: "", r: null },
            { q: "Change owner", c: "chown", a: "", r: null },
            { q: "Read permission number", c: "4", a: "", r: null },
            { q: "Write permission number", c: "2", a: "", r: null },
            { q: "Execute permission number", c: "1", a: "", r: null }
        ]
    },
    4: {
        id: 4,
        title: "Process Management",
        description: "Learn to manage running processes on Linux systems.",
        difficulty: 2,
        tasks: [
            { q: "List processes", c: "ps", a: "", r: null },
            { q: "Live process view", c: "top", a: "", r: null },
            { q: "Kill process", c: "kill", a: "", r: null },
            { q: "Background process", c: "&", a: "", r: null },
            { q: "Foreground process", c: "fg", a: "", r: null }
        ]
    },
    5: {
        id: 5,
        title: "Network Commands",
        description: "Essential commands for network configuration and troubleshooting.",
        difficulty: 3,
        tasks: [
            { q: "Ping host", c: "ping", a: "", r: null },
            { q: "Download file", c: "wget", a: "", r: null },
            { q: "HTTP requests", c: "curl", a: "", r: null },
            { q: "Remote login", c: "ssh", a: "", r: null },
            { q: "Network config", c: "ifconfig", a: "", r: null }
        ]
    },
    6: {
        id: 6,
        title: "Shell Scripting",
        description: "Learn to write powerful shell scripts to automate tasks.",
        difficulty: 3,
        tasks: [
            { q: "Script shebang", c: "#!/bin/bash", a: "", r: null },
            { q: "Variable assign", c: "=", a: "", r: null },
            { q: "If statement", c: "if", a: "", r: null },
            { q: "For loop", c: "for", a: "", r: null },
            { q: "Echo output", c: "echo", a: "", r: null }
        ]
    }
};

/**
 * Hilfsfunktionen zum Zugriff auf Lektionsinhalte
 */
export const lessonProvider = {
    /**
     * Gibt den statischen Inhalt einer Lektion anhand ihrer ID zurück
     */
    getLessonContent(lessonId: number): LessonContent | undefined {
        return lessonContents[lessonId];
    },

    /**
     * Gibt alle verfügbaren Lektionsinhalte zurück
     */
    getAllLessons(): LessonContent[] {
        return Object.values(lessonContents);
    },

    /**
     * Prüft ob ein Lektionsinhalt für die gegebene ID existiert
     */
    hasLessonContent(lessonId: number): boolean {
        return lessonId in lessonContents;
    },

    /**
     * Gibt die Anzahl der Aufgaben für eine Lektion zurück
     */
    getTaskCount(lessonId: number): number {
        return lessonContents[lessonId]?.tasks.length ?? 0;
    },

    /**
     * Berechnet den Fortschritt (Prozent) basierend auf korrekt beantworteten Aufgaben
     */
    calculateProgress(lessonId: number, answeredCorrectly: number): number {
        const taskCount = this.getTaskCount(lessonId);
        if (taskCount === 0) return 0;
        return Math.round((answeredCorrectly / taskCount) * 100);
    }
};
