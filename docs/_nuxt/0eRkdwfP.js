import{u as y,a as j,_ as v}from"./BkGJl2ZQ.js";import{_ as D}from"./CxKyWrla.js";import{_ as x}from"./D3M5zWk8.js";import{_ as J}from"./GCGlyXyH.js";import{_ as K}from"./DQimxJDu.js";import{_ as $,a as G}from"./zck6Z6fF.js";import{_ as S}from"./CtBl-4bT.js";import{_ as w}from"./BFqTcm4f.js";import{_ as C}from"./C-3WmzZG.js";import{_ as N}from"./Bag9INXo.js";import{g as L,c as T,b as e,w as l,a as t,d as n,u as I,o as B}from"./0eoLPSdn.js";import{u as U}from"./abRkgXpJ.js";import"./DlAUqK2U.js";import"./D4IM-IFr.js";const R=t("p",null,"The following tools must be installed to use the plugin:",-1),q={class:"list-unstyled my-2 me-3"},M={class:"mb-2"},V=t("span",{class:"text-muted"},"(recommended)",-1),O={class:"mb-2"},f="Getting started: building a Javascript application with Gradle and Node.js",m="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",sn=L({__name:"getting-started",setup(Y){const u=I(),r=`${u.public.canonicalBaseUrl}${u.public.paths.gettingStarted}`;return U({link:[{rel:"canonical",href:r}]}),y({description:m,ogDescription:m,ogTitle:f,ogUrl:r,title:f}),(p,A)=>{const h=j,s=D,b=x,i=J,k=v,d=K,a=$,F=S,_=w,o=C,c=N,g=G;return B(),T("section",null,[e(h,null,{default:l(()=>[n("Getting started")]),_:1}),e(s,null,{default:l(()=>[n("Requirements")]),_:1}),R,t("ul",null,[t("li",null,[e(b),n(" 6.1+ ")])]),t("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),e(i,{path:"/blob/main/CONTRIBUTING.md"},{default:l(()=>[n("contributing notes")]),_:1}),n("). ")]),e(s,null,{default:l(()=>[n("Supported distributions")]),_:1}),t("ul",null,[t("li",null,[e(k),n(": ≥6.2.1")]),t("li",null,[e(d,{version:1}),n(": ≥1.0.0 ("),e(d,{"label-key":"navigation.yarnBerry.originalName"}),n(" initial support planned in release 6.0.0)")])]),e(s,null,{default:l(()=>[n("Steps")]),_:1}),e(F,null,{default:l(()=>[n(" Starting from release "),e(i,{path:"/releases/tag/v3.0.1"},{default:l(()=>[n("3.0.1")]),_:1}),n(", ID "),e(a,null,{default:l(()=>[n("org.siouan.frontend")]),_:1}),n(" and classpath "),e(a,null,{default:l(()=>[n("org.siouan:frontend-gradle-plugin:<version>")]),_:1}),n(" are deprecated. If you are already using the plugin, we recommend "),e(i,{path:"/releases/tag/v5.3.0"},{default:l(()=>[n("upgrading")]),_:1}),n(" to the latest release as soon as possible. ")]),_:1}),t("ol",null,[t("li",null,[n(" Install the plugin. "),t("ul",q,[t("li",null,[t("p",M,[n(" Using "),e(_,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:l(()=>[n("Gradle DSL")]),_:1}),n(),V]),e(c,{id:"install-gradle-dsl"},{groovy:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`plugins {
    `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '5.3.0'
    `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk18' version '5.3.0'
}`)]),_:1})])]),kotlin:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`plugins {
    `),e(o,null,{default:l(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "5.3.0"
    `),e(o,null,{default:l(()=>[n("// For JDK 8+")]),_:1}),n(`
    id("org.siouan.frontend-jdk8") version "5.3.0"
}`)]),_:1})])]),_:1})]),t("li",null,[t("p",O,[n(" Using "),e(_,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:l(()=>[n("Gradle build script block")]),_:1})]),e(c,{id:"install-build-script-block"},{groovy:l(()=>[t("pre",null,[e(a,null,{default:l(()=>[n(`buildscript {
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
apply(plugin = "org.siouan.frontend-jdk8")`)]),_:1})])]),_:1})])])]),t("li",null,[e(g,{path:p.$config.public.paths.configuration},{default:l(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),e(i,{path:"/tree/main/examples"},{default:l(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),t("li",null,[n("Run "),e(a,null,{default:l(()=>[n("gradlew build")]),_:1}),n(".")]),t("li",null,[n(" If you need to run "),e(a,null,{default:l(()=>[n("node")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("npm")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("npx")]),_:1}),n("/"),e(a,null,{default:l(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),e(g,{path:p.$config.public.paths.faqs},{default:l(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{sn as default};
