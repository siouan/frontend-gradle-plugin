(window.webpackJsonp=window.webpackJsonp||[]).push([[84,3,5,6,7,8,9,20,21,25,30],{310:function(e,t,n){"use strict";n.r(t);n(113),n(31);var o=n(0),code=n(112),r=n(140),l=n(85),c=o.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:r.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),f=n(13),component=Object(f.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.configuration,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},318:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(84),l="https://nodejs.org",c=o.a.component("fgp-nodejs-link",{components:{fgpLink:r.default},props:{path:{type:String,default:"/"},label:{type:String,default:null}},computed:{internalLabel:function(){return this.label||this.$t("navigation.nodejs.label")},nodeUrl:function(){return this.path?"".concat(l).concat(this.path):l}}}),f=n(13),component=Object(f.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:e.nodeUrl}},[e._v(e._s(e.internalLabel))])}),[],!1,null,null,null);t.default=component.exports},320:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(140),l=o.a.component("fgp-faq",{components:{fgpSiteLink:r.default}}),c=n(13),component=Object(c.a)(l,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("article",{staticClass:"mt-4 pb-2 border-bottom"},[t("header",[t("h4",[e._t("question"),e._v(" "),t("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[e._v("↑")])],2)]),e._v(" "),t("section",{staticClass:"px-3"},[e._t("answer")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},321:function(e,t,n){"use strict";n.r(t);var o=n(0).a.component("fgp-main-title",{}),r=n(13),component=Object(r.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("header",[t("h1",[e._t("default")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},323:function(e,t,n){"use strict";n(324),n(19),n(31);t.a={head:function(){var e={meta:[],link:[]};return e.title=this.htmlTitle?this.htmlTitle:"Frontend Gradle plugin",this.metaDescription&&e.meta.push({hid:"description",name:"description",content:this.metaDescription}),e.meta.push({name:"og:title",content:e.title}),e.meta.push({name:"og:description",content:e.meta.find((function(meta){return"description"===meta.name})).content}),this.linkCanonicalHref&&e.link.push({rel:"canonical",href:this.linkCanonicalHref}),e}}},324:function(e,t,n){"use strict";var o=n(2),r=n(88).find,l=n(141),c="find",f=!0;c in[]&&Array(1)[c]((function(){f=!1})),o({target:"Array",proto:!0,forced:f},{find:function(e){return r(this,e,arguments.length>1?arguments[1]:void 0)}}),l(c)},325:function(e,t,n){var content=n(331);content.__esModule&&(content=content.default),"string"==typeof content&&(content=[[e.i,content,""]]),content.locals&&(e.exports=content.locals);(0,n(41).default)("76c9b02b",content,!0,{sourceMap:!1})},330:function(e,t,n){"use strict";n(325)},331:function(e,t,n){var o=n(40)((function(i){return i[1]}));o.push([e.i,".fgp-block-quote[data-v-0888454f]{border:solid #cfcfcf;border-width:0 0 0 3px;color:#9f9f9f;font-size:1rem}",""]),o.locals={},e.exports=o},333:function(e,t,n){"use strict";n.r(t);var o=n(0).a.component("fgp-block-quote",{}),r=(n(330),n(13)),component=Object(r.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("blockquote",{staticClass:"blockquote mt-3 px-3 fgp-block-quote"},[e._t("default")],2)}),[],!1,null,"0888454f",null);t.default=component.exports},334:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(84),l=o.a.component("fgp-java-system-properties-link",{components:{fgpLink:r.default}}),c=n(13),component=Object(c.a)(l,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:"https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html"}},[e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},370:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(320),l=o.a.component("fgp-cache-directory-faq",{components:{fgpFaq:r.default}}),c=n(13),component=Object(c.a)(l,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-faq",{scopedSlots:e._u([{key:"question",fn:function(){return[e._v("\n        What's the purpose of the "),t("fgp-code",[e._v(".frontend-gradle-plugin")]),e._v(" directory generated in each project?\n    ")]},proxy:!0},{key:"answer",fn:function(){return[e._v("\n        The plugin uses this directory to store some task outputs as files, so as Gradle can perform\n        "),t("fgp-gradle-docs-link",{attrs:{path:"/current/userguide/incremental_build.html"}},[e._v("up-to-date\n        checks")]),e._v(". It allows to skip some task execution and optimize build time when inputs and\n        outputs did not change, by reusing the already computed outputs. In practice, this directory is generally\n        located in the"),t("fgp-code",[e._v("build")]),e._v(" directory of the project. However, the plugin cannot rely on this\n        directory since it is removed when Gradle "),t("fgp-code",[e._v("clean")]),e._v(" task is executed, which would remove\n        mandatory outputs for plugin task "),t("fgp-task-link",{attrs:{name:"cleanFrontend"}}),e._v(".\n    ")]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports},371:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(320),l=n(318),c=o.a.component("fgp-custom-nodejs-distribution-server-faq",{components:{fgpFaq:r.default,fgpNodejsLink:l.default}}),f=n(13),component=Object(f.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-faq",{scopedSlots:e._u([{key:"question",fn:function(){return[e._v("\n        How to configure the plugin so as it can download a "),t("fgp-nodejs-link"),e._v(" distribution from a custom server?\n    ")]},proxy:!0},{key:"answer",fn:function(){return[e._v("\n        Properties "),t("fgp-property-link",{attrs:{name:"nodeDistributionUrlRoot"}}),e._v(" and\n        "),t("fgp-property-link",{attrs:{name:"nodeDistributionUrlPathPattern"}}),e._v(" exist specially for this use case.\n    ")]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports},372:function(e,t,n){"use strict";n.r(t);var o=n(0),code=n(112),r=n(320),l=n(318),c=n(310),f=o.a.component("fgp-node-corepack-npm-pnpm-yarn-direct-use-faq",{components:{fgpCode:code.default,fgpFaq:r.default,fgpNodejsLink:l.default,fgpPropertyLink:c.default}}),d=n(13),component=Object(d.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-faq",{scopedSlots:e._u([{key:"question",fn:function(){return[e._v("\n        How to use "),t("fgp-code",[e._v("node")]),e._v("/"),t("fgp-code",[e._v("corepack")]),e._v("/"),t("fgp-code",[e._v("npm")]),e._v("/"),t("fgp-code",[e._v("pnpm")]),e._v("/"),t("fgp-code",[e._v("yarn")]),e._v(" executables apart from Gradle when the "),t("fgp-nodejs-link"),e._v("\n        distribution is downloaded by the plugin?\n    ")]},proxy:!0},{key:"answer",fn:function(){return[t("ul",[t("li",[e._v("\n                Create a "),t("fgp-code",[e._v("NODEJS_HOME")]),e._v(" environment variable (or a name of your choice) containing\n                the real path set in the "),t("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),e._v(" property.\n            ")],1),e._v(" "),t("li",[e._v("\n                Add the directory containing executables to the "),t("fgp-code",[e._v("PATH")]),e._v(" environment variable:\n                "),t("ul",[t("li",[e._v("On Unix-like O/S, add the "),t("fgp-code",[e._v("$NODEJS_HOME/bin")]),e._v(" path.")],1),e._v(" "),t("li",[e._v("On Windows O/S, add "),t("fgp-code",[e._v("%NODEJS_HOME%")]),e._v(" path.")],1)])],1)])]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports},373:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(333),code=n(112),l=n(320),c=n(334),f=n(318),d=o.a.component("fgp-unsupported-platform-exception-faq",{components:{fgpBlockQuote:r.default,fgpCode:code.default,fgpFaq:l.default,fgpNodejsLink:f.default,fgpJavaSystemPropertiesLink:c.default}}),v=n(13),component=Object(v.a)(d,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-faq",{scopedSlots:e._u([{key:"question",fn:function(){return[e._v("\n        What should I do when error\n        "),t("fgp-code",[e._v("UnsupportedPlatformException")]),e._v("\n        occurs?\n    ")]},proxy:!0},{key:"answer",fn:function(){return[e._v('\n        By default, the plugin uses an automatic resolution process to "guess" the most relevant\n        '),t("fgp-nodejs-link"),e._v("\n        distribution applicable for your O/S and CPU architecture. Technically speaking, the plugin relies on\n        "),t("fgp-java-system-properties-link",[e._v("JVM system properties")]),e._v(" named\n        "),t("fgp-code",[e._v("os.name")]),e._v(" and "),t("fgp-code",[e._v("os.arch")]),e._v(". If the plugin gets values it does not expect\n        from these system properties, the following error shall occur during the build:\n\n        "),t("fgp-block-quote",[e._v("\n            Execution failed for task ':installNode'.\n            > org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException: This platform is not\n            supported yet: Platform(jvmArch=<os.arch>, osName=<os.name>)\n        ")]),e._v("\n\n        In this case, please open an issue in the project's "),t("fgp-repo-link",[e._v("issue tracker")]),e._v(", and\n        provide the entire error message above. If possible, provide also output of command\n        "),t("fgp-code",[e._v("java -XshowSettings:properties -version")]),e._v(" and output of command\n        "),t("fgp-code",[e._v("uname")]),e._v(" for Unix-like O/S (beware of hiding sensitive data in both cases). As a\n        workaround, consider using property "),t("fgp-property-link",{attrs:{name:"nodeDistributionUrlPathPattern"}}),e._v(" and specify\n        a hard-coded path to download the relevant distribution from the "),t("fgp-nodejs-link"),e._v(" distribution server.\n    ")]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports},392:function(e,t,n){"use strict";n.r(t);var o=n(0),r=n(370),code=n(112),l=n(371),c=n(321),f=n(372),d=n(323),v=n(140),_=n(373),h=o.a.component("fgp-faqs",{components:{fgpCacheDirectoryFaq:r.default,fgpCode:code.default,fgpCustomNodejsDistributionServerFaq:l.default,fgpMainTitle:c.default,fgpNodeCorepackNpmPnpmYarnDirectUseFaq:f.default,fgpSiteLink:v.default,fgpUnsupportedPlatformExceptionFaq:_.default},mixins:[d.a],data:function(){return{htmlTitle:"Frequently asked questions",metaDescription:"Using node, npm, npx, yarn executables apart from Gradle and resolving common issues.",linkCanonicalHref:"https://siouan.github.io/frontend-gradle-plugin/faqs/"}}}),m=n(13),component=Object(m.a)(h,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("section",[t("fgp-main-title",[e._v("Frequently asked questions")]),e._v(" "),t("nav",[t("ul",[t("li",[t("fgp-site-link",{attrs:{path:"#node-corepack-npm-pnpm-yarn-direct-use"}},[e._v("\n                    How to use "),t("fgp-code",[e._v("node")]),e._v("/"),t("fgp-code",[e._v("corepack")]),e._v("/"),t("fgp-code",[e._v("npm")]),e._v("/"),t("fgp-code",[e._v("pnpm")]),e._v("/"),t("fgp-code",[e._v("yarn")]),e._v(" executables apart from Gradle\n                    when the Node.js distribution is downloaded by the plugin?\n                ")],1)],1),e._v(" "),t("li",[t("fgp-site-link",{attrs:{path:"#unsupported-platform-exception"}},[e._v("\n                    What should I do when error "),t("fgp-code",[e._v("UnsupportedPlatformException")]),e._v(" occurs?\n                ")],1)],1),e._v(" "),t("li",[t("fgp-site-link",{attrs:{path:"#custom-nodejs-distribution-server"}},[e._v("\n                    How to configure plugin so as I can download "),t("fgp-code",[e._v("Node.js")]),e._v(" distribution from a\n                    customserver?\n                ")],1)],1),e._v(" "),t("li",[t("fgp-site-link",{attrs:{path:"#cache-directory"}},[e._v("\n                    What's the purpose of the "),t("fgp-code",[e._v(".frontend-gradle-plugin")]),e._v(" directory generated in\n                    each project?\n                ")],1)],1)])]),e._v(" "),t("fgp-node-corepack-npm-pnpm-yarn-direct-use-faq",{attrs:{id:"node-corepack-npm-pnpm-yarn-direct-use"}}),e._v(" "),t("fgp-unsupported-platform-exception-faq",{attrs:{id:"unsupported-platform-exception"}}),e._v(" "),t("fgp-custom-nodejs-distribution-server-faq",{attrs:{id:"custom-nodejs-distribution-server"}}),e._v(" "),t("fgp-cache-directory-faq",{attrs:{id:"cache-directory"}})],1)}),[],!1,null,null,null);t.default=component.exports}}]);