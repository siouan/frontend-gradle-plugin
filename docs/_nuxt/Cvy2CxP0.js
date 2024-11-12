import{u as N,a as $,_ as x}from"./C5OAmh4D.js";import{_ as w}from"./9a1LloSI.js";import{_ as L}from"./Ch3yw5C8.js";import{_ as C}from"./Drsc5h9S.js";import{_ as G}from"./Dik-u63B.js";import{_ as R}from"./CmFZJ-jt.js";import{_ as S}from"./B5cRyKvj.js";import{_ as P}from"./BzBqq-Pf.js";import{_ as B}from"./NZdZu9M6.js";import{_ as I}from"./zeoJfFwT.js";import{_ as M,a as U}from"./DZHecFjF.js";import{_ as q}from"./Fo91r-d8.js";import{_ as Y}from"./f_5w7MK_.js";import{f as A,c as V,b as t,w as o,a as u,d as l,u as O,o as E}from"./Bn0r76rF.js";import{u as H}from"./BQtiW08E.js";import"./DlAUqK2U.js";import"./A6maY33q.js";const Q={class:"list-unstyled my-2 me-3"},W={class:"mb-2"},z={class:"mb-2"},j="Building a Javascript application with the Gradle Node plugin",D="Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.",kn=A({__name:"getting-started",setup(X){const k=O(),a=`${k.public.canonicalBaseUrl}${k.public.paths.gettingStarted}`;return H({link:[{rel:"canonical",href:a}]}),N({description:D,ogDescription:D,ogTitle:j,ogUrl:a,title:j}),(p,n)=>{const J=$,d=w,e=L,m=C,b=G,f=x,K=R,T=S,v=P,F=B,i=I,s=M,y=q,g=U,r=Y;return E(),V("section",null,[t(J,null,{default:o(()=>n[0]||(n[0]=[l("Getting started")])),_:1}),t(d,null,{default:o(()=>n[1]||(n[1]=[l("Requirements")])),_:1}),n[112]||(n[112]=u("p",null,"The following tools must be installed to use the plugin:",-1)),u("ul",null,[u("li",null,[t(e,{path:"/plugin/org.siouan.frontend-jdk21"},{default:o(()=>n[2]||(n[2]=[l("JDK 21 build")])),_:1}),n[3]||(n[3]=l(": ")),t(m),n[4]||(n[4]=l(" 8.5+ "))]),u("li",null,[t(e,{path:"/plugin/org.siouan.frontend-jdk17"},{default:o(()=>n[5]||(n[5]=[l("JDK 17 build")])),_:1}),n[6]||(n[6]=l(": ")),t(m),n[7]||(n[7]=l(" 8.5+ "))]),u("li",null,[t(e,{path:"/plugin/org.siouan.frontend-jdk11"},{default:o(()=>n[8]||(n[8]=[l("JDK 11 build")])),_:1}),n[9]||(n[9]=l(": ")),t(m),n[10]||(n[10]=l(" 8.5+ "))])]),u("p",null,[n[12]||(n[12]=l(" The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the ")),t(b,{path:"/blob/main/CONTRIBUTING.md"},{default:o(()=>n[11]||(n[11]=[l("contributing notes")])),_:1}),n[13]||(n[13]=l("). "))]),t(d,null,{default:o(()=>n[14]||(n[14]=[l("Supported distributions")])),_:1}),u("ul",null,[u("li",null,[t(f),n[15]||(n[15]=l(": ^14.19.0, ≥16.9.0"))]),u("li",null,[t(K),n[16]||(n[16]=l(": any"))]),u("li",null,[t(T),n[17]||(n[17]=l(": ^5.0.0 (requires ")),t(f),n[18]||(n[18]=l(" 14), ≥6.0.0"))]),u("li",null,[t(v),n[19]||(n[19]=l(": ≥1.0.0"))])]),t(d,null,{default:o(()=>n[20]||(n[20]=[l("Steps")])),_:1}),u("ol",null,[u("li",null,[n[67]||(n[67]=l(" Install the plugin. ")),u("ul",Q,[u("li",null,[u("p",W,[n[22]||(n[22]=l(" Using ")),t(F,{path:"/current/userguide/plugins.html#sec:plugins_block"},{default:o(()=>n[21]||(n[21]=[l("Gradle DSL")])),_:1}),n[23]||(n[23]=l()),n[24]||(n[24]=u("span",{class:"text-muted"},"(recommended)",-1))]),t(y,{id:"install-gradle-dsl"},{groovy:o(()=>[u("pre",null,[t(s,null,{default:o(()=>[n[28]||(n[28]=l(`plugins {
    `)),t(i,null,{default:o(()=>n[25]||(n[25]=[l("// For JDK 21+")])),_:1}),n[29]||(n[29]=l(`
    id 'org.siouan.frontend-jdk21' version '9.1.0'
    `)),t(i,null,{default:o(()=>n[26]||(n[26]=[l("// For JDK 17+")])),_:1}),n[30]||(n[30]=l(`
    id 'org.siouan.frontend-jdk17' version '9.1.0'
    `)),t(i,null,{default:o(()=>n[27]||(n[27]=[l("// For JDK 11+")])),_:1}),n[31]||(n[31]=l(`
    id 'org.siouan.frontend-jdk11' version '9.1.0'
}`))]),_:1})])]),kotlin:o(()=>[u("pre",null,[t(s,null,{default:o(()=>[n[35]||(n[35]=l(`plugins {
    `)),t(i,null,{default:o(()=>n[32]||(n[32]=[l("// For JDK 21+")])),_:1}),n[36]||(n[36]=l(`
    id("org.siouan.frontend-jdk21") version "9.1.0"
    `)),t(i,null,{default:o(()=>n[33]||(n[33]=[l("// For JDK 17+")])),_:1}),n[37]||(n[37]=l(`
    id("org.siouan.frontend-jdk17") version "9.1.0"
    `)),t(i,null,{default:o(()=>n[34]||(n[34]=[l("// For JDK 11+")])),_:1}),n[38]||(n[38]=l(`
    id("org.siouan.frontend-jdk11") version "9.1.0"
}`))]),_:1})])]),_:1})]),u("li",null,[u("p",z,[n[40]||(n[40]=l(" Using ")),t(F,{path:"/current/userguide/plugins.html#sec:applying_plugins_buildscript"},{default:o(()=>n[39]||(n[39]=[l("Gradle build script block")])),_:1})]),t(y,{id:"install-build-script-block"},{groovy:o(()=>[u("pre",null,[t(s,null,{default:o(()=>[n[47]||(n[47]=l(`buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        `)),t(i,null,{default:o(()=>n[41]||(n[41]=[l("// For JDK 21+")])),_:1}),n[48]||(n[48]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk21:9.1.0'
        `)),t(i,null,{default:o(()=>n[42]||(n[42]=[l("// For JDK 17+")])),_:1}),n[49]||(n[49]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:9.1.0'
        `)),t(i,null,{default:o(()=>n[43]||(n[43]=[l("// For JDK 11+")])),_:1}),n[50]||(n[50]=l(`
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:9.1.0'
    }
}

`)),t(i,null,{default:o(()=>n[44]||(n[44]=[l("// For JDK 21+")])),_:1}),n[51]||(n[51]=l(`
apply plugin: 'org.siouan.frontend-jdk21'
`)),t(i,null,{default:o(()=>n[45]||(n[45]=[l("// For JDK 17+")])),_:1}),n[52]||(n[52]=l(`
apply plugin: 'org.siouan.frontend-jdk17'
`)),t(i,null,{default:o(()=>n[46]||(n[46]=[l("// For JDK 11+")])),_:1}),n[53]||(n[53]=l(`
apply plugin: 'org.siouan.frontend-jdk11'`))]),_:1})])]),kotlin:o(()=>[u("pre",null,[t(s,null,{default:o(()=>[n[60]||(n[60]=l(`buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        `)),t(i,null,{default:o(()=>n[54]||(n[54]=[l("// For JDK 21+")])),_:1}),n[61]||(n[61]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk21:9.1.0")
        `)),t(i,null,{default:o(()=>n[55]||(n[55]=[l("// For JDK 17+")])),_:1}),n[62]||(n[62]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk17:9.1.0")
        `)),t(i,null,{default:o(()=>n[56]||(n[56]=[l("// For JDK 11+")])),_:1}),n[63]||(n[63]=l(`
        classpath("org.siouan:frontend-gradle-plugin-jdk11:9.1.0")
    }
}

`)),t(i,null,{default:o(()=>n[57]||(n[57]=[l("// For JDK 21+")])),_:1}),n[64]||(n[64]=l(`
apply(plugin = "org.siouan.frontend-jdk21")
`)),t(i,null,{default:o(()=>n[58]||(n[58]=[l("// For JDK 17+")])),_:1}),n[65]||(n[65]=l(`
apply(plugin = "org.siouan.frontend-jdk17")
`)),t(i,null,{default:o(()=>n[59]||(n[59]=[l("// For JDK 11+")])),_:1}),n[66]||(n[66]=l(`
apply(plugin = "org.siouan.frontend-jdk11")`))]),_:1})])]),_:1})])])]),u("li",null,[n[69]||(n[69]=l(" Define the ")),t(s,null,{default:o(()=>[t(f,{path:"/api/packages.html#packagemanager",label:"packageManager"})]),_:1}),n[70]||(n[70]=l(" property in the ")),t(s,null,{default:o(()=>n[68]||(n[68]=[l("package.json")])),_:1}),n[71]||(n[71]=l(" file. "))]),u("li",null,[n[74]||(n[74]=l("Add ")),t(s,null,{default:o(()=>n[72]||(n[72]=[l(".frontend-gradle-plugin/")])),_:1}),n[75]||(n[75]=l(" directory to ")),t(s,null,{default:o(()=>n[73]||(n[73]=[l(".gitignore")])),_:1}),n[76]||(n[76]=l(" file(s)."))]),u("li",null,[t(g,{path:p.$config.public.paths.configuration},{default:o(()=>n[77]||(n[77]=[l("Configure")])),_:1},8,["path"]),n[80]||(n[80]=l(" the build, test (check), deploy (publish) phases in your project, with the help of the ")),t(g,{path:`${p.$config.public.paths.tasks}#dependency-tree`},{default:o(()=>n[78]||(n[78]=[l("built-in tasks dependency tree")])),_:1},8,["path"]),n[81]||(n[81]=l(" and optionally ")),t(b,{path:"/tree/main/examples"},{default:o(()=>n[79]||(n[79]=[l("examples")])),_:1}),n[82]||(n[82]=l(" provided for typical use cases. "))]),u("li",null,[n[84]||(n[84]=l("Run ")),t(s,null,{default:o(()=>n[83]||(n[83]=[l("gradlew build")])),_:1}),n[85]||(n[85]=l("."))]),u("li",null,[n[111]||(n[111]=l("Additionally: ")),u("ul",null,[u("li",null,[n[86]||(n[86]=l(" If you need to execute an instant command with a node-based executable (e.g. to install a dependency), invoke one of the following tasks on a Gradle command line: ")),t(r,{name:"runNode"}),n[87]||(n[87]=l(", ")),t(r,{name:"runCorepack"}),n[88]||(n[88]=l(", ")),t(r,{name:"runNpm"}),n[89]||(n[89]=l(", ")),t(r,{name:"runPnpm"}),n[90]||(n[90]=l(", ")),t(r,{name:"runYarn"}),n[91]||(n[91]=l(". "))]),u("li",null,[n[92]||(n[92]=l(" If you need to define a reusable node-based command with eventual custom inputs, outputs, or environment, use one of the following task types: ")),t(r,{name:"RunNodeTaskType"}),n[93]||(n[93]=l(", ")),t(r,{name:"RunCorepackTaskType"}),n[94]||(n[94]=l(", ")),t(r,{name:"RunNpmTaskType"}),n[95]||(n[95]=l(", ")),t(r,{name:"RunPnpmTaskType"}),n[96]||(n[96]=l(", ")),t(r,{name:"RunYarnTaskType"}),n[97]||(n[97]=l(". "))]),u("li",null,[n[104]||(n[104]=l(" If you need to run ")),t(s,null,{default:o(()=>n[98]||(n[98]=[l("node")])),_:1}),n[105]||(n[105]=l("/")),t(s,null,{default:o(()=>n[99]||(n[99]=[l("corepack")])),_:1}),n[106]||(n[106]=l("/")),t(s,null,{default:o(()=>n[100]||(n[100]=[l("npm")])),_:1}),n[107]||(n[107]=l("/")),t(s,null,{default:o(()=>n[101]||(n[101]=[l("pnpm")])),_:1}),n[108]||(n[108]=l("/")),t(s,null,{default:o(()=>n[102]||(n[102]=[l("yarn")])),_:1}),n[109]||(n[109]=l(" executables from a command line (e.g. to start a development server), take a look at the ")),t(g,{path:p.$config.public.paths.faqs},{default:o(()=>n[103]||(n[103]=[l("FAQ")])),_:1},8,["path"]),n[110]||(n[110]=l(". "))])])])])])}}});export{kn as default};
