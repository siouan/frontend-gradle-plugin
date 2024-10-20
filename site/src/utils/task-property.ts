export enum TaskPropertyBinding {
    CUSTOM = 'C',
    PROPERTY = 'P',
    USER = 'U'
}

export type TaskPropertyBindingType = `${TaskPropertyBinding}`;

export enum TaskPropertyType {
    DIRECTORY = 'D',
    EXECUTABLE_TYPE = 'ET',
    FILE = 'F',
    STRING = 'S',
    REGULAR_FILE = 'RF'
}

export type TaskPropertyTypeType = `${TaskPropertyType}`;
