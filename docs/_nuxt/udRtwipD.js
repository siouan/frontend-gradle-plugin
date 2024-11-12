import{u as $,a as v,_ as L}from"./pxczNYIJ.js";import{_ as N}from"./B2PBjYl0.js";import{_ as G}from"./UFfKkgvu.js";import{_ as C}from"./oPGknRiU.js";import{_ as S}from"./L44TgIuP.js";import{_ as w}from"./D9YCSiNI.js";import{_ as T}from"./D1UsOuFE.js";import{_ as B}from"./B6alVScc.js";import{_ as M}from"./BOufzIrK.js";import{_ as U}from"./CU-mwjLd.js";import{_ as P,a as R}from"./c6tsABtt.js";import{_ as q}from"./BE7txKzx.js";import{f as I,c as V,b as t,w as o,a as i,d as l,u as A,o as O}from"./CPdTkOzn.js";import{u as Y}from"./CtXgoLjO.js";import"./DlAUqK2U.js";import"./BWaDds42.js";const E={class:"list-unstyled my-2 me-3"},H={class:"mb-2"},Q={class:"mb-2"},j="Building a Javascript application with the Gradle Node plugin",D="Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.",gn=I({__name:"getting-started",setup(W){const d=A(),e=`${d.public.canonicalBaseUrl}${d.public.paths.gettingStarted}`;return Y({link:[{rel:"canonical",href:e}]}),$({description:D,ogDescription:D,ogTitle:j,ogUrl:e,title:j}),(g,n)=>{const y=v,r=N,f=G,m=C,a=S,p=L,J=w,K=T,x=B,k=M,s=U,u=P,b=q,F=R;return O(),V("section",null,[t(y,null,{default:o(()=>n[0]||(n[0]=[l("Getting started")])),_:1}),t(r,null,{default:o(()=>n[1]||(n[1]=[l("Requirements")])),_:1}),n[82]||(n[82]=i("p",null,"The following tools must be installed to use the plugin:",-1)),i("ul",null,[i("li",null,[t(f,{path:"/plugin/org.siouan.frontend-jdk17"},{default:o(()=>n[2]||(n[2]=[l("JDK 17 build")])),_:1}),n[3]||(n[3]=l(": ")),t(m),n[4]||(n[4]=l(" 7.3+ "))]),i("li",null,[t(f,{path:"/plugin/org.siouan.frontend-jdk11"},{default:o(()=>n[5]||(n[5]=[l("JDK 11 build")])),_:1}),n[6]||(n[6]=l(": ")),t(m),n[7]||(n[7]=l(" 6.1+ "))])]),i("p",null,[n[9]||(n[9]=l(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the ")),t(a,{path:"/blob/main/CONTRIBUTING.md"},{default:o(()=>n[8]||(n[8]=[l("contributing notes")])),_:1}),n[10]||(n[10]=l("). "))]),t(r,null,{default:o(()=>n[11]||(n[11]=[l("Supported distributions")])),_:1}),i("ul",null,[i("li",null,[t(p),n[12]||(n[12]=l(": ^14.19.0, ≥16.9.0"))]),i("li",null,[t(J),n[13]||(n[13]=l(": any"))]),i("li",null,[t(K),n[14]||(n[14]=l(": ^5.0.0 (requires ")),t(p),n[15]||(n[15]=l(" 14), ≥6.0.0"))]),i("li",null,[t(x),n[16]||(n[16]=l(": ≥1.0.0"))])]),t(r,null,{default:o(()=>n[17]||(n[17]=[l("Steps")])),_:1}),i("ol",null,[i("li",null,[n[52]||(n[52]=l(" Install the plugin. ")),i("ul",E,[i("li",null,[i("p",H,[n[19]||(n[19]=l(" Using ")),t(k,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:o(()=>n[18]||(n[18]=[l("Gradle DSL")])),_:1}),n[20]||(n[20]=l()),n[21]||(n[21]=i("span",{class:"text-muted"},"(recommended)",-1))]),t(b,{id:"install-gradle-dsl"},{groovy:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[24]||(n[24]=l(`plugins {
    `)),t(s,null,{default:o(()=>n[22]||(n[22]=[l("// For JDK 17+")])),_:1}),n[25]||(n[25]=l(`
    id 'org.siouan.frontend-jdk17' version '7.0.0'
    `)),t(s,null,{default:o(()=>n[23]||(n[23]=[l("// For JDK 11+")])),_:1}),n[26]||(n[26]=l(`
    id 'org.siouan.frontend-jdk11' version '7.0.0'
}`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[29]||(n[29]=l(`plugins {
    `)),t(s,null,{default:o(()=>n[27]||(n[27]=[l("// For JDK 17+")])),_:1}),n[30]||(n[30]=l(`
    id("org.siouan.frontend-jdk17") version "7.0.0"
    `)),t(s,null,{default:o(()=>n[28]||(n[28]=[l("// For JDK 11+")])),_:1}),n[31]||(n[31]=l(`
    id("org.siouan.frontend-jdk11") version "7.0.0"
}`))]),_:1})])]),_:1})]),i("li",null,[i("p",Q,[n[33]||(n[33]=l(" Using ")),t(k,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:o(()=>n[32]||(n[32]=[l("Gradle build script block")])),_:1})]),t(b,{id:"install-build-script-block"},{groovy:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[38]||(n[38]=l(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `)),t(s,null,{default:o(()=>n[34]||(n[34]=[l("// For JDK 17+")])),_:1}),n[39]||(n[39]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:7.0.0'
        `)),t(s,null,{default:o(()=>n[35]||(n[35]=[l("// For JDK 11+")])),_:1}),n[40]||(n[40]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:7.0.0'
    }
}

`)),t(s,null,{default:o(()=>n[36]||(n[36]=[l("// For JDK 17+")])),_:1}),n[41]||(n[41]=l(`
apply plugin: 'org.siouan.frontend-jdk17'
`)),t(s,null,{default:o(()=>n[37]||(n[37]=[l("// For JDK 11+")])),_:1}),n[42]||(n[42]=l(`
apply plugin: 'org.siouan.frontend-jdk11'`))]),_:1})])]),kotlin:o(()=>[i("pre",null,[t(u,null,{default:o(()=>[n[47]||(n[47]=l(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `)),t(s,null,{default:o(()=>n[43]||(n[43]=[l("// For JDK 17+")])),_:1}),n[48]||(n[48]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk17:7.0.0")
        `)),t(s,null,{default:o(()=>n[44]||(n[44]=[l("// For JDK 11+")])),_:1}),n[49]||(n[49]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:7.0.0")
    }
}

`)),t(s,null,{default:o(()=>n[45]||(n[45]=[l("// For JDK 17+")])),_:1}),n[50]||(n[50]=l(`
apply(plugin = "org.siouan.frontend-jdk17")
`)),t(s,null,{default:o(()=>n[46]||(n[46]=[l("// For JDK 11+")])),_:1}),n[51]||(n[51]=l(`
apply(plugin = "org.siouan.frontend-jdk11")`))]),_:1})])]),_:1})])])]),i("li",null,[n[54]||(n[54]=l(" Define the ")),t(u,null,{default:o(()=>[t(p,{path:"/api/packages.html#packagemanager",label:"packageManager"})]),_:1}),n[55]||(n[55]=l(" property in the ")),t(u,null,{default:o(()=>n[53]||(n[53]=[l("package.json")])),_:1}),n[56]||(n[56]=l(" file. "))]),i("li",null,[n[59]||(n[59]=l("Add ")),t(u,null,{default:o(()=>n[57]||(n[57]=[l(".frontend-gradle-plugin/")])),_:1}),n[60]||(n[60]=l(" directory to ")),t(u,null,{default:o(()=>n[58]||(n[58]=[l(".gitignore")])),_:1}),n[61]||(n[61]=l(" file(s)."))]),i("li",null,[t(F,{path:g.$config.public.paths.configuration},{default:o(()=>n[62]||(n[62]=[l("Configure")])),_:1},8,["path"]),n[64]||(n[64]=l(" your project, optionally with the help of ")),t(a,{path:"/tree/main/examples"},{default:o(()=>n[63]||(n[63]=[l("examples")])),_:1}),n[65]||(n[65]=l(" provided for typical use cases. "))]),i("li",null,[n[67]||(n[67]=l("Run ")),t(u,null,{default:o(()=>n[66]||(n[66]=[l("gradlew build")])),_:1}),n[68]||(n[68]=l("."))]),i("li",null,[n[75]||(n[75]=l(" If you need to run ")),t(u,null,{default:o(()=>n[69]||(n[69]=[l("node")])),_:1}),n[76]||(n[76]=l("/")),t(u,null,{default:o(()=>n[70]||(n[70]=[l("corepack")])),_:1}),n[77]||(n[77]=l("/")),t(u,null,{default:o(()=>n[71]||(n[71]=[l("npm")])),_:1}),n[78]||(n[78]=l("/")),t(u,null,{default:o(()=>n[72]||(n[72]=[l("pnpm")])),_:1}),n[79]||(n[79]=l("/")),t(u,null,{default:o(()=>n[73]||(n[73]=[l("yarn")])),_:1}),n[80]||(n[80]=l(" executables from a command line (e.g. to start a development server), take a look at the ")),t(F,{path:g.$config.public.paths.faqs},{default:o(()=>n[74]||(n[74]=[l("FAQ")])),_:1},8,["path"]),n[81]||(n[81]=l(". "))])])])}}});export{gn as default};
