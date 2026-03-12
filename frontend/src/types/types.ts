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

