import{_ as T}from"./BDM-Na_o.js";import{u as N,a as M,_ as P}from"./D8YflT2D.js";import{_ as z}from"./DiFinWc_.js";import{_ as S}from"./DqVRC5Wu.js";import{_ as B}from"./BFUaQN98.js";import{_ as j}from"./LJeWQp4g.js";import{_ as D}from"./Bq9rqCyi.js";import{_ as I}from"./BzSX6JfU.js";import{b as A,a as U,_ as q}from"./BlmrrCf-.js";import{_ as E}from"./CFN0ZZoK.js";import{_ as R}from"./DCavdD6H.js";import{_ as V}from"./D14qE6Wt.js";import{_ as Y}from"./CBaJjNJ-.js";import{f as H,c as J,b as o,w as i,a as e,d as n,u as O,o as W,t as K}from"./Bt6BF8tg.js";import{u as Q}from"./U9e_5WVs.js";import"./DlAUqK2U.js";import"./CAdOYEvR.js";const X={class:"card my-3"},Z={class:"card-body"},_={class:"card-text"},x="Gradle Node/NPM/PNPM/Yarn plugin to build Javascript applications",F="All-in-one Gradle Node plugin, Gradle NPM plugin, Gradle PNPM plugin, Gradle Yarn plugin to build Javascript applications with Gradle: distribution management, built-in tasks",kt=H({__name:"index",setup(h){const k=O(),y=`${k.public.canonicalBaseUrl}${k.public.paths.overview}`;return Q({link:[{rel:"canonical",href:y}]}),N({description:F,ogDescription:F,ogTitle:x,ogUrl:y,title:x}),(p,t)=>{const v=T,$=M,s=P,L=z,a=S,d=B,u=j,g=D,m=I,f=A,b=U,C=E,l=R,r=q,w=V,G=Y;return W(),J("section",null,[o($,{class:"text-center"},{default:i(()=>[t[0]||(t[0]=n(" Frontend Gradle plugin ")),o(v,{href:"https://github.com/siouan/frontend-gradle-plugin/releases/tag/v9.0.0",src:"https://img.shields.io/badge/Latest%20release-9.0.0-blue.svg",alt:"Latest release 9.0.0"}),t[1]||(t[1]=n()),o(v,{href:"https://github.com/siouan/frontend-gradle-plugin/stargazers",src:"https://img.shields.io/github/stars/siouan/frontend-gradle-plugin?style=badge&label=%E2%9C%AE&color=gold&cacheSeconds=86400",alt:"Stargazers"})]),_:1}),o(d,{class:"fs-2 text-center"},{default:i(()=>[t[2]||(t[2]=n("Build ")),o(s),t[3]||(t[3]=n("-based applications with ")),o(L),t[4]||(t[4]=n(" and ")),o(a)]),_:1}),e("p",null,[t[8]||(t[8]=n(" This plugin allows to build a ")),o(s),t[9]||(t[9]=n("-based application relying on a package manager supported by ")),o(a),t[10]||(t[10]=n(": ")),o(u),t[11]||(t[11]=n(", ")),o(g),t[12]||(t[12]=n(", ")),o(m),t[13]||(t[13]=n(". It is inspired by the philosophy of the ")),o(f,{href:"https://github.com/eirslett/frontend-maven-plugin"},{default:i(()=>t[5]||(t[5]=[n("Frontend Maven plugin")])),_:1}),t[14]||(t[14]=n(", an equivalent plugin for ")),o(f,{href:"https://maven.apache.org",title:"Apache Maven Project"},{default:i(()=>t[6]||(t[6]=[n("Maven")])),_:1}),t[15]||(t[15]=n(". Follow the ")),o(b,{path:p.$config.public.paths.gettingStarted},{default:i(()=>t[7]||(t[7]=[n("quick start guide")])),_:1},8,["path"]),t[16]||(t[16]=n(", and build your frontend application. Take a look at the ")),o(C,{path:"/releases",class:"text-lowercase"},{default:i(()=>[n(K(p.$t("navigation.repository.releases.label")),1)]),_:1}),t[17]||(t[17]=n(" if you are migrating from a previous version. "))]),o(d,null,{default:i(()=>t[18]||(t[18]=[n("Features")])),_:1}),o(l,{title:"Distribution management","icon-class":"fa fa-cloud-download-alt text-primary"},{default:i(()=>[t[19]||(t[19]=n(" By default, the plugin downloads and installs a ")),o(s),t[20]||(t[20]=n(" distribution. Multiple Gradle (sub-)projects may use the distribution downloaded by one of them, or even use a distribution already installed in the workstation to avoid network overhead and duplication. The plugin may also use a HTTP proxy server when downloading the ")),o(s),t[21]||(t[21]=n(" distribution, to take advantage of any caching facility, and submit to the organization's security rules. Basic authentication scheme is supported for both distribution and proxy servers. In case of connectivity issues, downloading the ")),o(s),t[22]||(t[22]=n(" distribution is also retryable, with a configurable exponential backoff strategy. "))]),_:1}),o(l,{title:"Support of multiple package managers","icon-class":"fas fa-dice-d6"},{default:i(()=>[t[24]||(t[24]=n(" The plugin delegates installation of your favorite package manager to ")),o(a),t[25]||(t[25]=n(". Choose your package manager among ")),o(u),t[26]||(t[26]=n(", ")),o(g),t[27]||(t[27]=n(", ")),o(m),t[28]||(t[28]=n(", set the ")),o(s,{path:"/api/packages.html#packagemanager",label:"packageManager"}),t[29]||(t[29]=n(" property in your ")),o(r,null,{default:i(()=>t[23]||(t[23]=[n("package.json")])),_:1}),t[30]||(t[30]=n(" file, and ")),o(a),t[31]||(t[31]=n(" downloads and activates the distribution expected for each (sub-)project. "))]),_:1}),o(l,{title:"Configurable dependencies installation","icon-class":"fa fa-cogs text-secondary"},{default:i(()=>[t[34]||(t[34]=n(" Depending on the environment, installing frontend dependencies using the ")),o(r,null,{default:i(()=>t[32]||(t[32]=[n("package.json")])),_:1}),t[35]||(t[35]=n(" file may require a different command (e.g. ")),o(r,null,{default:i(()=>t[33]||(t[33]=[n("npm ci")])),_:1}),t[36]||(t[36]=n("). "))]),_:1}),o(l,{title:"Built-in tasks","icon-class":"fa fa-plug text-danger"},{default:i(()=>[t[42]||(t[42]=n(" No need to define tasks to build, clean, check, or publish the frontend application through Gradle lifecycle. The plugin provides ready-to-use ")),o(b,{path:p.$config.public.paths.tasks},{default:i(()=>t[37]||(t[37]=[n("tasks")])),_:1},8,["path"]),t[43]||(t[43]=n(" out of the box, and ensures their implementation matches Gradle's ")),o(w,{path:"/current/userguide/task_configuration_avoidance.html"},{default:i(()=>t[38]||(t[38]=[n("recommendations")])),_:1}),t[44]||(t[44]=n(". Plug scripts from a ")),o(r,null,{default:i(()=>t[39]||(t[39]=[n("package.json")])),_:1}),t[45]||(t[45]=n(" file with the ")),o(b,{path:p.$config.public.paths.configuration},{default:i(()=>t[40]||(t[40]=[n("DSL")])),_:1},8,["path"]),t[46]||(t[46]=n(", and run ")),o(r,null,{default:i(()=>t[41]||(t[41]=[n("gradlew build")])),_:1}),t[47]||(t[47]=n(". "))]),_:1}),o(l,{title:"Customization","icon-class":"fa fa-code-branch text-warning"},{default:i(()=>[t[48]||(t[48]=n(" For more complex use cases, the plugin provides types to register custom tasks and run commands with ")),o(s),t[49]||(t[49]=n(", ")),o(a),t[50]||(t[50]=n(", ")),o(u),t[51]||(t[51]=n(", ")),o(g),t[52]||(t[52]=n(", ")),o(m),t[53]||(t[53]=n(". "))]),_:1}),o(d,{class:"text-center"},{default:i(()=>t[54]||(t[54]=[n("They use this plugin, thanks to these organizations!")])),_:1}),o(G),e("div",X,[e("div",Z,[t[63]||(t[63]=e("h5",{class:"card-title"},"Under the hood",-1)),e("ul",_,[e("li",null,[t[56]||(t[56]=e("strong",null,"Lazy configuration",-1)),t[57]||(t[57]=n(": tasks configuration is delayed until necessary thanks to the use of Gradle ")),o(w,{path:"/current/userguide/lazy_configuration.html"},{default:i(()=>t[55]||(t[55]=[n("lazy configuration API")])),_:1}),t[58]||(t[58]=n(", to optimize performance of builds and ease chaining tasks I/O. "))]),e("li",null,[t[60]||(t[60]=e("strong",null,"Self-contained domain design",-1)),t[61]||(t[61]=n(": the plugin design is influenced by ")),o(f,{href:"http://cleancoder.com/",title:"Clean coder"},{default:i(()=>t[59]||(t[59]=[n("clean coding")])),_:1}),t[62]||(t[62]=n(" principles. The domain layer is isolated from any framework and infrastructure. Writing cross-platform unit tests and maintaining the plugin is easier. Code coverage and predictability increase. "))])])])])])}}});export{kt as default};
