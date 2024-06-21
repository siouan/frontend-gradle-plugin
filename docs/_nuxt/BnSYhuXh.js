import{u as y,a as J,_ as K}from"./BkGJl2ZQ.js";import{_ as x}from"./CxKyWrla.js";import{_ as $}from"./brTDOBAl.js";import{_ as v}from"./D3M5zWk8.js";import{_ as L}from"./GCGlyXyH.js";import{_ as N}from"./BSZXaiZV.js";import{_ as G}from"./dvIwE-KW.js";import{_ as C}from"./DQimxJDu.js";import{_ as S}from"./BFqTcm4f.js";import{_ as w}from"./C-3WmzZG.js";import{_ as T,a as B}from"./zck6Z6fF.js";import{_ as M}from"./Bag9INXo.js";import{g as U,c as P,b as l,w as t,a as e,d as n,u as R,o as q}from"./0eoLPSdn.js";import{u as I}from"./abRkgXpJ.js";import"./DlAUqK2U.js";import"./D4IM-IFr.js";const V=e("p",null,"The following tools must be installed to use the plugin:",-1),A={class:"list-unstyled my-2 me-3"},O={class:"mb-2"},Y=e("span",{class:"text-muted"},"(recommended)",-1),E={class:"mb-2"},h="Building a Javascript application with the Gradle Node plugin",k="Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.",cn=U({__name:"getting-started",setup(H){const s=R(),r=`${s.public.canonicalBaseUrl}${s.public.paths.gettingStarted}`;return I({link:[{rel:"canonical",href:r}]}),y({description:k,ogDescription:k,ogTitle:h,ogUrl:r,title:h}),(p,Q)=>{const b=J,a=x,d=$,_=v,c=L,u=K,F=N,j=G,D=C,g=S,i=w,o=T,m=M,f=B;return q(),P("section",null,[l(b,null,{default:t(()=>[n("Getting started")]),_:1}),l(a,null,{default:t(()=>[n("Requirements")]),_:1}),V,e("ul",null,[e("li",null,[l(d,{path:"/plugin/org.siouan.frontend-jdk17"},{default:t(()=>[n("JDK 17 build")]),_:1}),n(": "),l(_),n(" 7.3+ ")]),e("li",null,[l(d,{path:"/plugin/org.siouan.frontend-jdk11"},{default:t(()=>[n("JDK 11 build")]),_:1}),n(": "),l(_),n(" 6.1+ ")])]),e("p",null,[n(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the "),l(c,{path:"/blob/main/CONTRIBUTING.md"},{default:t(()=>[n("contributing notes")]),_:1}),n("). ")]),l(a,null,{default:t(()=>[n("Supported distributions")]),_:1}),e("ul",null,[e("li",null,[l(u),n(": ^14.19.0, ≥16.9.0")]),e("li",null,[l(F),n(": any")]),e("li",null,[l(j),n(": ^5.0.0 (requires "),l(u),n(" 14), ≥6.0.0")]),e("li",null,[l(D),n(": ≥1.0.0")])]),l(a,null,{default:t(()=>[n("Steps")]),_:1}),e("ol",null,[e("li",null,[n(" Install the plugin. "),e("ul",A,[e("li",null,[e("p",O,[n(" Using "),l(g,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:t(()=>[n("Gradle DSL")]),_:1}),n(),Y]),l(m,{id:"install-gradle-dsl"},{groovy:t(()=>[e("pre",null,[l(o,null,{default:t(()=>[n(`plugins {
    `),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk17' version '7.0.0'
    `),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
    id 'org.siouan.frontend-jdk11' version '7.0.0'
}`)]),_:1})])]),kotlin:t(()=>[e("pre",null,[l(o,null,{default:t(()=>[n(`plugins {
    `),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
    id("org.siouan.frontend-jdk17") version "7.0.0"
    `),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
    id("org.siouan.frontend-jdk11") version "7.0.0"
}`)]),_:1})])]),_:1})]),e("li",null,[e("p",E,[n(" Using "),l(g,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:t(()=>[n("Gradle build script block")]),_:1})]),l(m,{id:"install-build-script-block"},{groovy:t(()=>[e("pre",null,[l(o,null,{default:t(()=>[n(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:7.0.0'
        `),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:7.0.0'
    }
}

`),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk17'
`),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
apply plugin: 'org.siouan.frontend-jdk11'`)]),_:1})])]),kotlin:t(()=>[e("pre",null,[l(o,null,{default:t(()=>[n(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk17:7.0.0")
        `),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:7.0.0")
    }
}

`),l(i,null,{default:t(()=>[n("// For JDK 17+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk17")
`),l(i,null,{default:t(()=>[n("// For JDK 11+")]),_:1}),n(`
apply(plugin = "org.siouan.frontend-jdk11")`)]),_:1})])]),_:1})])])]),e("li",null,[n(" Define the "),l(o,null,{default:t(()=>[l(u,{path:"/api/packages.html#packagemanager",label:"packageManager"})]),_:1}),n(" property in the "),l(o,null,{default:t(()=>[n("package.json")]),_:1}),n(" file. ")]),e("li",null,[n("Add "),l(o,null,{default:t(()=>[n(".frontend-gradle-plugin/")]),_:1}),n(" directory to "),l(o,null,{default:t(()=>[n(".gitignore")]),_:1}),n(" file(s).")]),e("li",null,[l(f,{path:p.$config.public.paths.configuration},{default:t(()=>[n("Configure")]),_:1},8,["path"]),n(" your project, optionally with the help of "),l(c,{path:"/tree/main/examples"},{default:t(()=>[n("examples")]),_:1}),n(" provided for typical use cases. ")]),e("li",null,[n("Run "),l(o,null,{default:t(()=>[n("gradlew build")]),_:1}),n(".")]),e("li",null,[n(" If you need to run "),l(o,null,{default:t(()=>[n("node")]),_:1}),n("/"),l(o,null,{default:t(()=>[n("corepack")]),_:1}),n("/"),l(o,null,{default:t(()=>[n("npm")]),_:1}),n("/"),l(o,null,{default:t(()=>[n("pnpm")]),_:1}),n("/"),l(o,null,{default:t(()=>[n("yarn")]),_:1}),n(" executables from a command line (e.g. to start a development server), take a look at the "),l(f,{path:p.$config.public.paths.faqs},{default:t(()=>[n("FAQ")]),_:1},8,["path"]),n(". ")])])])}}});export{cn as default};
