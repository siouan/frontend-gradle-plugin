import{u as F,a as j,_ as y}from"./D2a5xX6z.js";import{_ as D}from"./BB8qlrFw.js";import{_ as v}from"./Djv68nLm.js";import{_ as x}from"./Ba89GBn_.js";import{_ as J}from"./td49Uotm.js";import{_ as K,a as G}from"./CB0qIKt7.js";import{_ as w}from"./CK4tp4Ei.js";import{_ as S}from"./Dq5EYBAl.js";import{_ as $}from"./DQQmHE7p.js";import{_ as L}from"./D1_FnBaM.js";import{u as N}from"./DfGbskvx.js";import{g as T,c as C,b as l,w as e,a as t,d as n,o as I}from"./F1WuTW_A.js";import"./DlAUqK2U.js";import"./BtiXSH7J.js";const U=t("p",null,"The following tools must be installed to use the plugin:",-1),B={class:"list-unstyled my-2 me-3"},R={class:"mb-2"},q=t("span",{class:"text-muted"},"(recommended)",-1),M={class:"mb-2"},_="https://siouan.github.io/frontend-gradle-plugin/getting-started",c="Getting started: building a Javascript application with Gradle and Node.js",g="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",on=T({__name:"getting-started",setup(V){return N({link:[{rel:"canonical",href:_}]}),F({description:g,ogDescription:g,ogTitle:c,ogUrl:_,title:c}),(u,O)=>{const f=j,i=D,m=v,s=x,h=y,k=J,a=K,b=w,r=S,o=$,d=L,p=G;return I(),C("section",null,[l(f,null,{default:e(()=>[n("Getting started")]),_:1}),l(i,null,{default:e(()=>[n("Requirements")]),_:1}),U,t("ul",null,[t("li",null,[l(m),n(" 6.1+ ")])]),t("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),l(s,{path:"/blob/main/CONTRIBUTING.md"},{default:e(()=>[n("contributing notes")]),_:1}),n("). ")]),l(i,null,{default:e(()=>[n("Supported distributions")]),_:1}),t("ul",null,[t("li",null,[l(h),n(": ≥6.2.1")]),t("li",null,[l(k),n(": ≥1.0.0")])]),l(i,null,{default:e(()=>[n("Steps")]),_:1}),l(b,null,{default:e(()=>[n(" Starting from release "),l(s,{path:"/releases/tag/v3.0.1"},{default:e(()=>[n("3.0.1")]),_:1}),n(", ID "),l(a,null,{default:e(()=>[n("org.siouan.frontend")]),_:1}),n(" and classpath "),l(a,null,{default:e(()=>[n("org.siouan:frontend-gradle-plugin:<version>")]),_:1}),n(" are deprecated. If you are already using the plugin, we recommend "),l(s,{path:"/releases/tag/v6.0.0"},{default:e(()=>[n("upgrading")]),_:1}),n(" to the latest release as soon as possible. ")]),_:1}),t("ol",null,[t("li",null,[n(" Install the plugin. "),t("ul",B,[t("li",null,[t("p",R,[n(" Using "),l(r,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:e(()=>[n("Gradle DSL")]),_:1}),n(),q]),l(d,{id:"install-gradle-dsl"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '6.0.0'
    `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk18' version '6.0.0'
}`)]),_:1})])]),kotlin:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "6.0.0"
    `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
    id("org.siouan.frontend-jdk8") version "6.0.0"
}`)]),_:1})])]),_:1})]),t("li",null,[t("p",M,[n(" Using "),l(r,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:e(()=>[n("Gradle build script block")]),_:1})]),l(d,{id:"install-build-script-block"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:6.0.0'
        `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:6.0.0'
    }
}

`),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk11'
`),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk8'`)]),_:1})])]),kotlin:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:6.0.0")
        `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk8:6.0.0")
    }
}

`),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk11")
`),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk8")`)]),_:1})])]),_:1})])])]),t("li",null,[l(p,{path:u.$config.public.paths.configuration},{default:e(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),l(s,{path:"/tree/main/examples"},{default:e(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),t("li",null,[n("Run "),l(a,null,{default:e(()=>[n("gradlew build")]),_:1}),n(".")]),t("li",null,[n(" If you need to run "),l(a,null,{default:e(()=>[n("node")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("npm")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("npx")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),l(p,{path:u.$config.public.paths.faqs},{default:e(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{on as default};
