
import homepage from '../pages/homepage.vue'
import signInPage from '../pages/signInPage.vue'
import registerPage from '../pages/registerPage.vue'
import LessonsPage from '../pages/LessonsPage.vue'
import AboutPage from '../pages/AboutPage.vue'
import ContactPage from '../pages/ContactPage.vue'

// Lessons
// @ts-ignore
import LinuxFundamentals from '../pages/lessons/LinuxFundamentals.vue'
// @ts-ignore
import EssentialCommands from '../pages/lessons/EssentialCommands.vue'
// @ts-ignore
import FilePermissions from '../pages/lessons/FilePermissions.vue'
// @ts-ignore
import ProcessManagement from '../pages/lessons/ProcessManagement.vue'
// @ts-ignore
import NetworkCommands from '../pages/lessons/NetworkCommands.vue'
// @ts-ignore
import ShellScripting from '../pages/lessons/ShellScripting.vue'

import { createRouter, createWebHistory } from 'vue-router'

import LessonDetailView from '../pages/LessonView.vue'

// Terminal and Collaboration
import TerminalPage from '../pages/TerminalPage.vue'
import CollaborationPage from '../pages/CollaborationPage.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: homepage },
        { path: '/signin', component: signInPage },
        { path: '/register', component: registerPage },
        { path: '/lessons', component: LessonsPage },
        { path: '/about', component: AboutPage },
        { path: '/contact', component: ContactPage },

        // STATISCHE ROUTEN (Deine händischen Files)
        { path: '/lessons/linux-fundamentals', component: LinuxFundamentals },
        { path: '/lessons/essential-commands', component: EssentialCommands },
        { path: '/lessons/file-permissions', component: FilePermissions },
        { path: '/lessons/process-management', component: ProcessManagement },
        { path: '/lessons/network-commands', component: NetworkCommands },
        { path: '/lessons/shell-scripting', component: ShellScripting },

        // DYNAMISCHE ROUTE (Für alles aus der Datenbank)
        // WICHTIG: Diese Route muss UNTER den statischen stehen,
        // damit 'linux-fundamentals' nicht als ID interpretiert wird.
        {
            path: '/lessons/:id',
            name: 'lesson-detail',
            component: LessonDetailView,
            props: true
        },

        { path: '/terminal', component: TerminalPage },
        { path: '/collaboration', component: CollaborationPage }
    ]
})

export default router