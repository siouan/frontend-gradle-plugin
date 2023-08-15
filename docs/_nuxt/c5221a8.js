(window.webpackJsonp=window.webpackJsonp||[]).push([[38,26,27,28,33],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.tasks,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},309:function(t,e,n){"use strict";n.r(e);n(31),n(26),n(46);var r=n(0),code=n(112),l=n(84),o=n(311),f=n(140),c=n(308),d=/^javax?\.([a-z]\w+\.)+[A-Z]\w+$/,_="java.lang.String",v=r.a.component("fgp-property",{components:{fgpCode:code.default,fgpLink:l.default,fgpPropertyLinkAnchor:o.default,fgpSiteLink:f.default,fgpTaskLink:c.default},props:{name:{type:String,required:!0},type:{type:String,required:!0},required:{type:Boolean,default:!0},defaultValue:{type:String,default:null},example:{type:String,default:null},tasks:{type:Array,required:!0}},computed:{defaultScriptValue:function(){return null===this.defaultValue?"null":this.type===_?'"'.concat(this.defaultValue,'"'):this.defaultValue},jdkHref:function(){return this.type&&d.test(this.type)?"https://docs.oracle.com/javase/8/docs/api/index.html?".concat(this.type.replace(/\./,"/"),".html"):null},scriptExample:function(){return this.type===_?'"'.concat(this.example,'"'):this.example}}}),y=n(13),component=Object(y.a)(v,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-property-link-anchor",{attrs:{name:t.name}}),t._v("\n            Property\n            "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],1),t._v(" "),e("ul",[t.tasks.length>0?e("li",[t._v("\n                Related tasks:\n                "),t._l(t.tasks,(function(n,r){return e("span",{key:n},[e("fgp-task-link",{attrs:{name:n}}),r<t.tasks.length-1?[t._v(", ")]:t._e()],2)}))],2):t._e(),t._v(" "),e("li",[t._v("\n                Type:\n                "),t.jdkHref?[e("fgp-link",{attrs:{href:t.jdkHref}},[e("fgp-code",[t._v(t._s(t.type))])],1)]:[e("fgp-code",[t._v(t._s(t.type))])]],2),t._v(" "),e("li",[t._v("\n                Required: "),e("fgp-code",[t._v(t._s(t.required))])],1),t._v(" "),e("li",[t._v("\n                Default value: "),e("fgp-code",[t._v(t._s(t.defaultScriptValue))])],1),t._v(" "),t.example?e("li",[t._v("\n                Example: "),e("fgp-code",[t._v(t._s(t.scriptExample))])],1):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("default")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},310:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),l=n(140),o=n(85),f=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:l.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.configuration,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},311:function(t,e,n){"use strict";n.r(e);n(31);var r=n(0).a.component("fgp-property-link-anchor",{props:{name:{type:String,required:!0}}}),l=n(13),component=Object(l.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports},352:function(t,e,n){"use strict";n.r(e);var r=n(0),code=n(112),l=n(309),o=n(310),f=r.a.component("fgp-http-proxy-host-property",{components:{fgpCode:code.default,fgpProperty:l.default,fgpPropertyLink:o.default}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-property",{attrs:{name:"httpProxyHost",type:"java.lang.String",required:!1,example:"127.0.0.1",tasks:["installNode","installYarn"]}},[e("p",[t._v("\n        IP address or domain name of a HTTP proxy server to use when downloading distributions with a non-secure\n        HTTP protocol (e.g. the "),e("fgp-property-link",{attrs:{name:"nodeDistributionUrlRoot"}}),t._v(" property and/or the\n        "),e("fgp-property-link",{attrs:{name:"yarnDistributionUrlRoot"}}),t._v(" property use the "),e("fgp-code",[t._v("http")]),t._v(" protocol).\n        The "),e("fgp-site-link",{attrs:{path:"#proxy-resolution-process"}},[t._v("proxy resolution process")]),t._v(" hereafter\n        explains how this setting apply.\n    ")],1)])}),[],!1,null,null,null);e.default=component.exports}}]);