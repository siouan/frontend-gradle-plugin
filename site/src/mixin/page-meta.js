export default {
    head() {
        const headTags = {
            meta: [],
            link: []
        };
        headTags.title = this.htmlTitle ? this.htmlTitle : 'Frontend Gradle plugin';
        if (this.metaDescription) {
            headTags.meta.push({
                hid: 'description',
                name: 'description',
                content: this.metaDescription
            });
        }
        headTags.meta.push({ name: 'og:title', content: headTags.title });
        headTags.meta.push({ name: 'og:description', content: headTags.meta.find(meta => meta.name === 'description').content });
        if (this.linkCanonicalHref) {
            headTags.link.push({ rel: 'canonical', href: this.linkCanonicalHref });
        }
        return headTags;
    }
};
