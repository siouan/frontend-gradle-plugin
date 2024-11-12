import{u as x,a as $,_ as L}from"./C5OAmh4D.js";import{_ as N}from"./9a1LloSI.js";import{_ as G}from"./Ch3yw5C8.js";import{_ as C}from"./Drsc5h9S.js";import{_ as S}from"./Dik-u63B.js";import{_ as w}from"./CmFZJ-jt.js";import{_ as T}from"./B5cRyKvj.js";import{_ as B}from"./BzBqq-Pf.js";import{_ as M}from"./NZdZu9M6.js";import{_ as U}from"./zeoJfFwT.js";import{_ as P,a as R}from"./DZHecFjF.js";import{_ as q}from"./Fo91r-d8.js";import{f as I,c as V,b as t,w as o,a as i,d as l,u as A,o as O}from"./Bn0r76rF.js";import{u as Y}from"./BQtiW08E.js";import"./DlAUqK2U.js";import"./A6maY33q.js";const E={class:"list-unstyled my-2 me-3"},H={class:"mb-2"},Q={class:"mb-2"},j="Building a Javascript application with the Gradle Node plugin",D="Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.",fn=I({__name:"getting-started",setup(W){const f=A(),m=`${f.public.canonicalBaseUrl}${f.public.paths.gettingStarted}`;return Y({link:[{rel:"canonical",href:m}]}),x({description:D,ogDescription:D,ogTitle:j,ogUrl:m,title:j}),(e,n)=>{const J=$,s=N,p=G,d=C,k=S,g=L,K=w,y=T,v=B,a=M,u=U,r=P,F=q,b=R;return O(),V("section",null,[t(J,null,{default:o(()=>n[0]||(n[0]=[l("Getting started")])),_:1}),t(s,null,{default:o(()=>n[1]||(n[1]=[l("Requirements")])),_:1}),n[97]||(n[97]=i("p",null,"The following tools must be installed to use the plugin:",-1)),i("ul",null,[i("li",null,[t(p,{path:"/plugin/org.siouan.frontend-jdk21"},{default:o(()=>n[2]||(n[2]=[l("JDK 21 build")])),_:1}),n[3]||(n[3]=l(": ")),t(d),n[4]||(n[4]=l(" 8.5+ "))]),i("li",null,[t(p,{path:"/plugin/org.siouan.frontend-jdk17"},{default:o(()=>n[5]||(n[5]=[l("JDK 17 build")])),_:1}),n[6]||(n[6]=l(": ")),t(d),n[7]||(n[7]=l(" 8.5+ "))]),i("li",null,[t(p,{path:"/plugin/org.siouan.frontend-jdk11"},{default:o(()=>n[8]||(n[8]=[l("JDK 11 build")])),_:1}),n[9]||(n[9]=l(": ")),t(d),n[10]||(n[10]=l(" 8.5+ "))])]),i("p",null,[n[12]||(n[12]=l(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the ")),t(k,{path:"/blob/main/CONTRIBUTING.md"},{default:o(()=>n[11]||(n[11]=[l("contributing notes")])),_:1}),n[13]||(n[13]=l("). "))]),t(s,null,{default:o(()=>n[14]||(n[14]=[l("Supported distributions")])),_:1}),i("ul",null,[i("li",null,[t(g),n[15]||(n[15]=l(": ^14.19.0, ≥16.9.0"))]),i("li",null,[t(K),n[16]||(n[16]=l(": any"))]),i("li",null,[t(y),n[17]||(n[17]=l(": ^5.0.0 (requires ")),t(g),n[18]||(n[18]=l(" 14), ≥6.0.0"))]),i("li",null,[t(v),n[19]||(n[19]=l(": ≥1.0.0"))])]),t(s,null,{default:o(()=>n[20]||(n[20]=[l("Steps")])),_:1}),i("ol",null,[i("li",null,[n[67]||(n[67]=l(" Install the plugin. ")),i("ul",E,[i("li",null,[i("p",H,[n[22]||(n[22]=l(" Using ")),t(a,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:o(()=>n[21]||(n[21]=[l("Gradle DSL")])),_:1}),n[23]||(n[23]=l()),n[24]||(n[24]=i("span",{class:"text-muted"},"(recommended)",-1))]),t(F,{id:"install-gradle-dsl"},{groovy:o(()=>[i("pre",null,[t(r,null,{default:o(()=>[n[28]||(n[28]=l(`plugins {
    `)),t(u,null,{default:o(()=>n[25]||(n[25]=[l("// For JDK 21+")])),_:1}),n[29]||(n[29]=l(`
    id 'org.siouan.frontend-jdk21' version '8.1.0'
    `)),t(u,null,{default:o(()=>n[26]||(n[26]=[l("// For JDK 17+")])),_:1}),n[30]||(n[30]=l(`
    id 'org.siouan.frontend-jdk17' version '8.1.0'
    `)),t(u,null,{default:o(()=>n[27]||(n[27]=[l("// For JDK 11+")])),_:1}),n[31]||(n[31]=l(`
    id 'org.siouan.frontend-jdk11' version '8.1.0'
}`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(r,null,{default:o(()=>[n[35]||(n[35]=l(`plugins {
    `)),t(u,null,{default:o(()=>n[32]||(n[32]=[l("// For JDK 21+")])),_:1}),n[36]||(n[36]=l(`
    id("org.siouan.frontend-jdk21") version "8.1.0"
    `)),t(u,null,{default:o(()=>n[33]||(n[33]=[l("// For JDK 17+")])),_:1}),n[37]||(n[37]=l(`
    id("org.siouan.frontend-jdk17") version "8.1.0"
    `)),t(u,null,{default:o(()=>n[34]||(n[34]=[l("// For JDK 11+")])),_:1}),n[38]||(n[38]=l(`
    id("org.siouan.frontend-jdk11") version "8.1.0"
}`))]),_:1})])]),_:1})]),i("li",null,[i("p",Q,[n[40]||(n[40]=l(" Using ")),t(a,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:o(()=>n[39]||(n[39]=[l("Gradle build script block")])),_:1})]),t(F,{id:"install-build-script-block"},{groovy:o(()=>[i("pre",null,[t(r,null,{default:o(()=>[n[47]||(n[47]=l(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `)),t(u,null,{default:o(()=>n[41]||(n[41]=[l("// For JDK 21+")])),_:1}),n[48]||(n[48]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk21:8.1.0'
        `)),t(u,null,{default:o(()=>n[42]||(n[42]=[l("// For JDK 17+")])),_:1}),n[49]||(n[49]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:8.1.0'
        `)),t(u,null,{default:o(()=>n[43]||(n[43]=[l("// For JDK 11+")])),_:1}),n[50]||(n[50]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:8.1.0'
    }
}

`)),t(u,null,{default:o(()=>n[44]||(n[44]=[l("// For JDK 21+")])),_:1}),n[51]||(n[51]=l(`
apply plugin: 'org.siouan.frontend-jdk21'
`)),t(u,null,{default:o(()=>n[45]||(n[45]=[l("// For JDK 17+")])),_:1}),n[52]||(n[52]=l(`
apply plugin: 'org.siouan.frontend-jdk17'
`)),t(u,null,{default:o(()=>n[46]||(n[46]=[l("// For JDK 11+")])),_:1}),n[53]||(n[53]=l(`
apply plugin: 'org.siouan.frontend-jdk11'`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(r,null,{default:o(()=>[n[60]||(n[60]=l(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `)),t(u,null,{default:o(()=>n[54]||(n[54]=[l("// For JDK 21+")])),_:1}),n[61]||(n[61]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk21:8.1.0")
        `)),t(u,null,{default:o(()=>n[55]||(n[55]=[l("// For JDK 17+")])),_:1}),n[62]||(n[62]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk17:8.1.0")
        `)),t(u,null,{default:o(()=>n[56]||(n[56]=[l("// For JDK 11+")])),_:1}),n[63]||(n[63]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:8.1.0")
    }
}

`)),t(u,null,{default:o(()=>n[57]||(n[57]=[l("// For JDK 21+")])),_:1}),n[64]||(n[64]=l(`
apply(plugin = "org.siouan.frontend-jdk21")
`)),t(u,null,{default:o(()=>n[58]||(n[58]=[l("// For JDK 17+")])),_:1}),n[65]||(n[65]=l(`
apply(plugin = "org.siouan.frontend-jdk17")
`)),t(u,null,{default:o(()=>n[59]||(n[59]=[l("// For JDK 11+")])),_:1}),n[66]||(n[66]=l(`
apply(plugin = "org.siouan.frontend-jdk11")`))]),_:1})])]),_:1})])])]),i("li",null,[n[69]||(n[69]=l(" Define the ")),t(r,null,{default:o(()=>[t(g,{path:"/api/packages.html#packagemanager",label:"packageManager"})]),_:1}),n[70]||(n[70]=l(" property in the ")),t(r,null,{default:o(()=>n[68]||(n[68]=[l("package.json")])),_:1}),n[71]||(n[71]=l(" file. "))]),i("li",null,[n[74]||(n[74]=l("Add ")),t(r,null,{default:o(()=>n[72]||(n[72]=[l(".frontend-gradle-plugin/")])),_:1}),n[75]||(n[75]=l(" directory to ")),t(r,null,{default:o(()=>n[73]||(n[73]=[l(".gitignore")])),_:1}),n[76]||(n[76]=l(" file(s)."))]),i("li",null,[t(b,{path:e.$config.public.paths.configuration},{default:o(()=>n[77]||(n[77]=[l("Configure")])),_:1},8,["path"]),n[79]||(n[79]=l(" your project, optionally with the help of ")),t(k,{path:"/tree/main/examples"},{default:o(()=>n[78]||(n[78]=[l("examples")])),_:1}),n[80]||(n[80]=l(" provided for typical use cases. "))]),i("li",null,[n[82]||(n[82]=l("Run ")),t(r,null,{default:o(()=>n[81]||(n[81]=[l("gradlew build")])),_:1}),n[83]||(n[83]=l("."))]),i("li",null,[n[90]||(n[90]=l(" If you need to run ")),t(r,null,{default:o(()=>n[84]||(n[84]=[l("node")])),_:1}),n[91]||(n[91]=l("/")),t(r,null,{default:o(()=>n[85]||(n[85]=[l("corepack")])),_:1}),n[92]||(n[92]=l("/")),t(r,null,{default:o(()=>n[86]||(n[86]=[l("npm")])),_:1}),n[93]||(n[93]=l("/")),t(r,null,{default:o(()=>n[87]||(n[87]=[l("pnpm")])),_:1}),n[94]||(n[94]=l("/")),t(r,null,{default:o(()=>n[88]||(n[88]=[l("yarn")])),_:1}),n[95]||(n[95]=l(" executables from a command line (e.g. to start a development server), take a look at the ")),t(b,{path:e.$config.public.paths.faqs},{default:o(()=>n[89]||(n[89]=[l("FAQ")])),_:1},8,["path"]),n[96]||(n[96]=l(". "))])])])}}});export{fn as default};
