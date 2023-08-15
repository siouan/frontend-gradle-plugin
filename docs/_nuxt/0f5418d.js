(window.webpackJsonp=window.webpackJsonp||[]).push([[57,26,27,28,33],{308:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.tasks,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},309:function(e,t,n){"use strict";n.r(t);n(31),n(26),n(46);var r=n(0),code=n(112),l=n(84),o=n(311),f=n(140),c=n(308),d=/^javax?\.([a-z]\w+\.)+[A-Z]\w+$/,_="java.lang.String",v=r.a.component("fgp-property",{components:{fgpCode:code.default,fgpLink:l.default,fgpPropertyLinkAnchor:o.default,fgpSiteLink:f.default,fgpTaskLink:c.default},props:{name:{type:String,required:!0},type:{type:String,required:!0},required:{type:Boolean,default:!0},defaultValue:{type:String,default:null},example:{type:String,default:null},tasks:{type:Array,required:!0}},computed:{defaultScriptValue:function(){return null===this.defaultValue?"null":this.type===_?'"'.concat(this.defaultValue,'"'):this.defaultValue},jdkHref:function(){return this.type&&d.test(this.type)?"https://docs.oracle.com/javase/8/docs/api/index.html?".concat(this.type.replace(/\./,"/"),".html"):null},scriptExample:function(){return this.type===_?'"'.concat(this.example,'"'):this.example}}}),y=n(13),component=Object(y.a)(v,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("article",{staticClass:"mb-3 border-bottom"},[t("header",[t("h4",[t("fgp-property-link-anchor",{attrs:{name:e.name}}),e._v("\n            Property\n            "),t("fgp-code",[e._v("\n                "+e._s(e.name)+"\n            ")]),e._v(" "),t("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[e._v("↑")])],1),e._v(" "),t("ul",[e.tasks.length>0?t("li",[e._v("\n                Related tasks:\n                "),e._l(e.tasks,(function(n,r){return t("span",{key:n},[t("fgp-task-link",{attrs:{name:n}}),r<e.tasks.length-1?[e._v(", ")]:e._e()],2)}))],2):e._e(),e._v(" "),t("li",[e._v("\n                Type:\n                "),e.jdkHref?[t("fgp-link",{attrs:{href:e.jdkHref}},[t("fgp-code",[e._v(e._s(e.type))])],1)]:[t("fgp-code",[e._v(e._s(e.type))])]],2),e._v(" "),t("li",[e._v("\n                Required: "),t("fgp-code",[e._v(e._s(e.required))])],1),e._v(" "),t("li",[e._v("\n                Default value: "),t("fgp-code",[e._v(e._s(e.defaultScriptValue))])],1),e._v(" "),e.example?t("li",[e._v("\n                Example: "),t("fgp-code",[e._v(e._s(e.scriptExample))])],1):e._e()])]),e._v(" "),t("section",{staticClass:"px-3"},[e._t("default")],2)])}),[],!1,null,null,null);t.default=component.exports;installComponents(component,{Header:n(114).default})},310:function(e,t,n){"use strict";n.r(t);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-site-link",{attrs:{path:"".concat(e.fgp.paths.configuration,"#").concat(e.name)}},[t("fgp-code",[e._v(e._s(e.name))])],1)}),[],!1,null,null,null);t.default=component.exports},311:function(e,t,n){"use strict";n.r(t);n(31);var r=n(0).a.component("fgp-property-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("span",{staticClass:"text-info",attrs:{id:e.name}},[e._v("#")])}),[],!1,null,null,null);t.default=component.exports},363:function(e,t,n){"use strict";n.r(t);var r=n(0),code=n(112),l=n(309),o=n(310),f=r.a.component("fgp-yarn-distribution-provided-property",{components:{fgpCode:code.default,fgpProperty:l.default,fgpPropertyLink:o.default}}),c=n(13),component=Object(c.a)(f,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-property",{attrs:{name:"yarnDistributionProvided",type:"boolean","default-value":"false",tasks:["installYarn"]}},[t("p",[e._v("Whether the "),t("fgp-yarn-link"),e._v(" distribution is already provided, and shall not be downloaded.")],1),e._v(" "),t("ol",[t("li",[e._v("\n            When "),t("fgp-code",[e._v("false")]),e._v(", at least the "),t("fgp-property-link",{attrs:{name:"yarnVersion"}}),e._v(" property must be\n            set.\n        ")],1),e._v(" "),t("li",[e._v("\n            When "),t("fgp-code",[e._v("true")]),e._v(", the plugin relies on the following locations in this exact order to find\n            "),t("fgp-code",[e._v("yarn")]),e._v(" executable:\n            "),t("ol",[t("li",[e._v("\n                    The directory pointed by the "),t("fgp-property-link",{attrs:{name:"yarnInstallDirectory"}}),e._v(" property, if set.\n                ")],1),e._v(" "),t("li",[e._v("The directory pointed by the "),t("fgp-code",[e._v("YARN_HOME")]),e._v(" environment variable, if set.")],1),e._v(" "),t("li",[e._v("Any directory in the "),t("fgp-code",[e._v("PATH")]),e._v(" environment variable.")],1)]),e._v("\n            Other "),t("fgp-code",[e._v("yarn*")]),e._v(" properties should not be used for clarity.\n        ")],1)])])}),[],!1,null,null,null);t.default=component.exports}}]);