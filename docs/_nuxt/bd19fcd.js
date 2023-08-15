(window.webpackJsonp=window.webpackJsonp||[]).push([[80,11,14,28,29,68,75],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),c=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.tasks,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},312:function(t,e,n){"use strict";n.r(e);var r=n(0),o=n(84),l=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:o.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=n(13),component=Object(c.a)(l,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(t.path),"hover-style-disabled":t.hoverStyleDisabled}},[t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},313:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0),o=n(314),l=n(315),c=r.a.component("fgp-task",{components:{fgpTaskPropertyType:o.default,fgpTaskLinkAnchor:l.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-task-link-anchor",{attrs:{name:t.name}}),t._v(" "),t.type?[t._v("Type")]:[t._v("Task")],t._v(" "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v("\n            -\n            "),t._t("title"),t._v(" "),t.cacheable?e("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[t._v("C")]):t._e(),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],2),t._v(" "),e("ul",[t.inputs.length>0?e("li",[t._v("\n                Inputs:\n                "),e("ul",t._l(t.inputs,(function(input,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:input.type}}),e("fgp-code",[t._v(t._s(input.name))]),t._v(":\n                        "),"P"===input.binding?[e("fgp-property-link",{attrs:{name:input.property}}),t._v(" property\n                        ")]:t._e(),t._v(" "),"C"===input.binding?t._t(input.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.outputs.length>0?e("li",[t._v("\n                Outputs:\n                "),e("ul",t._l(t.outputs,(function(output,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:output.type}}),e("fgp-code",[t._v(t._s(output.name))]),t._v(":\n                        "),"C"===output.binding?t._t(output.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.skippable?e("li",[t._v("\n                Skipped when "),t._t("skipConditions")],2):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("description")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},314:function(t,e,n){"use strict";n.r(e);n(47);var r=n(0),o=n(312),l="ET",c="F",d="S",f="RF",v=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:o.default},props:{type:{type:String,required:!0,validator:function(t){return[l,c,f,d].includes(t)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case f:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case l:case c:case d:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case l:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case f:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case d:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case l:return"badge-dark";case c:case d:return"badge-primary";case f:return"badge-danger";default:return null}}}}),_=n(13),component=Object(_.a)(v,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:t.chipClass,attrs:{path:t.gradleDocsLinkPath,title:t.gradleDocsLinkTitle,"hover-style-disabled":""}},[t._v(t._s(t.type))])}),[],!1,null,null,null);e.default=component.exports},315:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),o=n(13),component=Object(o.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports},319:function(t,e,n){"use strict";n.r(e);n(49),n(38),n(48),n(19),n(86),n(39),n(87);var r=n(32),o=n(0),l=n(63);function c(object,t){var e=Object.keys(object);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(object);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(object,t).enumerable}))),e.push.apply(e,n)}return e}function d(t){for(var i=1;i<arguments.length;i++){var source=null!=arguments[i]?arguments[i]:{};i%2?c(Object(source),!0).forEach((function(e){Object(r.a)(t,e,source[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(source)):c(Object(source)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(source,e))}))}return t}var f=o.a.component("fgp-gradle-scripts",{computed:d({groovyTabClass:function(){return{active:this.groovyTabVisible}},kotlinTabClass:function(){return{active:this.kotlinTabVisible}}},Object(l.b)("gradle-scripts",["groovyTabVisible","kotlinTabVisible"])),methods:d({},Object(l.c)("gradle-scripts",["setGroovyTabVisible","setKotlinTabVisible"]))}),v=n(13),component=Object(v.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",{staticClass:"mb-2"},[e("ul",{staticClass:"nav nav-tabs"},[e("li",{staticClass:"nav-item"},[e("nuxt-link",{staticClass:"nav-link",class:t.groovyTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(e){return e.preventDefault(),t.setGroovyTabVisible.apply(null,arguments)}}},[t._v("Groovy")])],1),t._v(" "),e("li",{staticClass:"nav-item"},[e("nuxt-link",{staticClass:"nav-link",class:t.kotlinTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(e){return e.preventDefault(),t.setKotlinTabVisible.apply(null,arguments)}}},[t._v("Kotlin")])],1)]),t._v(" "),e("div",{staticClass:"pt-3 pb-1 pl-3 bg-light"},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.groovyTabVisible,expression:"groovyTabVisible"}]},[t._t("groovy")],2),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.kotlinTabVisible,expression:"kotlinTabVisible"}]},[t._t("kotlin")],2)])])}),[],!1,null,null,null);e.default=component.exports},385:function(t,e,n){"use strict";n.r(e);var r=n(0),code=n(112),o=n(319),l=n(313),c=n(308),d=r.a.component("fgp-run-npm-task-type",{components:{fgpCode:code.default,fgpGradleScripts:o.default,fgpTask:l.default,fgpTaskLink:c.default},data:function(){return{inputs:[{name:"packageJsonDirectory",type:"F",binding:"P",property:"packageJsonDirectory"},{name:"nodeInstallDirectory",type:"F",binding:"P",property:"nodeInstallDirectory"},{name:"script",type:"S",binding:"P",property:"script"}]}}}),f=n(13),component=Object(f.a)(d,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-task",{attrs:{name:"RunNpm",type:"",inputs:t.inputs},scopedSlots:t._u([{key:"title",fn:function(){return[t._v("Run a custom command with "),e("fgp-code",[t._v("npm")])]},proxy:!0},{key:"description",fn:function(){return[e("p",[t._v("\n                The plugin provides task type\n                "),e("fgp-code",[t._v("org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm")]),t._v(" that allows creating\n                a custom task to run a "),e("fgp-code",[t._v("npm")]),t._v(" command. The "),e("fgp-code",[t._v("script")]),t._v(" property must\n                be set with the corresponding command. Then, choose whether additional dependencies located in the\n                "),e("fgp-code",[t._v("package.json")]),t._v(" file should be installed: make the task either depends on\n                "),e("fgp-task-link",{attrs:{name:"installPackageManager"}}),t._v(" task or on "),e("fgp-task-link",{attrs:{name:"installFrontend"}}),t._v(" task.\n                The code hereafter shows the configuration required to output the version of "),e("fgp-code",[t._v("npm")]),t._v(":\n            ")],1),t._v(" "),e("fgp-gradle-scripts",{staticClass:"my-3",attrs:{id:"run-npm-example"},scopedSlots:t._u([{key:"groovy",fn:function(){return[e("pre",[e("fgp-code",[t._v("import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm\ntasks.register('npmVersion', RunNpm) {\n    "),e("fgp-code-comment",[t._v("// dependsOn tasks.named('installPackageManager')\n    // dependsOn tasks.named('installFrontend')")]),t._v("\n    script = '--version'\n}")],1)],1)]},proxy:!0},{key:"kotlin",fn:function(){return[e("pre",[e("fgp-code",[t._v('import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm\ntasks.register<RunNpm>("npmVersion") {\n    '),e("fgp-code-comment",[t._v('// dependsOn(tasks.named("installPackageManager"))\n    // dependsOn(tasks.named("installFrontend"))')]),t._v('\n    script.set("--version")\n}')],1)],1)]},proxy:!0}])})]},proxy:!0}])})}),[],!1,null,null,null);e.default=component.exports}}]);