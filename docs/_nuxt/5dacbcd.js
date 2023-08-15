(window.webpackJsonp=window.webpackJsonp||[]).push([[59,26,27,28,33,83],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.tasks,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},309:function(t,e,n){"use strict";n.r(e);n(31),n(26),n(46);var r=n(0),code=n(112),l=n(84),o=n(311),f=n(140),c=n(308),d=/^javax?\.([a-z]\w+\.)+[A-Z]\w+$/,_="java.lang.String",v=r.a.component("fgp-property",{components:{fgpCode:code.default,fgpLink:l.default,fgpPropertyLinkAnchor:o.default,fgpSiteLink:f.default,fgpTaskLink:c.default},props:{name:{type:String,required:!0},type:{type:String,required:!0},required:{type:Boolean,default:!0},defaultValue:{type:String,default:null},example:{type:String,default:null},tasks:{type:Array,required:!0}},computed:{defaultScriptValue:function(){return null===this.defaultValue?"null":this.type===_?'"'.concat(this.defaultValue,'"'):this.defaultValue},jdkHref:function(){return this.type&&d.test(this.type)?"https://docs.oracle.com/javase/8/docs/api/index.html?".concat(this.type.replace(/\./,"/"),".html"):null},scriptExample:function(){return this.type===_?'"'.concat(this.example,'"'):this.example}}}),m=n(13),component=Object(m.a)(v,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-property-link-anchor",{attrs:{name:t.name}}),t._v("\n            Property\n            "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],1),t._v(" "),e("ul",[t.tasks.length>0?e("li",[t._v("\n                Related tasks:\n                "),t._l(t.tasks,(function(n,r){return e("span",{key:n},[e("fgp-task-link",{attrs:{name:n}}),r<t.tasks.length-1?[t._v(", ")]:t._e()],2)}))],2):t._e(),t._v(" "),e("li",[t._v("\n                Type:\n                "),t.jdkHref?[e("fgp-link",{attrs:{href:t.jdkHref}},[e("fgp-code",[t._v(t._s(t.type))])],1)]:[e("fgp-code",[t._v(t._s(t.type))])]],2),t._v(" "),e("li",[t._v("\n                Required: "),e("fgp-code",[t._v(t._s(t.required))])],1),t._v(" "),e("li",[t._v("\n                Default value: "),e("fgp-code",[t._v(t._s(t.defaultScriptValue))])],1),t._v(" "),t.example?e("li",[t._v("\n                Example: "),e("fgp-code",[t._v(t._s(t.scriptExample))])],1):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("default")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},310:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.configuration,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},311:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0).a.component("fgp-property-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports},317:function(t,e,n){"use strict";n.r(e);var r=n(0).a.component("fgp-warning",{}),l=n(13),component=Object(l.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("p",{staticClass:"text-danger px-2"},[e("i",{staticClass:"fas fa-exclamation-triangle mr-1"}),t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},367:function(t,e,n){"use strict";n.r(e);var r=n(0),code=n(112),l=n(309),o=n(310),f=n(317),c=r.a.component("fgp-yarn-distribution-server-username-property",{components:{fgpCode:code.default,fgpProperty:l.default,fgpPropertyLink:o.default,fgpWarning:f.default}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-property",{attrs:{name:"yarnDistributionServerUsername",type:"java.lang.String",required:!1,tasks:["installYarn"]}},[e("p",[t._v("\n        This property may be used to download the distribution with a given identity (BASIC scheme server-side).\n        When not "),e("fgp-code",[t._v("null")]),t._v(", the "),e("fgp-property-link",{attrs:{name:"yarnDistributionServerPassword"}}),t._v(" property\n        is also required.\n    ")],1),t._v(" "),e("fgp-warning",[t._v("\n        SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or\n        an external user property, and use one or the other as the value of this property.\n    ")])],1)}),[],!1,null,null,null);e.default=component.exports}}]);