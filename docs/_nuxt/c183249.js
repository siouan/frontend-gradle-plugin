(window.webpackJsonp=window.webpackJsonp||[]).push([[74,14],{312:function(e,t,r){"use strict";r.r(t);var n=r(0),l=r(84),o=n.a.component("fgp-gradle-docs-link",{components:{fgpLink:l.default},props:{path:{type:String,default:null},hoverStyleDisabled:{type:Boolean,default:!1}}}),c=r(13),component=Object(c.a)(o,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-link",{attrs:{href:"https://docs.gradle.org".concat(e.path),"hover-style-disabled":e.hoverStyleDisabled}},[e._t("default")],2)}),[],!1,null,null,null);t.default=component.exports},314:function(e,t,r){"use strict";r.r(t);r(47);var n=r(0),l=r(312),o="ET",c="F",d="S",f="RF",h=n.a.component("fgp-task-property-type",{components:{fgpGradleDocsLink:l.default},props:{type:{type:String,required:!0,validator:function(e){return[o,c,f,d].includes(e)}}},computed:{gradleDocsLinkPath:function(){switch(this.type){case f:return"/current/javadoc/org/gradle/api/file/RegularFileProperty.html";case o:case c:case d:return"/current/javadoc/org/gradle/api/provider/Property.html";default:return null}},gradleDocsLinkTitle:function(){switch(this.type){case o:return"Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)";case c:return"Provider of java.io.File instance (task is out-of-date if the path changes)";case f:return"Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)";case d:return"Provider of java.lang.String instance (task is out-of-date if the value changes)";default:return null}},chipClass:function(){switch(this.type){case o:return"badge-dark";case c:case d:return"badge-primary";case f:return"badge-danger";default:return null}}}}),v=r(13),component=Object(v.a)(h,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("fgp-gradle-docs-link",{staticClass:"badge mr-1",class:e.chipClass,attrs:{path:e.gradleDocsLinkPath,title:e.gradleDocsLinkTitle,"hover-style-disabled":""}},[e._v(e._s(e.type))])}),[],!1,null,null,null);t.default=component.exports}}]);