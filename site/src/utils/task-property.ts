export enum TaskPropertyBinding {
    CUSTOM = 'C',
    PROPERTY = 'P',
}

export type TaskPropertyBindingType = `${TaskPropertyBinding}`;

export enum TaskPropertyType {
    EXECUTABLE_TYPE = 'ET',
    FILE = 'F',
    STRING = 'S',
    REGULAR_FILE = 'RF',
}

export type TaskPropertyTypeType = `${TaskPropertyType}`;
