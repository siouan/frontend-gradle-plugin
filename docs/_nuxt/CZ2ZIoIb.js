import{u as F,a as y,_ as j}from"./9-PJfM6s.js";import{_ as v}from"./DOrgdP5_.js";import{_ as D}from"./ZKfSxdRe.js";import{_ as x}from"./BwUBF5Zk.js";import{_ as J}from"./DKQOUftO.js";import{_ as K,a as G}from"./Fh2q3ca0.js";import{_ as w}from"./a-vbRXXh.js";import{_ as N}from"./DZ65drhv.js";import{_ as S}from"./B9QobURR.js";import{_ as $}from"./CgKqJOwV.js";import{u as L}from"./BzCyTmLR.js";import{g as T,c as C,b as e,w as l,a as t,d as n,o as I}from"./BviPqJDo.js";import"./DlAUqK2U.js";import"./BM98ekuH.js";const B=t("p",null,"The following tools must be installed to use the plugin:",-1),U={class:"list-unstyled my-2 me-3"},R={class:"mb-2"},q=t("span",{class:"text-muted"},"(recommended)",-1),M={class:"mb-2"},c="https://siouan.github.io/frontend-gradle-plugin/getting-started",g="Getting started: building a Javascript application with Gradle and Node.js",f="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",on=T({__name:"getting-started",setup(V){return L({link:[{rel:"canonical",href:c}]}),F({description:f,ogDescription:f,ogTitle:g,ogUrl:c,title:g}),(u,O)=>{const m=y,s=v,h=D,i=x,k=j,r=J,a=K,b=w,d=N,o=S,p=$,_=G;return I(),C("section",null,[e(m,null,{default:l(()=>[n("Getting started")]),_:1}),e(s,null,{default:l(()=>[n("Requirements")]),_:1}),B,t("ul",null,[t("li",null,[e(h),n(" 6.1+ ")])]),t("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),e(i,{path:"/blob/main/CONTRIBUTING.md"},{default:l(()=>[n("contributing notes")]),_:1}),n("). ")]),e(s,null,{default:l(()=>[n("Supported distributions")]),_:1}),t("ul",null,[t("li",null,[e(k),n(": ≥6.2.1")]),t("li",null,[e(r,{version:1}),n(": ≥1.0.0 ("),e(r,{"label-key":"navigation.yarnBerry.originalName"}),n(" initial support planned in release 6.0.0)")])]),e(s,null,{default:l(()=>[n("Steps")]),_:1}),e(b,null,{default:l(()=>[n(" Starting from release "),e(i,{path:"/releases/tag/v3.0.1"},{default:l(()=>[n("3.0.1")]),_:1}),n(", ID "),e(a,null,{default:l(()=>[n("org.siouan.frontend")]),_:1}),n(" and classpath "),e(a,null,{default:l(()=>[n("org.siouan:frontend-gradle-plugin:<version>")]),_:1}),n(" are deprecated. If you are already using the plugin, we recommend "),e(i,{path:"/releases/tag/v5.3.0"},{default:l(()=>[n("upgrading")]),_:1}),n(" to the latest release as soon as possible. ")]),_:1}),t("ol",null,[t("li",null,[n(" Install the plugin. "),t("ul",U,[t("li",null,[t("p",R,[n(" Using "),e(d,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:l(()=>[n("Gradle DSL")]),_:1}),n(),q]),e(p,{id:"install-gradle-dsl"},{groovy:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`plugins {
    `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '5.3.0'
    `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk18' version '5.3.0'
}`)]),_:1})])]),kotlin:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`plugins {
    `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "5.3.0"
    `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
    id("org.siouan.frontend-jdk8") version "5.3.0"
}`)]),_:1})])]),_:1})]),t("li",null,[t("p",M,[n(" Using "),e(d,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:l(()=>[n("Gradle build script block")]),_:1})]),e(p,{id:"install-build-script-block"},{groovy:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:5.3.0'
        `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:5.3.0'
    }
}

`),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk11'
`),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk8'`)]),_:1})])]),kotlin:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:5.3.0")
        `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk8:5.3.0")
    }
}

`),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk11")
`),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk8")`)]),_:1})])]),_:1})])])]),t("li",null,[e(_,{path:u.$config.public.paths.configuration},{default:l(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),e(i,{path:"/tree/main/examples"},{default:l(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),t("li",null,[n("Run "),e(a,null,{default:l(()=>[n("gradlew build")]),_:1}),n(".")]),t("li",null,[n(" If you need to run "),e(a,null,{default:l(()=>[n("node")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("npm")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("npx")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),e(_,{path:u.$config.public.paths.faqs},{default:l(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{on as default};
