(window.webpackJsonp=window.webpackJsonp||[]).push([[61,25,26,27,29,32],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var l=n(0),code=n(112),r=n(140),o=n(85),f=l.a.component("fgp-task-link",{components:{fgpCode:code.default,fgpSiteLink:r.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.tasks,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},309:function(t,e,n){"use strict";n.r(e);n(31),n(26),n(46);var l=n(0),code=n(112),r=n(84),o=n(311),f=n(140),c=n(308),d=/^javax?\.([a-z]\w+\.)+[A-Z]\w+$/,_="java.lang.String",y=l.a.component("fgp-property",{components:{fgpCode:code.default,fgpLink:r.default,fgpPropertyLinkAnchor:o.default,fgpSiteLink:f.default,fgpTaskLink:c.default},props:{name:{type:String,required:!0},type:{type:String,required:!0},required:{type:Boolean,default:!0},defaultValue:{type:String,default:null},example:{type:String,default:null},tasks:{type:Array,required:!0}},computed:{defaultScriptValue:function(){return null===this.defaultValue?"null":this.type===_?'"'.concat(this.defaultValue,'"'):this.defaultValue},jdkHref:function(){return this.type&&d.test(this.type)?"https://docs.oracle.com/javase/8/docs/api/index.html?".concat(this.type.replace(/\./,"/"),".html"):null},scriptExample:function(){return this.type===_?'"'.concat(this.example,'"'):this.example}}}),h=n(13),component=Object(h.a)(y,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("article",{staticClass:"mb-3 border-bottom"},[e("header",[e("h4",[e("fgp-property-link-anchor",{attrs:{name:t.name}}),t._v("\n            Property\n            "),e("fgp-code",[t._v("\n                "+t._s(t.name)+"\n            ")]),t._v(" "),e("fgp-site-link",{staticClass:"small text-info",attrs:{path:"#app"}},[t._v("↑")])],1),t._v(" "),e("ul",[t.tasks.length>0?e("li",[t._v("\n                Related tasks:\n                "),t._l(t.tasks,(function(n,l){return e("span",{key:n},[e("fgp-task-link",{attrs:{name:n}}),l<t.tasks.length-1?[t._v(", ")]:t._e()],2)}))],2):t._e(),t._v(" "),e("li",[t._v("\n                Type:\n                "),t.jdkHref?[e("fgp-link",{attrs:{href:t.jdkHref}},[e("fgp-code",[t._v(t._s(t.type))])],1)]:[e("fgp-code",[t._v(t._s(t.type))])]],2),t._v(" "),e("li",[t._v("\n                Required: "),e("fgp-code",[t._v(t._s(t.required))])],1),t._v(" "),e("li",[t._v("\n                Default value: "),e("fgp-code",[t._v(t._s(t.defaultScriptValue))])],1),t._v(" "),t.example?e("li",[t._v("\n                Example: "),e("fgp-code",[t._v(t._s(t.scriptExample))])],1):t._e()])]),t._v(" "),e("section",{staticClass:"px-3"},[t._t("default")],2)])}),[],!1,null,null,null);e.default=component.exports;installComponents(component,{Header:n(114).default})},310:function(t,e,n){"use strict";n.r(e);n(113),n(31);var l=n(0),code=n(112),r=n(140),o=n(85),f=l.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:r.default},mixins:[o.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.configuration,"#").concat(t.name)}},[e("fgp-code",[t._v(t._s(t.name))])],1)}),[],!1,null,null,null);e.default=component.exports},311:function(t,e,n){"use strict";n.r(e);n(31);var l=n(0).a.component("fgp-property-link-anchor",{props:{name:{type:String,required:!0}}}),r=n(13),component=Object(r.a)(l,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("span",{staticClass:"text-info",attrs:{id:t.name}},[t._v("#")])}),[],!1,null,null,null);e.default=component.exports},322:function(t,e,n){"use strict";n.r(e);var l=n(0),r=n(84),o=l.a.component("fgp-yarn-link",{components:{fgpLink:r.default},props:{label:{type:String,default:null},labelKey:{type:String,default:"navigation.yarnBerry.label"},path:{type:String,default:""},title:{type:String,default:null}},computed:{href:function(){return"https://yarnpkg.com/".concat(this.path)},internalLabel:function(){return this.label||this.$t(this.labelKey)},internalTitle:function(){return this.title||this.$t("navigation.yarnBerry.title")}}}),f=n(13),component=Object(f.a)(o,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-link",{attrs:{href:t.href,title:t.internalTitle}},[t._v(t._s(t.internalLabel))])}),[],!1,null,null,null);e.default=component.exports},362:function(t,e,n){"use strict";n.r(e);var l=n(0),code=n(112),r=n(309),o=n(310),f=n(322),c=l.a.component("fgp-yarn-enabled-property",{components:{fgpCode:code.default,fgpProperty:r.default,fgpPropertyLink:o.default,fgpYarnLink:f.default}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-property",{attrs:{name:"yarnEnabled",type:"boolean","default-value":"false",tasks:["installYarn","installFrontend","cleanFrontend","assembleFrontend","checkFrontend","publishFrontend"]}},[e("p",[t._v("\n        Whether "),e("fgp-code",[t._v("yarn")]),t._v(" executable shall be used instead of "),e("fgp-code",[t._v("npm")]),t._v(" executable\n        when executing frontend tasks. Consequently, a "),e("fgp-yarn-link"),t._v(" distribution is required, and the plugin\n        will locate it depending on the value of the "),e("fgp-property-link",{attrs:{name:"yarnDistributionProvided"}}),t._v(" property.\n    ")],1)])}),[],!1,null,null,null);e.default=component.exports}}]);