(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{318:function(t,e,n){"use strict";n.r(e);n(47),n(38),n(48),n(19),n(86),n(39),n(87);var r=n(32),l=n(0),o=n(63);function c(object,t){var e=Object.keys(object);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(object);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(object,t).enumerable}))),e.push.apply(e,n)}return e}function v(t){for(var i=1;i<arguments.length;i++){var source=null!=arguments[i]?arguments[i]:{};i%2?c(Object(source),!0).forEach((function(e){Object(r.a)(t,e,source[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(source)):c(Object(source)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(source,e))}))}return t}var f=l.a.component("fgp-gradle-scripts",{computed:v({groovyTabClass:function(){return{active:this.groovyTabVisible}},kotlinTabClass:function(){return{active:this.kotlinTabVisible}}},Object(o.b)("gradle-scripts",["groovyTabVisible","kotlinTabVisible"])),methods:v({},Object(o.c)("gradle-scripts",["setGroovyTabVisible","setKotlinTabVisible"]))}),y=n(13),component=Object(y.a)(f,(function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",{staticClass:"mb-2"},[e("ul",{staticClass:"nav nav-tabs"},[e("li",{staticClass:"nav-item"},[e("nuxt-link",{staticClass:"nav-link",class:t.groovyTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(e){return e.preventDefault(),t.setGroovyTabVisible.apply(null,arguments)}}},[t._v("Groovy")])],1),t._v(" "),e("li",{staticClass:"nav-item"},[e("nuxt-link",{staticClass:"nav-link",class:t.kotlinTabClass,attrs:{to:"#",append:"",event:""},nativeOn:{click:function(e){return e.preventDefault(),t.setKotlinTabVisible.apply(null,arguments)}}},[t._v("Kotlin")])],1)]),t._v(" "),e("div",{staticClass:"pt-3 pb-1 pl-3 bg-light"},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.groovyTabVisible,expression:"groovyTabVisible"}]},[t._t("groovy")],2),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.kotlinTabVisible,expression:"kotlinTabVisible"}]},[t._t("kotlin")],2)])])}),[],!1,null,null,null);e.default=component.exports}}]);