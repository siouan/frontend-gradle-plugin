(window.webpackJsonp=window.webpackJsonp||[]).push([[67,14,28,74],{312:function(t,e,n){"use strict";n.r(e);var r=n(0),l=n(84),o=r.a.component("fgp-gradle-docs-link",{components:{fgpLink:l.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=n(13),component=Object(c.a)(o,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(t.path),"hover-style-disabled":t.hoverStyleDisabled}},[t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},313:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0),l=n(314),o=n(315),c=r.a.component("fgp-task",{components:{fgpTaskPropertyType:l.default,fgpTaskLinkAnchor:o.default},props:{name:{type:String,required:!0},type:{type:Boolean,default:!1},inputs:{type:Array,default:function(){return[]}},outputs:{type:Array,default:function(){return[]}},cacheable:{type:Boolean,default:!1}},computed:{skippable:function(){return!!this.$slots.skipConditions}}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-task-link-anchor",{attrs:{name:t.name}}),t._v(" "),t.type?[t._v("Type")]:[t._v("Task")],t._v(" "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v("\n            -\n            "),t._t("title"),t._v(" "),t.cacheable?e("fgp-gradle-docs-link",{staticClass:"badge badge-dark",attrs:{path:"/current/userguide/build_cache.html#sec:task_output_caching_details",title:"Cacheable task","hover-style-disabled":""}},[t._v("C")]):t._e(),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],2),t._v(" "),e("ul",[t.inputs.length>0?e("li",[t._v("\n                Inputs:\n                "),e("ul",t._l(t.inputs,(function(input,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:input.type}}),e("fgp-code",[t._v(t._s(input.name))]),t._v(":\n                        "),"P"===input.binding?[e("fgp-property-link",{attrs:{name:input.property}}),t._v(" property\n                        ")]:t._e(),t._v(" "),"C"===input.binding?t._t(input.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.outputs.length>0?e("li",[t._v("\n                Outputs:\n                "),e("ul",t._l(t.outputs,(function(output,n){return e("li",{key:n},[e("fgp-task-property-type",{attrs:{type:output.type}}),e("fgp-code",[t._v(t._s(output.name))]),t._v(":\n                        "),"C"===output.binding?t._t(output.name):t._e()],2)})),0)]):t._e(),t._v(" "),t.skippable?e("li",[t._v("\n                Skipped when "),t._t("skipConditions")],2):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("description")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},314:function(t,e,n){"use strict";n.r(e);n(47);var r=n(0),l=n(312),o="ET",c="F",d="S",f="RF",_=r.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:l.default},props:{type:{type:String,required:!0,validator:function(t){return[o,c,f,d].includes(t)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case f:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case o:case c:case d:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case o:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case f:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case d:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case o:return"badge-dark";case c:case d:return"badge-primary";case f:return"badge-danger";default:return null}}}}),h=n(13),component=Object(h.a)(_,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:t.chipClass,attrs:{path:t.gradleDocsLinkPath,title:t.gradleDocsLinkTitle,"hover-style-disabled":""}},[t._v(t._s(t.type))])}),[],!1,null,null,null);e.default=component.exports},315:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0).a.component("fgp-task-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports}}]);