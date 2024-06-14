(window.webpackJsonp=window.webpackJsonp||[]).push([[79,14,16,17,20,24,28,30,31,74,81],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),d=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),f=n(13),component=Object(f.a)(d,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.configuration,"#").concat(t.name)}},[t.$slots.default?[t._t("default")]:e("fgp-code",[t._v(t._s(t.name))])],2)}),[],!1,null,null,null);e.default=component.exports},310:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(84),l=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:o.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),d=n(13),component=Object(d.a)(l,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(t.path),"hover-style-disabled":t.hoverStyleDisabled}},[t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},311:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),d=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),f=n(13),component=Object(f.a)(d,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.tasks,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},312:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0),o=n(310),l=n(308),d=n(140),f=n(311),c=n(313),_=n(314),h=r.a.component("fgp-task",{components:{fgpGradleDocsLink:o.default,fgpPropertyLink:l.default,fgpSiteLink:d.default,fgpTaskLink:f.default,fgpTaskLinkAnchor:c.default,fgpTaskPropertyType:_.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},dependingTasks:{type:Array,default:function(){return[]}},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),v=n(13),component=Object(v.a)(h,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-task-link-anchor",{attrs:{name:t.name}}),t._v(" "),t.type?[t._v("Type")]:[t._v("Task")],t._v(" "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v("\n            -\n            "),t._t("title"),t._v(" "),t.cacheable?e("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[t._v("C")]):t._e(),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],2),t._v(" "),e("ul",[t.dependingTasks.length>0?e("li",[t._v("\n                Depends on:\n                "),t._l(t.dependingTasks,(function(n,r){return e("span",{key:n},[e("fgp-task-link",{attrs:{name:n}}),r<t.dependingTasks.length-1?[t._v(", ")]:t._e()],2)}))],2):t._e(),t._v(" "),t.inputs.length>0?e("li",[t._v("\n                Inputs:\n                "),e("ul",t._l(t.inputs,(function(input,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:input.type}}),e("fgp-code",[t._v(t._s(input.name))]),t._v(":\n                        "),"P"===input.binding?[e("fgp-property-link",{attrs:{name:input.property}}),t._v(" property\n                        ")]:t._e(),t._v(" "),"C"===input.binding?t._t(input.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.outputs.length>0?e("li",[t._v("\n                Outputs:\n                "),e("ul",t._l(t.outputs,(function(output,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:output.type}}),e("fgp-code",[t._v(t._s(output.name))]),t._v(":\n                        "),"C"===output.binding?t._t(output.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.skippable?e("li",[t._v("\n                Skipped when "),t._t("skipConditions")],2):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("description")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},313:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),o=n(13),component=Object(o.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports},314:function(t,e,n){"use strict";n.r(e);n(46);var r=n(0),o=n(310),l="ET",d="F",f="S",c="RF",_=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:o.default},props:{type:{type:String,required:!0,validator:function(t){return[l,d,c,f].includes(t)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case c:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case l:case d:case f:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case l:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case d:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case c:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case f:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case l:return"badge-dark";case d:case f:return"badge-primary";case c:return"badge-danger";default:return null}}}}),h=n(13),component=Object(h.a)(_,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:t.chipClass,attrs:{path:t.gradleDocsLinkPath,title:t.gradleDocsLinkTitle,"hover-style-disabled":""}},[t._v(t._s(t.type))])}),[],!1,null,null,null);e.default=component.exports},315:function(t,e,n){"use strict";n.r(e);var r=n(0).a.component("fgp-info",{}),o=n(13),component=Object(o.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("p",{staticClass:"text-info px-2"},[e("i",{staticClass:"fas fa-info-circle mr-1"}),t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},317:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(84),l="https://nodejs.org",d=r.a.component("fgp-nodejs-link",{components:{fgpLink:o.default},props:{path:{type:String,default:"/"},label:{type:String,default:null}},computed:{internalLabel:function(){return this.label||this.$t("navigation.nodejs.label")},nodeUrl:function(){return this.path?"".concat(l).concat(this.path):l}}}),f=n(13),component=Object(f.a)(d,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:t.nodeUrl}},[t._v(t._s(t.internalLabel))])}),[],!1,null,null,null);e.default=component.exports},326:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(310),l=r.a.component("fgp-gradle-task-outcome-link",{components:{fgpGradleDocsLink:o.default},props:{outcome:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(l,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-gradle-docs-link",{attrs:{path:"/current/userguide/more_about_tasks.html#sec:task_outcomes"}},[t._v(t._s(t.outcome))])}),[],!1,null,null,null);e.default=component.exports},336:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(84),l=r.a.component("fgp-gradle-guides-link",{components:{fgpLink:o.default},props:{path:{type:String,default:null}}}),d=n(13),component=Object(d.a)(l,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:"https://guides.gradle.org".concat(t.path)}},[t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},389:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(336),l=n(326),d=n(315),f=n(317),c=n(308),_=n(312),h=r.a.component("fgp-install-node-task",{components:{fgpGradleGuidesLink:o.default,fgpGradleTaskOutcomeLink:l.default,fgpInfo:d.default,fgpNodejsLink:f.default,fgpPropertyLink:c.default,fgpTask:_.default},data:function(){return{inputs:[{name:"nodeVersion",type:"S",binding:"P",property:"nodeVersion"},{name:"nodeDistributionUrlRoot",type:"S",binding:"P",property:"nodeDistributionUrlRoot"},{name:"nodeDistributionUrlPathPattern",type:"S",binding:"P",property:"nodeDistributionUrlPathPattern"},{name:"nodeInstallDirectory",type:"F",binding:"P",property:"nodeInstallDirectory"}],outputs:[{name:"nodeExecutableFile",type:"RF",binding:"C"}]}}}),v=n(13),component=Object(v.a)(h,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-task",{attrs:{name:"installNode",inputs:t.inputs,outputs:t.outputs},scopedSlots:t._u([{key:"title",fn:function(){return[t._v("Install "),e("fgp-nodejs-link")]},proxy:!0},{key:"nodeExecutableFile",fn:function(){return[e("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),e("fgp-code",[t._v("/node.exe")]),t._v(" or\n        "),e("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),e("fgp-code",[t._v("/bin/node")]),t._v(" depending on the O/S.\n    ")]},proxy:!0},{key:"skipConditions",fn:function(){return[t._v("\n        property "),e("fgp-property-link",{attrs:{name:"nodeDistributionProvided"}}),t._v(" is "),e("fgp-code",[t._v("true")]),t._v(".\n    ")]},proxy:!0},{key:"description",fn:function(){return[e("p",[t._v("\n            The task downloads a "),e("fgp-nodejs-link"),t._v(" distribution, verifies its integrity, and installs it in the\n            directory pointed by the "),e("fgp-property-link",{attrs:{name:"nodeInstallDirectory"}}),t._v(" property (if this property is\n            "),e("fgp-code",[t._v("null")]),t._v(", the distribution is installed in directory\n            "),e("fgp-code",[t._v("${projectDir}/node")]),t._v("). The URL used to download the distribution is resolved using\n            the "),e("fgp-property-link",{attrs:{name:"nodeDistributionUrlRoot"}}),t._v(" property and the\n            "),e("fgp-property-link",{attrs:{name:"nodeDistributionUrlPathPattern"}}),t._v(" property. Checking the distribution\n            integrity consists of downloading a file providing the distribution shasum. This file is expected to be\n            in the same remote web directory than the distribution archive. For example, if the distribution is\n            located at "),e("fgp-code",[t._v("https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip")]),t._v(", the plugin\n            attempts to download the shasum file located at\n            "),e("fgp-code",[t._v("https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt")]),t._v(". By default, the plugin relies on the\n            VM "),e("fgp-java-network-properties-link",[t._v("network properties")]),t._v(" to know if a\n            proxy server shall be used when downloading the distribution and the shasum. A custom proxy server may\n            also be used by defining "),e("fgp-property-link",{attrs:{name:"httpsProxyHost"}}),t._v(" property (respectively\n            "),e("fgp-property-link",{attrs:{name:"httpProxyHost"}}),t._v(" property) if the\n            "),e("fgp-property-link",{attrs:{name:"nodeDistributionUrlRoot"}}),t._v(" property uses the "),e("fgp-code",[t._v("https")]),t._v("\n            protocol (resp. uses the "),e("fgp-code",[t._v("http")]),t._v(" protocol). In case of connectivity/HTTP error,\n            download of the distribution file and the shasum file may be retried using property\n            "),e("fgp-property-link",{attrs:{name:"maxDownloadAttempts"}}),t._v(".\n        ")],1),t._v(" "),e("p",[t._v("\n            If a "),e("fgp-nodejs-link"),t._v(" distribution is already installed in the system - either as a global\n            installation or as an installation performed by another Gradle (sub-)project - and shall be used instead\n            of a downloaded distribution, take a look at the\n            "),e("fgp-property-link",{attrs:{name:"nodeDistributionProvided"}}),t._v(" property instead: when "),e("fgp-code",[t._v("true")]),t._v(",\n            this task is ignored if invoked during a Gradle build, and its outcome will always be\n            "),e("fgp-gradle-task-outcome-link",{attrs:{outcome:"SKIPPED"}}),t._v(".\n        ")],1),t._v(" "),e("p",[t._v("\n            The task takes advantage of\n            "),e("fgp-gradle-guides-link",{attrs:{path:"/performance/#incremental_build"}},[t._v("Gradle incremental build")]),t._v(", and is not executed again unless one of its inputs/outputs changed. Consequently, if the task takes\n            part of a Gradle build, its outcome will be "),e("fgp-gradle-task-outcome-link",{attrs:{outcome:"UP-TO-DATE"}}),t._v(".\n        ")],1),t._v(" "),e("fgp-info",[t._v("\n            This task should not be executed directly. Gradle executes it if the build requires it.\n        ")])]},proxy:!0}])})}),[],!1,null,null,null);e.default=component.exports}}]);