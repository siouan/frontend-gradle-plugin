export interface Storage {
    clear: () => void,
    getItem: (key: string) => string | null,
    removeItem: (key: string) => void,
    setItem: (key: string, item: string) => void
}
