(window.webpackJsonp=window.webpackJsonp||[]).push([[85,13,16,28,30,31,74,81],{308:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),c=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.configuration,"#").concat(e.name)}},[e.$slots.default?[e._t("default")]:t("fgp-code",[e._v(e._s(e.name))])],2)}),[],!1,null,null,null);t.default=component.exports},310:function(e,t,n){"use strict";n.r(t);var r=n(0),o=n(84),l=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:o.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=n(13),component=Object(c.a)(l,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(e.path),"hover-style-disabled":e.hoverStyleDisabled}},[e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},311:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),c=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.tasks,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},312:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0),o=n(310),l=n(308),c=n(140),d=n(311),f=n(313),v=n(314),_=r.a.component("fgp-task",{components:{fgpGradleDocsLink:o.default,fgpPropertyLink:l.default,fgpSiteLink:c.default,fgpTaskLink:d.default,fgpTaskLinkAnchor:f.default,fgpTaskPropertyType:v.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},dependingTasks:{type:Array,default:function(){return[]}},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),y=n(13),component=Object(y.a)(_,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("article",{staticClass:"mb-3 border-bottom"},[t("header",[t("h4",[t("fgp-task-link-anchor",{attrs:{name:e.name}}),e._v(" "),e.type?[e._v("Type")]:[e._v("Task")],e._v(" "),t("fgp-code",[e._v("\n                "+e._s(e.name)+"\n            ")]),e._v("\n            -\n            "),e._t("title"),e._v(" "),e.cacheable?t("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[e._v("C")]):e._e(),e._v(" "),t("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[e._v("↑")])],2),e._v(" "),t("ul",[e.dependingTasks.length>0?t("li",[e._v("\n                Depends on:\n                "),e._l(e.dependingTasks,(function(n,r){return t("span",{key:n},[t("fgp-task-link",{attrs:{name:n}}),r<e.dependingTasks.length-1?[e._v(", ")]:e._e()],2)}))],2):e._e(),e._v(" "),e.inputs.length>0?t("li",[e._v("\n                Inputs:\n                "),t("ul",e._l(e.inputs,(function(input,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:input.type}}),t("fgp-code",[e._v(e._s(input.name))]),e._v(":\n                        "),"P"===input.binding?[t("fgp-property-link",{attrs:{name:input.property}}),e._v(" property\n                        ")]:e._e(),e._v(" "),"C"===input.binding?e._t(input.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.outputs.length>0?t("li",[e._v("\n                Outputs:\n                "),t("ul",e._l(e.outputs,(function(output,n){return t("li",{key:n},[t("fgp-task-property-type",{attrs:{type:output.type}}),t("fgp-code",[e._v(e._s(output.name))]),e._v(":\n                        "),"C"===output.binding?e._t(output.name):e._e()],2)})),0)]):e._e(),e._v(" "),e.skippable?t("li",[e._v("\n                Skipped when "),e._t("skipConditions")],2):e._e()])]),e._v(" "),t("section",{staticClass:"px-3"},[e._t("description")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},313:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),o=n(13),component=Object(o.a)(r,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("span",{staticClass:"text-info",attrs:{id:e.name}},[e._v("#")])}),[],!1,null,null,null);t.default=component.exports},314:function(e,t,n){"use strict";n.r(t);n(46);var r=n(0),o=n(310),l="ET",c="F",d="S",f="RF",v=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:o.default},props:{type:{type:String,required:!0,validator:function(e){return[l,c,f,d].includes(e)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case f:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case l:case c:case d:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case l:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case f:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case d:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case l:return"badge-dark";case c:case d:return"badge-primary";case f:return"badge-danger";default:return null}}}}),_=n(13),component=Object(_.a)(v,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:e.chipClass,attrs:{path:e.gradleDocsLinkPath,title:e.gradleDocsLinkTitle,"hover-style-disabled":""}},[e._v(e._s(e.type))])}),[],!1,null,null,null);t.default=component.exports},318:function(e,t,n){"use strict";n.r(t);n(47),n(38),n(48),n(19),n(86),n(39),n(87);var r=n(32),o=n(0),l=n(63);function c(object,e){var t=Object.keys(object);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(object);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(object,e).enumerable}))),t.push.apply(t,n)}return t}function d(e){for(var i=1;i<arguments.length;i++){var source=null!=arguments[i]?arguments[i]:{};i%2?c(Object(source),!0).forEach((function(t){Object(r.a)(e,t,source[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(source)):c(Object(source)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(source,t))}))}return e}var f=o.a.component("fgp-gradle-scripts",{computed:d({groovyTabClass:function(){return{active:this.groovyTabVisible}},kotlinTabClass:function(){return{active:this.kotlinTabVisible}}},Object(l.b)("gradle-scripts",["groovyTabVisible","kotlinTabVisible"])),methods:d({},Object(l.c)("gradle-scripts",["setGroovyTabVisible","setKotlinTabVisible"]))}),v=n(13),component=Object(v.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("div",{staticClass:"mb-2"},[t("ul",{staticClass:"nav nav-tabs"},[t("li",{staticClass:"nav-item"},[t("nuxt-link",{staticClass:"nav-link",class:e.groovyTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(t){return t.preventDefault(),e.setGroovyTabVisible.apply(null,arguments)}}},[e._v("Groovy")])],1),e._v(" "),t("li",{staticClass:"nav-item"},[t("nuxt-link",{staticClass:"nav-link",class:e.kotlinTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(t){return t.preventDefault(),e.setKotlinTabVisible.apply(null,arguments)}}},[e._v("Kotlin")])],1)]),e._v(" "),t("div",{staticClass:"pt-3 pb-1 pl-3 bg-light"},[t("div",{directives:[{name:"show",rawName:"v-show",value:e.groovyTabVisible,expression:"groovyTabVisible"}]},[e._t("groovy")],2),e._v(" "),t("div",{directives:[{name:"show",rawName:"v-show",value:e.kotlinTabVisible,expression:"kotlinTabVisible"}]},[e._t("kotlin")],2)])])}),[],!1,null,null,null);t.default=component.exports},394:function(e,t,n){"use strict";n.r(t);var r=n(0),code=n(112),o=n(318),l=n(312),c=n(311),d=r.a.component("fgp-run-node-task-type",{components:{fgpCode:code.default,fgpGradleScripts:o.default,fgpTask:l.default,fgpTaskLink:c.default},data:function(){return{inputs:[{name:"packageJsonDirectory",type:"F",binding:"P",property:"packageJsonDirectory"},{name:"nodeInstallDirectory",type:"F",binding:"P",property:"nodeInstallDirectory"},{name:"script",type:"S",binding:"P",property:"script"}]}}}),f=n(13),component=Object(f.a)(d,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-task",{attrs:{name:"RunNode",type:"",inputs:e.inputs},scopedSlots:e._u([{key:"title",fn:function(){return[e._v("Run a custom command with "),t("fgp-code",[e._v("node")])]},proxy:!0},{key:"description",fn:function(){return[t("p",[e._v("\n                The plugin provides task type\n                "),t("fgp-code",[e._v("org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode")]),e._v(" that allows creating\n                a custom task to run a JS script. The "),t("fgp-code",[e._v("script")]),e._v(" property must be set with the\n                corresponding command. Then, choose whether "),t("fgp-nodejs-link"),e._v(" only is required, or if additional\n                dependencies located in the "),t("fgp-code",[e._v("package.json")]),e._v(" file should be installed: make the task\n                either depends on "),t("fgp-task-link",{attrs:{name:"installNode"}}),e._v(" task or on\n                "),t("fgp-task-link",{attrs:{name:"installFrontend"}}),e._v(" task. The code hereafter shows the configuration required to\n                run a JS script:\n            ")],1),e._v(" "),t("fgp-gradle-scripts",{staticClass:"my-3",attrs:{id:"run-node-example"},scopedSlots:e._u([{key:"groovy",fn:function(){return[t("pre",[t("fgp-code",[e._v("import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode\ntasks.register('myScript', RunNode) {\n    "),t("fgp-code-comment",[e._v("// dependsOn tasks.named('installNode')\n    // dependsOn tasks.named('installFrontend')")]),e._v("\n    script = 'my-script.js'\n}")],1)],1)]},proxy:!0},{key:"kotlin",fn:function(){return[t("pre",[t("fgp-code",[e._v('import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode\ntasks.register<RunNode>("myScript") {\n    '),t("fgp-code-comment",[e._v('// dependsOn(tasks.named("installNode"))\n    // dependsOn(tasks.named("installFrontend"))')]),e._v('\n    script.set("my-script.js")\n}')],1)],1)]},proxy:!0}])})]},proxy:!0}])})}),[],!1,null,null,null);t.default=component.exports}}]);