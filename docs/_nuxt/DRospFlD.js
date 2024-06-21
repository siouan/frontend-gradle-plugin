import{u as j,a as y,_ as D}from"./BkGJl2ZQ.js";import{_ as v}from"./CxKyWrla.js";import{_ as x}from"./D3M5zWk8.js";import{_ as J}from"./GCGlyXyH.js";import{_ as K}from"./DQimxJDu.js";import{_ as $,a as G}from"./zck6Z6fF.js";import{_ as S}from"./CtBl-4bT.js";import{_ as w}from"./BFqTcm4f.js";import{_ as C}from"./C-3WmzZG.js";import{_ as L}from"./Bag9INXo.js";import{g as N,c as T,b as l,w as e,a as t,d as n,u as I,o as U}from"./0eoLPSdn.js";import{u as B}from"./abRkgXpJ.js";import"./DlAUqK2U.js";import"./D4IM-IFr.js";const R=t("p",null,"The following tools must be installed to use the plugin:",-1),q={class:"list-unstyled my-2 me-3"},M={class:"mb-2"},V=t("span",{class:"text-muted"},"(recommended)",-1),O={class:"mb-2"},g="Getting started: building a Javascript application with Gradle and Node.js",f="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",sn=N({__name:"getting-started",setup(Y){const u=I(),r=`${u.public.canonicalBaseUrl}${u.public.paths.gettingStarted}`;return B({link:[{rel:"canonical",href:r}]}),j({description:f,ogDescription:f,ogTitle:g,ogUrl:r,title:g}),(d,A)=>{const m=y,i=v,h=x,s=J,b=D,k=K,a=$,F=S,p=w,o=C,_=L,c=G;return U(),T("section",null,[l(m,null,{default:e(()=>[n("Getting started")]),_:1}),l(i,null,{default:e(()=>[n("Requirements")]),_:1}),R,t("ul",null,[t("li",null,[l(h),n(" 6.1+ ")])]),t("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),l(s,{path:"/blob/main/CONTRIBUTING.md"},{default:e(()=>[n("contributing notes")]),_:1}),n("). ")]),l(i,null,{default:e(()=>[n("Supported distributions")]),_:1}),t("ul",null,[t("li",null,[l(b),n(": ≥6.2.1")]),t("li",null,[l(k),n(": ≥1.0.0")])]),l(i,null,{default:e(()=>[n("Steps")]),_:1}),l(F,null,{default:e(()=>[n(" Starting from release "),l(s,{path:"/releases/tag/v3.0.1"},{default:e(()=>[n("3.0.1")]),_:1}),n(", ID "),l(a,null,{default:e(()=>[n("org.siouan.frontend")]),_:1}),n(" and classpath "),l(a,null,{default:e(()=>[n("org.siouan:frontend-gradle-plugin:<version>")]),_:1}),n(" are deprecated. If you are already using the plugin, we recommend "),l(s,{path:"/releases/tag/v6.0.0"},{default:e(()=>[n("upgrading")]),_:1}),n(" to the latest release as soon as possible. ")]),_:1}),t("ol",null,[t("li",null,[n(" Install the plugin. "),t("ul",q,[t("li",null,[t("p",M,[n(" Using "),l(p,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:e(()=>[n("Gradle DSL")]),_:1}),n(),V]),l(_,{id:"install-gradle-dsl"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '6.0.0'
    `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk18' version '6.0.0'
}`)]),_:1})])]),kotlin:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "6.0.0"
    `),l(o,null,{default:e(()=>[n("// For JDK 8+")]),_:1}),n(`
    id("org.siouan.frontend-jdk8") version "6.0.0"
}`)]),_:1})])]),_:1})]),t("li",null,[t("p",O,[n(" Using "),l(p,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:e(()=>[n("Gradle build script block")]),_:1})]),l(_,{id:"install-build-script-block"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`buildscript {
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
apply(plugin = "org.siouan.frontend-jdk8")`)]),_:1})])]),_:1})])])]),t("li",null,[l(c,{path:d.$config.public.paths.configuration},{default:e(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),l(s,{path:"/tree/main/examples"},{default:e(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),t("li",null,[n("Run "),l(a,null,{default:e(()=>[n("gradlew build")]),_:1}),n(".")]),t("li",null,[n(" If you need to run "),l(a,null,{default:e(()=>[n("node")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("npm")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("npx")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),l(c,{path:d.$config.public.paths.faqs},{default:e(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{sn as default};
