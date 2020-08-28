export default {
    head() {
        const headTags = {
            meta: [],
            link: []
        };
        headTags.title = this.htmlTitle ? this.htmlTitle : process.env.FGP_WEBSITE_TITLE;
        headTags.meta.push({
            hid: "description",
            name: "description",
            content: this.metaDescription ? this.metaDescription : process.env.FGP_WEBSITE_DESCRIPTION
        });
        headTags.meta.push({ name: "og:title", content: headTags.title });
        headTags.meta.push({ name: "og:description", content: headTags.meta.find(meta => meta.name === "description").content });
        if (this.linkCanonicalHref) {
            headTags.link.push({ rel: "canonical", href: this.linkCanonicalHref });
        }
        return headTags;
    }
};
