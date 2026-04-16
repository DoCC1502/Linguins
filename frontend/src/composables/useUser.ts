import { ref, computed } from 'vue';

// Außerhalb der Funktion → global reaktiver State (geteilt zwischen allen Komponenten)
const _user = ref<{ username: string; email: string; role: string } | null>(null);

// Beim Laden direkt aus localStorage befüllen (z.B. nach Seiten-Refresh)
const stored = localStorage.getItem('user');
if (stored) {
    try {
        _user.value = JSON.parse(stored);
    } catch {
        localStorage.removeItem('user');
    }
}

export function useUser() {
    const user = computed(() => _user.value);
    const username = computed(() => _user.value?.username ?? null);
    const isLoggedIn = computed(() => _user.value !== null);

    function setUser(userData: { username: string; email: string; role: string }) {
        _user.value = userData;
        localStorage.setItem('user', JSON.stringify(userData));
    }

    function clearUser() {
        _user.value = null;
        localStorage.removeItem('user');
    }

    return { user, username, isLoggedIn, setUser, clearUser };
}