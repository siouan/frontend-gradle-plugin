import{u as x,a as J,_ as K}from"./pxczNYIJ.js";import{_ as $}from"./B2PBjYl0.js";import{_ as G}from"./oPGknRiU.js";import{_ as S}from"./L44TgIuP.js";import{_ as w}from"./B6alVScc.js";import{_ as C,a as L}from"./c6tsABtt.js";import{_ as N}from"./DAH-GGfd.js";import{_ as T}from"./BOufzIrK.js";import{_ as I}from"./CU-mwjLd.js";import{_ as U}from"./BE7txKzx.js";import{f as B,c as R,b as t,w as o,a as s,d as l,u as q,o as M}from"./CPdTkOzn.js";import{u as V}from"./CtXgoLjO.js";import"./DlAUqK2U.js";import"./BWaDds42.js";const O={class:"list-unstyled my-2 me-3"},Y={class:"mb-2"},A={class:"mb-2"},b="Getting started: building a Javascript application with Gradle and Node.js",k="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",sn=B({__name:"getting-started",setup(E){const d=q(),e=`${d.public.canonicalBaseUrl}${d.public.paths.gettingStarted}`;return V({link:[{rel:"canonical",href:e}]}),x({description:k,ogDescription:k,ogTitle:b,ogUrl:e,title:b}),(g,n)=>{const F=J,p=$,j=G,r=S,y=K,D=w,u=C,v=N,f=T,i=I,m=U,a=L;return M(),R("section",null,[t(F,null,{default:o(()=>n[0]||(n[0]=[l("Getting started")])),_:1}),t(p,null,{default:o(()=>n[1]||(n[1]=[l("Requirements")])),_:1}),n[72]||(n[72]=s("p",null,"The following tools must be installed to use the plugin:",-1)),s("ul",null,[s("li",null,[t(j),n[2]||(n[2]=l(" 6.1+ "))])]),s("p",null,[n[4]||(n[4]=l(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the ")),t(r,{path:"/blob/main/CONTRIBUTING.md"},{default:o(()=>n[3]||(n[3]=[l("contributing notes")])),_:1}),n[5]||(n[5]=l("). "))]),t(p,null,{default:o(()=>n[6]||(n[6]=[l("Supported distributions")])),_:1}),s("ul",null,[s("li",null,[t(y),n[7]||(n[7]=l(": ≥6.2.1"))]),s("li",null,[t(D),n[8]||(n[8]=l(": ≥1.0.0"))])]),t(p,null,{default:o(()=>n[9]||(n[9]=[l("Steps")])),_:1}),t(v,null,{default:o(()=>[n[14]||(n[14]=l(" Starting from release ")),t(r,{path:"/releases/tag/v3.0.1"},{default:o(()=>n[10]||(n[10]=[l("3.0.1")])),_:1}),n[15]||(n[15]=l(", ID ")),t(u,null,{default:o(()=>n[11]||(n[11]=[l("org.siouan.frontend")])),_:1}),n[16]||(n[16]=l(" and classpath ")),t(u,null,{default:o(()=>n[12]||(n[12]=[l("org.siouan:frontend-gradle-plugin:<version>")])),_:1}),n[17]||(n[17]=l(" are deprecated. If you are already using the plugin, we recommend ")),t(r,{path:"/releases/tag/v6.0.0"},{default:o(()=>n[13]||(n[13]=[l("upgrading")])),_:1}),n[18]||(n[18]=l(" to the latest release as soon as possible. "))]),_:1}),s("ol",null,[s("li",null,[n[53]||(n[53]=l(" Install the plugin. ")),s("ul",O,[s("li",null,[s("p",Y,[n[20]||(n[20]=l(" Using ")),t(f,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:o(()=>n[19]||(n[19]=[l("Gradle DSL")])),_:1}),n[21]||(n[21]=l()),n[22]||(n[22]=s("span",{class:"text-muted"},"(recommended)",-1))]),t(m,{id:"install-gradle-dsl"},{groovy:o(()=>[s("pre",null,[t(u,null,{default:o(()=>[n[25]||(n[25]=l(`plugins {
    `)),t(i,null,{default:o(()=>n[23]||(n[23]=[l("// For JDK 11+")])),_:1}),n[26]||(n[26]=l(`
    id 'org.siouan.frontend-jdk11' version '6.0.0'
    `)),t(i,null,{default:o(()=>n[24]||(n[24]=[l("// For JDK 8+")])),_:1}),n[27]||(n[27]=l(`
    id 'org.siouan.frontend-jdk18' version '6.0.0'
}`))]),_:1})])]),kotlin:o(()=>[s("pre",null,[t(u,null,{default:o(()=>[n[30]||(n[30]=l(`plugins {
    `)),t(i,null,{default:o(()=>n[28]||(n[28]=[l("// For JDK 11+")])),_:1}),n[31]||(n[31]=l(`
    id("org.siouan.frontend-jdk11") version "6.0.0"
    `)),t(i,null,{default:o(()=>n[29]||(n[29]=[l("// For JDK 8+")])),_:1}),n[32]||(n[32]=l(`
    id("org.siouan.frontend-jdk8") version "6.0.0"
}`))]),_:1})])]),_:1})]),s("li",null,[s("p",A,[n[34]||(n[34]=l(" Using ")),t(f,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:o(()=>n[33]||(n[33]=[l("Gradle build script block")])),_:1})]),t(m,{id:"install-build-script-block"},{groovy:o(()=>[s("pre",null,[t(u,null,{default:o(()=>[n[39]||(n[39]=l(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `)),t(i,null,{default:o(()=>n[35]||(n[35]=[l("// For JDK 11+")])),_:1}),n[40]||(n[40]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:6.0.0'
        `)),t(i,null,{default:o(()=>n[36]||(n[36]=[l("// For JDK 8+")])),_:1}),n[41]||(n[41]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:6.0.0'
    }
}

`)),t(i,null,{default:o(()=>n[37]||(n[37]=[l("// For JDK 11+")])),_:1}),n[42]||(n[42]=l(`
apply plugin: 'org.siouan.frontend-jdk11'
`)),t(i,null,{default:o(()=>n[38]||(n[38]=[l("// For JDK 8+")])),_:1}),n[43]||(n[43]=l(`
apply plugin: 'org.siouan.frontend-jdk8'`))]),_:1})])]),kotlin:o(()=>[s("pre",null,[t(u,null,{default:o(()=>[n[48]||(n[48]=l(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `)),t(i,null,{default:o(()=>n[44]||(n[44]=[l("// For JDK 11+")])),_:1}),n[49]||(n[49]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:6.0.0")
        `)),t(i,null,{default:o(()=>n[45]||(n[45]=[l("// For JDK 8+")])),_:1}),n[50]||(n[50]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk8:6.0.0")
    }
}

`)),t(i,null,{default:o(()=>n[46]||(n[46]=[l("// For JDK 11+")])),_:1}),n[51]||(n[51]=l(`
apply(plugin = "org.siouan.frontend-jdk11")
`)),t(i,null,{default:o(()=>n[47]||(n[47]=[l("// For JDK 8+")])),_:1}),n[52]||(n[52]=l(`
apply(plugin = "org.siouan.frontend-jdk8")`))]),_:1})])]),_:1})])])]),s("li",null,[t(a,{path:g.$config.public.paths.configuration},{default:o(()=>n[54]||(n[54]=[l("Configure")])),_:1},8,["path"]),n[56]||(n[56]=l(" your project, optionally with the help of ")),t(r,{path:"/tree/main/examples"},{default:o(()=>n[55]||(n[55]=[l("examples")])),_:1}),n[57]||(n[57]=l(" provided for typical use cases. "))]),s("li",null,[n[59]||(n[59]=l("Run ")),t(u,null,{default:o(()=>n[58]||(n[58]=[l("gradlew build")])),_:1}),n[60]||(n[60]=l("."))]),s("li",null,[n[66]||(n[66]=l(" If you need to run ")),t(u,null,{default:o(()=>n[61]||(n[61]=[l("node")])),_:1}),n[67]||(n[67]=l("/")),t(u,null,{default:o(()=>n[62]||(n[62]=[l("npm")])),_:1}),n[68]||(n[68]=l("/")),t(u,null,{default:o(()=>n[63]||(n[63]=[l("npx")])),_:1}),n[69]||(n[69]=l("/")),t(u,null,{default:o(()=>n[64]||(n[64]=[l("yarn")])),_:1}),n[70]||(n[70]=l(" executables from a command line (e.g. to start a development server), take a look at the ")),t(a,{path:g.$config.public.paths.faqs},{default:o(()=>n[65]||(n[65]=[l("FAQ")])),_:1},8,["path"]),n[71]||(n[71]=l(". "))])])])}}});export{sn as default};
