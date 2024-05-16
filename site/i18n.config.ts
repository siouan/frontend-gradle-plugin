export default defineI18nConfig(() => ({
    legacy: false,
    locale: 'en',
    messages: {
        en: {
            lang: {
                english: 'English',
                french: 'Français',
            },
            maintenance: {
                inProgress: 'Coming soon, thanks for your patience!',
            },
            menu: {
                configuration: 'Configuration',
                examples: 'Examples',
                faq: 'FAQ',
                overview: 'Overview',
                installation: 'Get started',
                tasks: 'Tasks',
            },
            navigation: {
                corepack: {
                    label: 'Corepack',
                },
                gradle: {
                    label: 'Gradle',
                },
                nodejs: {
                    label: 'Node.js',
                },
                notFound: 'The page you requested was not found.',
                npm: {
                    label: 'npm',
                },
                pnpm: {
                    label: 'pnpm',
                },
                repository: {
                    releases: {
                        label: 'Release notes',
                    },
                },
                yarnBerry: {
                    label: 'Yarn',
                    originalName: 'Berry',
                    title: 'Yarn Berry',
                },
                yarn1: {
                    label: 'Yarn 1',
                    title: 'Classic Yarn',
                },
                yarn2: {
                    label: 'Yarn 2+',
                    title: 'Yarn',
                },
            },
            siouan: {
                organizationTooltip: 'Siouan at GitHub.com',
                projectTooltip: 'Frontend Gradle plugin at GitHub.com',
            },
        },
    },
}));
