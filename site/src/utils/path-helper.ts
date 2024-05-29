const RELEASE_GROUP_NAME = 'release';
const CANONICAL_PATH_GROUP_NAME = 'canonicalPath';
const RELEASE_PATH_PATTERN = `^(?:\\/(?<${RELEASE_GROUP_NAME}>\\d+))?(?<${CANONICAL_PATH_GROUP_NAME}>\\/(?:[^\\d].*)?)$`;
const RELEASE_PATH_REGEXP = new RegExp(RELEASE_PATH_PATTERN);

export const parseReleaseAndCanonicalPath = (path: string, defaultRelease: number): [number, string] => {
    const pathWithoutLeadingSlash = path.substring(1);
    const indexOfSecondSlash = pathWithoutLeadingSlash.indexOf('/');
    const matchResult = path.match(RELEASE_PATH_REGEXP);
    if (!matchResult?.groups) {
        return [defaultRelease, '/'];
    }
    let release = Number(matchResult.groups[RELEASE_GROUP_NAME]);
    const canonicalPath = matchResult.groups[CANONICAL_PATH_GROUP_NAME] as string;
    if (Number.isNaN(release)) {
        release = defaultRelease;
    }
    return [release, canonicalPath];
}
