export enum TaskOutcome {
    SUCCESS = 'SUCCESS',
    NO_SOURCE = 'NO_SOURCE',
    SKIPPED = 'SKIPPED',
    UP_TO_DATE = 'UP_TO_DATE'
}

export type TaskOutcomeType = `${TaskOutcome}`;
