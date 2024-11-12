import{u as x,a as J,_ as K}from"./C5OAmh4D.js";import{_ as $}from"./9a1LloSI.js";import{_ as G}from"./Drsc5h9S.js";import{_ as S}from"./Dik-u63B.js";import{_ as w}from"./BzBqq-Pf.js";import{_ as C,a as N}from"./DZHecFjF.js";import{_ as L}from"./BoGBuAYq.js";import{_ as T}from"./NZdZu9M6.js";import{_ as I}from"./zeoJfFwT.js";import{_ as B}from"./Fo91r-d8.js";import{f as U,c as R,b as t,w as o,a as i,d as l,u as q,o as M}from"./Bn0r76rF.js";import{u as V}from"./BQtiW08E.js";import"./DlAUqK2U.js";import"./A6maY33q.js";const O={class:"list-unstyled my-2 me-3"},Y={class:"mb-2"},A={class:"mb-2"},k="Getting started: building a Javascript application with Gradle and Node.js",F="Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.",sn=U({__name:"getting-started",setup(E){const d=q(),e=`${d.public.canonicalBaseUrl}${d.public.paths.gettingStarted}`;return V({link:[{rel:"canonical",href:e}]}),x({description:F,ogDescription:F,ogTitle:k,ogUrl:e,title:k}),(g,n)=>{const y=J,p=$,j=G,r=S,v=K,f=w,u=C,D=L,m=T,s=I,a=B,b=N;return M(),R("section",null,[t(y,null,{default:o(()=>n[0]||(n[0]=[l("Getting started")])),_:1}),t(p,null,{default:o(()=>n[1]||(n[1]=[l("Requirements")])),_:1}),n[73]||(n[73]=i("p",null,"The following tools must be installed to use the plugin:",-1)),i("ul",null,[i("li",null,[t(j),n[2]||(n[2]=l(" 6.1+ "))])]),i("p",null,[n[4]||(n[4]=l(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the ")),t(r,{path:"/blob/main/CONTRIBUTING.md"},{default:o(()=>n[3]||(n[3]=[l("contributing notes")])),_:1}),n[5]||(n[5]=l("). "))]),t(p,null,{default:o(()=>n[6]||(n[6]=[l("Supported distributions")])),_:1}),i("ul",null,[i("li",null,[t(v),n[7]||(n[7]=l(": ≥6.2.1"))]),i("li",null,[t(f,{version:1}),n[8]||(n[8]=l(": ≥1.0.0 (")),t(f,{"label-key":"navigation.yarnBerry.originalName"}),n[9]||(n[9]=l(" initial support planned in release 6.0.0)"))])]),t(p,null,{default:o(()=>n[10]||(n[10]=[l("Steps")])),_:1}),t(D,null,{default:o(()=>[n[15]||(n[15]=l(" Starting from release ")),t(r,{path:"/releases/tag/v3.0.1"},{default:o(()=>n[11]||(n[11]=[l("3.0.1")])),_:1}),n[16]||(n[16]=l(", ID ")),t(u,null,{default:o(()=>n[12]||(n[12]=[l("org.siouan.frontend")])),_:1}),n[17]||(n[17]=l(" and classpath ")),t(u,null,{default:o(()=>n[13]||(n[13]=[l("org.siouan:frontend-gradle-plugin:<version>")])),_:1}),n[18]||(n[18]=l(" are deprecated. If you are already using the plugin, we recommend ")),t(r,{path:"/releases/tag/v5.3.0"},{default:o(()=>n[14]||(n[14]=[l("upgrading")])),_:1}),n[19]||(n[19]=l(" to the latest release as soon as possible. "))]),_:1}),i("ol",null,[i("li",null,[n[54]||(n[54]=l(" Install the plugin. ")),i("ul",O,[i("li",null,[i("p",Y,[n[21]||(n[21]=l(" Using ")),t(m,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:o(()=>n[20]||(n[20]=[l("Gradle DSL")])),_:1}),n[22]||(n[22]=l()),n[23]||(n[23]=i("span",{class:"text-muted"},"(recommended)",-1))]),t(a,{id:"install-gradle-dsl"},{groovy:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[26]||(n[26]=l(`plugins {
    `)),t(s,null,{default:o(()=>n[24]||(n[24]=[l("// For JDK 11+")])),_:1}),n[27]||(n[27]=l(`
    id 'org.siouan.frontend-jdk11' version '5.3.0'
    `)),t(s,null,{default:o(()=>n[25]||(n[25]=[l("// For JDK 8+")])),_:1}),n[28]||(n[28]=l(`
    id 'org.siouan.frontend-jdk18' version '5.3.0'
}`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[31]||(n[31]=l(`plugins {
    `)),t(s,null,{default:o(()=>n[29]||(n[29]=[l("// For JDK 11+")])),_:1}),n[32]||(n[32]=l(`
    id("org.siouan.frontend-jdk11") version "5.3.0"
    `)),t(s,null,{default:o(()=>n[30]||(n[30]=[l("// For JDK 8+")])),_:1}),n[33]||(n[33]=l(`
    id("org.siouan.frontend-jdk8") version "5.3.0"
}`))]),_:1})])]),_:1})]),i("li",null,[i("p",A,[n[35]||(n[35]=l(" Using ")),t(m,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:o(()=>n[34]||(n[34]=[l("Gradle build script block")])),_:1})]),t(a,{id:"install-build-script-block"},{groovy:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[40]||(n[40]=l(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `)),t(s,null,{default:o(()=>n[36]||(n[36]=[l("// For JDK 11+")])),_:1}),n[41]||(n[41]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:5.3.0'
        `)),t(s,null,{default:o(()=>n[37]||(n[37]=[l("// For JDK 8+")])),_:1}),n[42]||(n[42]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:5.3.0'
    }
}

`)),t(s,null,{default:o(()=>n[38]||(n[38]=[l("// For JDK 11+")])),_:1}),n[43]||(n[43]=l(`
apply plugin: 'org.siouan.frontend-jdk11'
`)),t(s,null,{default:o(()=>n[39]||(n[39]=[l("// For JDK 8+")])),_:1}),n[44]||(n[44]=l(`
apply plugin: 'org.siouan.frontend-jdk8'`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[49]||(n[49]=l(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `)),t(s,null,{default:o(()=>n[45]||(n[45]=[l("// For JDK 11+")])),_:1}),n[50]||(n[50]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:5.3.0")
        `)),t(s,null,{default:o(()=>n[46]||(n[46]=[l("// For JDK 8+")])),_:1}),n[51]||(n[51]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk8:5.3.0")
    }
}

`)),t(s,null,{default:o(()=>n[47]||(n[47]=[l("// For JDK 11+")])),_:1}),n[52]||(n[52]=l(`
apply(plugin = "org.siouan.frontend-jdk11")
`)),t(s,null,{default:o(()=>n[48]||(n[48]=[l("// For JDK 8+")])),_:1}),n[53]||(n[53]=l(`
apply(plugin = "org.siouan.frontend-jdk8")`))]),_:1})])]),_:1})])])]),i("li",null,[t(b,{path:g.$config.public.paths.configuration},{default:o(()=>n[55]||(n[55]=[l("Configure")])),_:1},8,["path"]),n[57]||(n[57]=l(" your project, optionally with the help of ")),t(r,{path:"/tree/main/examples"},{default:o(()=>n[56]||(n[56]=[l("examples")])),_:1}),n[58]||(n[58]=l(" provided for typical use cases. "))]),i("li",null,[n[60]||(n[60]=l("Run ")),t(u,null,{default:o(()=>n[59]||(n[59]=[l("gradlew build")])),_:1}),n[61]||(n[61]=l("."))]),i("li",null,[n[67]||(n[67]=l(" If you need to run ")),t(u,null,{default:o(()=>n[62]||(n[62]=[l("node")])),_:1}),n[68]||(n[68]=l("/")),t(u,null,{default:o(()=>n[63]||(n[63]=[l("npm")])),_:1}),n[69]||(n[69]=l("/")),t(u,null,{default:o(()=>n[64]||(n[64]=[l("npx")])),_:1}),n[70]||(n[70]=l("/")),t(u,null,{default:o(()=>n[65]||(n[65]=[l("yarn")])),_:1}),n[71]||(n[71]=l(" executables from a command line (e.g. to start a development server), take a look at the ")),t(b,{path:g.$config.public.paths.faqs},{default:o(()=>n[66]||(n[66]=[l("FAQ")])),_:1},8,["path"]),n[72]||(n[72]=l(". "))])])])}}});export{sn as default};
