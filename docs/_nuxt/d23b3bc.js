(window.webpackJsonp=window.webpackJsonp||[]).push([[40,28,89],{308:function(t,e,n){"use strict";n.r(e);n(113),n(31);var r=n(0),code=n(112),o=n(140),l=n(85),f=r.a.component("fgp-property-link",{components:{fgpCode:code.default,fgpSiteLink:o.default},mixins:[l.a],props:{name:{type:String,required:!0}}}),c=n(13),component=Object(c.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-site-link",{attrs:{path:"".concat(t.fgp.paths.configuration,"#").concat(t.name)}},[t.$slots.default?[t._t("default")]:e("fgp-code",[t._v(t._s(t.name))])],2)}),[],!1,null,null,null);e.default=component.exports},316:function(t,e,n){"use strict";n.r(e);var r=n(0).a.component("fgp-warning",{}),o=n(13),component=Object(o.a)(r,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("p",{staticClass:"text-danger px-2"},[e("i",{staticClass:"fas fa-exclamation-triangle mr-1"}),t._t("default")],2)}),[],!1,null,null,null);e.default=component.exports},346:function(t,e,n){"use strict";n.r(e);var r=n(0),code=n(112),o=n(309),l=n(308),f=n(316),c=r.a.component("fgp-http-proxy-password-property",{components:{fgpCode:code.default,fgpProperty:o.default,fgpPropertyLink:l.default,fgpWarning:f.default}}),d=n(13),component=Object(d.a)(c,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("fgp-property",{attrs:{name:"httpProxyPassword",type:"java.lang.String",required:!1,tasks:["installNode","installYarn"]}},[e("p",[t._v("\n        Set this property to download distributions through the proxy server with an authenticated identity (BASIC\n        scheme server-side). The property is ignored unless the "),e("fgp-property-link",{attrs:{name:"httpProxyUsername"}}),t._v("\n        property is not "),e("fgp-code",[t._v("null")]),t._v(".\n    ")],1),t._v(" "),e("fgp-warning",[t._v("\n        SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or\n        an external user property, and use one or the other as the value of this property.\n    ")])],1)}),[],!1,null,null,null);e.default=component.exports}}]);