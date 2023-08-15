(window.webpackJsonp=window.webpackJsonp||[]).push([[74,12,14,28,29,68,75],{308:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),c=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),f=n(13),component=Object(f.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.tasks,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},312:function(e,t,n){"use strict";n.r(t);var r=n(0),l=n(84),o=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:l.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=n(13),component=Object(c.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(e.path),"hover-style-disabled":e.hoverStyleDisabled}},[e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},313:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0),l=n(314),o=n(315),c=r.a.component("fgp-task",{components:{fgpTaskPropertyType:l.default,fgpTaskLinkAnchor:o.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),f=n(13),component=Object(f.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("article",{staticClass:"mb-3 border-bottom"},[t("header",[t("h4",[t("fgp-task-link-anchor",{attrs:{name:e.name}}),e._v(" "),e.type?[e._v("Type")]:[e._v("Task")],e._v(" "),t("fgp-code",[e._v("\n                "+e._s(e.name)+"\n            ")]),e._v("\n            -\n            "),e._t("title"),e._v(" "),e.cacheable?t("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[e._v("C")]):e._e(),e._v(" "),t("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[e._v("↑")])],2),e._v(" "),t("ul",[e.inputs.length>0?t("li",[e._v("\n                Inputs:\n                "),t("ul",e._l(e.inputs,(function(input,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:input.type}}),t("fgp-code",[e._v(e._s(input.name))]),e._v(":\n                        "),"P"===input.binding?[t("fgp-property-link",{attrs:{name:input.property}}),e._v(" property\n                        ")]:e._e(),e._v(" "),"C"===input.binding?e._t(input.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.outputs.length>0?t("li",[e._v("\n                Outputs:\n                "),t("ul",e._l(e.outputs,(function(output,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:output.type}}),t("fgp-code",[e._v(e._s(output.name))]),e._v(":\n                        "),"C"===output.binding?e._t(output.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.skippable?t("li",[e._v("\n                Skipped when "),e._t("skipConditions")],2):e._e()])]),e._v(" "),t("section",{staticClass:"px-3"},[e._t("description")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},314:function(e,t,n){"use strict";n.r(t);n(47);var r=n(0),l=n(312),o="ET",c="F",f="S",d="RF",_=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:l.default},props:{type:{type:String,required:!0,validator:function(e){return[o,c,d,f].includes(e)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case d:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case o:case c:case f:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case o:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case d:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case f:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case o:return"badge-dark";case c:case f:return"badge-primary";case d:return"badge-danger";default:return null}}}}),k=n(13),component=Object(k.a)(_,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:e.chipClass,attrs:{path:e.gradleDocsLinkPath,title:e.gradleDocsLinkTitle,"hover-style-disabled":""}},[e._v(e._s(e.type))])}),[],!1,null,null,null);t.default=component.exports},315:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("span",{staticClass:"text-info",attrs:{id:e.name}},[e._v("#")])}),[],!1,null,null,null);t.default=component.exports},316:function(e,t,n){"use strict";n.r(t);var r=n(0).a.component("fgp-info",{}),l=n(13),component=Object(l.a)(r,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("p",{staticClass:"text-info px-2"},[t("i",{staticClass:"fas fa-info-circle mr-1"}),e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},380:function(e,t,n){"use strict";n.r(t);var r=n(0),code=n(112),l=n(316),o=n(313),c=n(308),f=r.a.component("fgp-install-package-manager-task",{components:{fgpCode:code.default,fgpInfo:l.default,fgpTask:o.default,fgpTaskLink:c.default},data:function(){return{inputs:[{name:"packageManagerSpecificationFile",type:"RF",binding:"C"}],outputs:[{name:"packageManagerExecutableFile",type:"RF",binding:"C"}]}}}),d=n(13),component=Object(d.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-task",{attrs:{name:"installPackageManager",inputs:e.inputs,outputs:e.outputs},scopedSlots:e._u([{key:"title",fn:function(){return[e._v("Install package manager")]},proxy:!0},{key:"packageManagerSpecificationFile",fn:function(){return[t("fgp-property-link",{attrs:{name:"cacheDirectory"}}),t("fgp-code",[e._v("/resolvePackageManager/package-manager-specification.txt")])]},proxy:!0},{key:"packageManagerExecutableFile",fn:function(){return[t("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),t("fgp-code",[e._v("/[npm|pnpm|yarn].cmd")]),e._v(" or\n        "),t("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),t("fgp-code",[e._v("/bin/[npm|pnpm|yarn]")]),e._v(" depending on the O/S.\n    ")]},proxy:!0},{key:"skipConditions",fn:function(){return[e._v("\n        file "),t("fgp-property-link",{attrs:{name:"cacheDirectory"}}),t("fgp-code",[e._v("/resolvePackageManager/package-manager-executable-path.txt")]),e._v(" does not exist.\n    ")]},proxy:!0},{key:"description",fn:function(){return[t("p",[e._v("\n            The task installs the package manager resolved with task "),t("fgp-task-link",{attrs:{name:"resolvePackageManager"}}),e._v(",\n            by executing command "),t("fgp-code",[e._v("corepack enable <package-manager>")]),e._v(".\n        ")],1),e._v(" "),t("p",[e._v("\n            The task takes advantage of\n            "),t("fgp-gradle-guides-link",{attrs:{path:"/performance/#incremental_build"}},[e._v("Gradle incremental build")]),e._v(", and is not executed again unless one of its\n            inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be\n            "),t("fgp-gradle-task-outcome-link",{attrs:{outcome:"UP-TO-DATE"}}),e._v(".\n        ")],1),e._v(" "),t("fgp-info",[e._v("\n            This task should not be executed directly. Gradle executes it if the build requires it.\n        ")])]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports}}]);