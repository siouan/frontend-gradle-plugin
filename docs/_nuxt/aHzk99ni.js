import{u as J,a as K,_ as y}from"./BkGJl2ZQ.js";import{_ as v}from"./CxKyWrla.js";import{_ as x}from"./brTDOBAl.js";import{_ as $}from"./D3M5zWk8.js";import{_ as L}from"./GCGlyXyH.js";import{_ as N}from"./BSZXaiZV.js";import{_ as G}from"./dvIwE-KW.js";import{_ as C}from"./DQimxJDu.js";import{_ as S}from"./BFqTcm4f.js";import{_ as w}from"./C-3WmzZG.js";import{_ as T,a as B}from"./zck6Z6fF.js";import{_ as M}from"./Bag9INXo.js";import{g as U,c as P,b as l,w as e,a as t,d as n,u as R,o as q}from"./0eoLPSdn.js";import{u as I}from"./abRkgXpJ.js";import"./DlAUqK2U.js";import"./D4IM-IFr.js";const V=t("p",null,"The following tools must be installed to use the plugin:",-1),A={class:"list-unstyled my-2 me-3"},O={class:"mb-2"},Y=t("span",{class:"text-muted"},"(recommended)",-1),E={class:"mb-2"},h="Building a Javascript application with the Gradle Node plugin",k="Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.",cn=U({__name:"getting-started",setup(H){const d=R(),p=`${d.public.canonicalBaseUrl}${d.public.paths.gettingStarted}`;return I({link:[{rel:"canonical",href:p}]}),J({description:k,ogDescription:k,ogTitle:h,ogUrl:p,title:h}),(_,Q)=>{const F=K,i=v,u=x,s=$,c=L,r=y,b=N,j=G,D=C,g=S,o=w,a=T,f=M,m=B;return q(),P("section",null,[l(F,null,{default:e(()=>[n("Getting started")]),_:1}),l(i,null,{default:e(()=>[n("Requirements")]),_:1}),V,t("ul",null,[t("li",null,[l(u,{path:"/plugin/org.siouan.frontend-jdk21"},{default:e(()=>[n("JDK 21 build")]),_:1}),n(": "),l(s),n(" 8.5+ ")]),t("li",null,[l(u,{path:"/plugin/org.siouan.frontend-jdk17"},{default:e(()=>[n("JDK 17 build")]),_:1}),n(": "),l(s),n(" 8.5+ ")]),t("li",null,[l(u,{path:"/plugin/org.siouan.frontend-jdk11"},{default:e(()=>[n("JDK 11 build")]),_:1}),n(": "),l(s),n(" 8.5+ ")])]),t("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),l(c,{path:"/blob/main/CONTRIBUTING.md"},{default:e(()=>[n("contributing notes")]),_:1}),n("). ")]),l(i,null,{default:e(()=>[n("Supported distributions")]),_:1}),t("ul",null,[t("li",null,[l(r),n(": ^14.19.0, ≥16.9.0")]),t("li",null,[l(b),n(": any")]),t("li",null,[l(j),n(": ^5.0.0 (requires "),l(r),n(" 14), ≥6.0.0")]),t("li",null,[l(D),n(": ≥1.0.0")])]),l(i,null,{default:e(()=>[n("Steps")]),_:1}),t("ol",null,[t("li",null,[n(" Install the plugin. "),t("ul",A,[t("li",null,[t("p",O,[n(" Using "),l(g,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:e(()=>[n("Gradle DSL")]),_:1}),n(),Y]),l(f,{id:"install-gradle-dsl"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk21' version '8.1.0'
    `),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk17' version '8.1.0'
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '8.1.0'
}`)]),_:1})])]),kotlin:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`plugins {
    `),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
    id("org.siouan.frontend-jdk21") version "8.1.0"
    `),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
    id("org.siouan.frontend-jdk17") version "8.1.0"
    `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "8.1.0"
}`)]),_:1})])]),_:1})]),t("li",null,[t("p",E,[n(" Using "),l(g,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:e(()=>[n("Gradle build script block")]),_:1})]),l(f,{id:"install-build-script-block"},{groovy:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk21:8.1.0'
        `),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:8.1.0'
        `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:8.1.0'
    }
}

`),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk21'
`),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk17'
`),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk11'`)]),_:1})])]),kotlin:e(()=>[t("pre",null,[l(a,null,{default:e(()=>[n(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk21:8.1.0")
        `),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk17:8.1.0")
        `),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:8.1.0")
    }
}

`),l(o,null,{default:e(()=>[n("// For JDK 21+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk21")
`),l(o,null,{default:e(()=>[n("// For JDK 17+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk17")
`),l(o,null,{default:e(()=>[n("// For JDK 11+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk11")`)]),_:1})])]),_:1})])])]),t("li",null,[n(" Define the "),l(a,null,{default:e(()=>[l(r,{path:"/api/packages.html#packagemanager",label:"packageManager"})]),_:1}),n(" property in the "),l(a,null,{default:e(()=>[n("package.json")]),_:1}),n(" file. ")]),t("li",null,[n("Add "),l(a,null,{default:e(()=>[n(".frontend-gradle-plugin/")]),_:1}),n(" directory to "),l(a,null,{default:e(()=>[n(".gitignore")]),_:1}),n(" file(s).")]),t("li",null,[l(m,{path:_.$config.public.paths.configuration},{default:e(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),l(c,{path:"/tree/main/examples"},{default:e(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),t("li",null,[n("Run "),l(a,null,{default:e(()=>[n("gradlew build")]),_:1}),n(".")]),t("li",null,[n(" If you need to run "),l(a,null,{default:e(()=>[n("node")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("corepack")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("npm")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("pnpm")]),_:1}),n("/"),l(a,null,{default:e(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),l(m,{path:_.$config.public.paths.faqs},{default:e(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{cn as default};
