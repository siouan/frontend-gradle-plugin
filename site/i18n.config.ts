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
                npx: {
                    label: "npx"
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
                    baseline: 'Berry',
                    label: 'Yarn',
                    originalName: 'Yarn Berry',
                    title: 'Yarn Berry',
                },
                yarnClassic: {
                    baseline: 'Classic',
                    label: 'Yarn',
                    originalName: 'Classic Yarn',
                    title: 'Classic Yarn',
                }
            },
            siouan: {
                organizationTooltip: 'Siouan at GitHub.com',
                projectTooltip: 'Frontend Gradle plugin at GitHub.com',
            },
        },
    },
}));
