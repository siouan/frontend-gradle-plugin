module.exports = {
    devServer: {
        clientLogLevel: "info",
        https: false,
        index: "index.html",
        port: 10000,
        progress: true
    },
    outputDir: "dist",
    pages: {
        index: {
            // entry for the page
            entry: "src/webapp/main.js",
            // the source template
            template: "public/index.html",
            // output as dist/index.html
            filename: "index.html",
            // chunks to include on this page, by default includes
            // extracted common chunks and vendor chunks.
            chunks: ["chunk-vendors", "chunk-common", "index"]
        }
    },
    productionSourceMap: false,
    publicPath: "/frontend-gradle-plugin/"
};
