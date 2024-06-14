(window.webpackJsonp=window.webpackJsonp||[]).push([[75,16,20,28,30,31,74,81],{308:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),c=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.configuration,"#").concat(e.name)}},[e.$slots.default?[e._t("default")]:t("fgp-code",[e._v(e._s(e.name))])],2)}),[],!1,null,null,null);t.default=component.exports},310:function(e,t,n){"use strict";n.r(t);var r=n(0),l=n(84),o=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:l.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=n(13),component=Object(c.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(e.path),"hover-style-disabled":e.hoverStyleDisabled}},[e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},311:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),c=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.tasks,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},312:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0),l=n(310),o=n(308),c=n(140),d=n(311),f=n(313),_=n(314),k=r.a.component("fgp-task",{components:{fgpGradleDocsLink:l.default,fgpPropertyLink:o.default,fgpSiteLink:c.default,fgpTaskLink:d.default,fgpTaskLinkAnchor:f.default,fgpTaskPropertyType:_.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},dependingTasks:{type:Array,default:function(){return[]}},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),v=n(13),component=Object(v.a)(k,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("article",{staticClass:"mb-3 border-bottom"},[t("header",[t("h4",[t("fgp-task-link-anchor",{attrs:{name:e.name}}),e._v(" "),e.type?[e._v("Type")]:[e._v("Task")],e._v(" "),t("fgp-code",[e._v("\n                "+e._s(e.name)+"\n            ")]),e._v("\n            -\n            "),e._t("title"),e._v(" "),e.cacheable?t("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[e._v("C")]):e._e(),e._v(" "),t("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[e._v("↑")])],2),e._v(" "),t("ul",[e.dependingTasks.length>0?t("li",[e._v("\n                Depends on:\n                "),e._l(e.dependingTasks,(function(n,r){return t("span",{key:n},[t("fgp-task-link",{attrs:{name:n}}),r<e.dependingTasks.length-1?[e._v(", ")]:e._e()],2)}))],2):e._e(),e._v(" "),e.inputs.length>0?t("li",[e._v("\n                Inputs:\n                "),t("ul",e._l(e.inputs,(function(input,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:input.type}}),t("fgp-code",[e._v(e._s(input.name))]),e._v(":\n                        "),"P"===input.binding?[t("fgp-property-link",{attrs:{name:input.property}}),e._v(" property\n                        ")]:e._e(),e._v(" "),"C"===input.binding?e._t(input.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.outputs.length>0?t("li",[e._v("\n                Outputs:\n                "),t("ul",e._l(e.outputs,(function(output,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:output.type}}),t("fgp-code",[e._v(e._s(output.name))]),e._v(":\n                        "),"C"===output.binding?e._t(output.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.skippable?t("li",[e._v("\n                Skipped when "),e._t("skipConditions")],2):e._e()])]),e._v(" "),t("section",{staticClass:"px-3"},[e._t("description")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},313:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("span",{staticClass:"text-info",attrs:{id:e.name}},[e._v("#")])}),[],!1,null,null,null);t.default=component.exports},314:function(e,t,n){"use strict";n.r(t);n(46);var r=n(0),l=n(310),o="ET",c="F",d="S",f="RF",_=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:l.default},props:{type:{type:String,required:!0,validator:function(e){return[o,c,f,d].includes(e)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case f:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case o:case c:case d:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case o:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case f:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case d:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case o:return"badge-dark";case c:case d:return"badge-primary";case f:return"badge-danger";default:return null}}}}),k=n(13),component=Object(k.a)(_,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:e.chipClass,attrs:{path:e.gradleDocsLinkPath,title:e.gradleDocsLinkTitle,"hover-style-disabled":""}},[e._v(e._s(e.type))])}),[],!1,null,null,null);t.default=component.exports},326:function(e,t,n){"use strict";n.r(t);var r=n(0),l=n(310),o=r.a.component("fgp-gradle-task-outcome-link",{components:{fgpGradleDocsLink:l.default},props:{outcome:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-gradle-docs-link",{attrs:{path:"/current/userguide/more_about_tasks.html#sec:task_outcomes"}},[e._v(e._s(e.outcome))])}),[],!1,null,null,null);t.default=component.exports},384:function(e,t,n){"use strict";n.r(t);var r=n(0),code=n(112),l=n(326),o=n(308),c=n(312),d=n(311),f=r.a.component("fgp-assemble-frontend-task",{components:{fgpCode:code.default,fgpGradleTaskOutcomeLink:l.default,fgpPropertyLink:o.default,fgpTask:c.default,fgpTaskLink:d.default},data:function(){return{inputs:[{name:"packageJsonDirectory",type:"F",binding:"P",property:"packageJsonDirectory"},{name:"nodeInstallDirectory",type:"F",binding:"P",property:"nodeInstallDirectory"},{name:"script",type:"S",binding:"P",property:"assembleScript"}]}}}),_=n(13),component=Object(_.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-task",{attrs:{name:"assembleFrontend","depending-tasks":["installFrontend"],inputs:e.inputs},scopedSlots:e._u([{key:"title",fn:function(){return[e._v("Assemble frontend artifacts")]},proxy:!0},{key:"skipConditions",fn:function(){return[e._v("\n        task "),t("fgp-task-link",{attrs:{name:"installFrontend"}}),e._v(" was skipped or property\n        "),t("fgp-property-link",{attrs:{name:"assembleScript"}}),e._v(" is "),t("fgp-code",[e._v("null")]),e._v(".\n    ")]},proxy:!0},{key:"description",fn:function(){return[e._v("\n        This task allows to execute a build script as part of a Gradle build. The build script shall be defined in\n        the "),t("fgp-code",[e._v("package.json")]),e._v(" file, and the "),t("fgp-property-link",{attrs:{name:"assembleScript"}}),e._v(" property\n        shall be set with the corresponding\n        "),t("fgp-code",[e._v("npm")]),e._v("/"),t("fgp-code",[e._v("pnpm")]),e._v("/"),t("fgp-code",[e._v("yarn")]),e._v(" command. This task depends on\n        the "),t("fgp-task-link",{attrs:{name:"installFrontend"}}),e._v(" task, and is skipped if the\n        "),t("fgp-property-link",{attrs:{name:"assembleScript"}}),e._v(" property is "),t("fgp-code",[e._v("null")]),e._v(". Apart from direct\n        execution, the task is also executed when the Gradle lifecycle\n        "),t("fgp-gradle-docs-link",{attrs:{path:"/current/userguide/base_plugin.html#sec:base_tasks"}},[e._v("assemble")]),e._v(" task is executed.\n\n        "),t("div",{staticClass:"card my-3"},[t("div",{staticClass:"card-body"},[t("h5",{staticClass:"card-title"},[e._v("About\n                    "),t("fgp-gradle-docs-link",{attrs:{path:"/current/userguide/incremental_build.html"}},[e._v("incremental build")]),e._v("\n                    and up-to-date checks")],1),e._v(" "),t("p",{staticClass:"card-text"},[e._v("\n                    If you execute this task several times in a row, you may notice the\n                    "),t("fgp-code",[e._v("npm")]),e._v("/"),t("fgp-code",[e._v("pnpm")]),e._v("/"),t("fgp-code",[e._v("yarn")]),e._v(" command is always\n                    executed: Gradle does not skip the task based on a previous execution with the\n                    "),t("fgp-gradle-task-outcome-link",{attrs:{outcome:"SUCCESS"}}),e._v(" outcome. This is the expected behaviour\n                    because the task does not declare any input/output Gradle could track, to know the task is\n                    already "),t("fgp-gradle-task-outcome-link",{attrs:{outcome:"UP-TO-DATE"}}),e._v(" (e.g. unlike task\n                    "),t("fgp-task-link",{attrs:{name:"installNode"}}),e._v("). The task provides the ability to plug the developer's own\n                    Javascript build process to Gradle, and nothing more. Every Javascript build process is unique:\n                    it depends on the project, the languages involved (e.g. TypeScript, JSX, ECMA script, SASS,\n                    SCSS...), the directory layout, the build utilities (Webpack...), etc., chosen by the team.\n                    Moreover, some build utilities are already able to build artifacts incrementally. The plugin\n                    does not duplicate this logic. If you are about to tweak this task, take a look at these\n                    "),t("fgp-site-link",{attrs:{path:"#tweaking-tasks"}},[e._v("recommendations")]),e._v(".\n                ")],1)])])]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports}}]);