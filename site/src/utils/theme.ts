export enum ThemeId {
    DARK = 'dark',
    LIGHT = 'light'
}

export type ThemeIdType = `${ThemeId}`;

export enum PreferedThemeId {
    SYSTEM = 'system',
    DARK = ThemeId.DARK,
    LIGHT = ThemeId.LIGHT
}

export type PreferedThemeIdType = `${PreferedThemeId}`;
