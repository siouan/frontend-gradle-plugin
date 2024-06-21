import{_ as F}from"./BFASJRg5.js";import{u as w,a as $,_ as L}from"./BkGJl2ZQ.js";import{_ as N}from"./D3M5zWk8.js";import{_ as T}from"./BSZXaiZV.js";import{_ as C}from"./DQimxJDu.js";import{b as G,a as B,_ as M}from"./zck6Z6fF.js";import{_ as P}from"./GCGlyXyH.js";import{_ as S}from"./CxKyWrla.js";import{_ as z}from"./CHnPAX8H.js";import{_ as D}from"./BFqTcm4f.js";import{_ as j}from"./FM89rvkd.js";import{g as q,c as I,b as t,w as n,a,d as e,u as U,o as V,t as Y}from"./0eoLPSdn.js";import{u as A}from"./abRkgXpJ.js";import"./DlAUqK2U.js";import"./D4IM-IFr.js";const H={class:"fs-2 text-center"},J={class:"card my-3"},O={class:"card-body"},R=a("h5",{class:"card-title"},"Under the hood",-1),W={class:"card-text"},E=a("strong",null,"Lazy configuration",-1),X=a("strong",null,"Self-contained domain design",-1),f="Gradle Node/NPM/Yarn plugin to build Javascript applications",h="All-in-one Gradle Node plugin, Gradle NPM plugin, Gradle Yarn plugin to build Javascript applications with Gradle: distribution management, built-in tasks, NPX support",me=q({__name:"index",setup(K){const p=U(),u=`${p.public.canonicalBaseUrl}${p.public.paths.overview}`;return A({link:[{rel:"canonical",href:u}]}),w({description:h,ogDescription:h,ogTitle:f,ogUrl:u,title:f}),(s,Q)=>{const b=F,y=$,o=L,_=N,m=T,i=C,c=G,d=B,k=P,v=S,l=z,r=M,g=D,x=j;return V(),I("section",null,[t(y,{class:"text-center"},{default:n(()=>[e(" Frontend Gradle plugin "),t(b,{href:"https://github.com/siouan/frontend-gradle-plugin/releases/tag/v6.0.0",src:"https://img.shields.io/badge/Latest%20release-6.0.0-blue.svg",alt:"Latest release 6.0.0",class:"ml-1"})]),_:1}),a("p",H,[e("Build "),t(o),e("-based applications with "),t(_)]),a("p",null,[e(" This plugin allows to integrate into "),t(_),e(" a build based on "),t(o),e("/"),t(m),e("/"),t(i),e(". It is inspired by the philosophy of the "),t(c,{href:"https://github.com/eirslett/frontend-maven-plugin"},{default:n(()=>[e("Frontend Maven plugin")]),_:1}),e(", an equivalent plugin for "),t(c,{href:"https://maven.apache.org",title:"Apache Maven Project"},{default:n(()=>[e("Maven")]),_:1}),e(". Follow the "),t(d,{path:s.$config.public.paths.gettingStarted},{default:n(()=>[e("quick start guide")]),_:1},8,["path"]),e(", and build your frontend application. Take a look at the "),t(k,{path:"/releases",class:"text-lowercase"},{default:n(()=>[e(Y(s.$t("navigation.repository.releases.label")),1)]),_:1}),e(" if you are migrating from a previous version. ")]),t(v,null,{default:n(()=>[e("Features")]),_:1}),t(l,{title:"Distribution management","icon-class":"fa fa-cloud-download-alt text-primary"},{default:n(()=>[e(" The plugin downloads and installs a "),t(o),e(" distribution. When required, the plugin may also trigger the install of a Yarn distribution relying on the "),t(i,{"label-key":"navigation.yarnBerry.baseline"}),e(" baseline. This means that both "),t(i,{version:1,"label-key":"navigation.yarnClassic.originalName"}),e(" and "),t(i,{"label-key":"navigation.yarnBerry.originalName"}),e(" distributions are supported. Optionally, a shared/global "),t(o),e(" distribution may be used instead to avoid network overhead and duplication. The plugin may also use a HTTP proxy server when downloading the "),t(o),e(" distribution, to take advantage of any caching facility, and submit to the organization's security rules. Basic authentication scheme is supported for both distribution and proxy servers. ")]),_:1}),t(l,{title:"Configurable dependencies installation","icon-class":"fa fa-cogs text-secondary"},{default:n(()=>[e(" Depending on the environment, installing frontend dependencies using the "),t(r,null,{default:n(()=>[e("package.json")]),_:1}),e(" file may require a different command (e.g. "),t(r,null,{default:n(()=>[e("npm ci")]),_:1}),e("). ")]),_:1}),t(l,{title:"Built-in tasks","icon-class":"fa fa-plug text-danger"},{default:n(()=>[e(" No need to define tasks to build, clean, check, or publish the frontend application through Gradle lifecycle. The plugin provides ready-to-use "),t(d,{path:s.$config.public.paths.tasks},{default:n(()=>[e("tasks")]),_:1},8,["path"]),e(" out of the box, and ensures their implementation matches Gradle's "),t(g,{path:"/current/userguide/task_configuration_avoidance.html"},{default:n(()=>[e("recommendations")]),_:1}),e(". Plug scripts from a "),t(r,null,{default:n(()=>[e("package.json")]),_:1}),e(" file with the "),t(d,{path:s.$config.public.paths.configuration},{default:n(()=>[e("DSL")]),_:1},8,["path"]),e(", and run "),t(r,null,{default:n(()=>[e("gradlew build")]),_:1}),e(". ")]),_:1}),t(l,{title:"Customization","icon-class":"fa fa-code-branch text-warning"},{default:n(()=>[e(" For more complex use cases, the plugin provides types to create tasks and run custom commands with "),t(o),e(", "),t(m),e(", "),t(x),e(", "),t(i),e(". ")]),_:1}),a("div",J,[a("div",O,[R,a("ul",W,[a("li",null,[E,e(": tasks configuration is delayed until necessary thanks to the use of Gradle "),t(g,{path:"/current/userguide/lazy_configuration.html"},{default:n(()=>[e("lazy configuration API")]),_:1}),e(", to optimize performance of builds and ease chaining tasks I/O. ")]),a("li",null,[X,e(": the plugin design is influenced by "),t(c,{href:"http://cleancoder.com/",title:"Clean coder"},{default:n(()=>[e("clean coding")]),_:1}),e(" principles. The domain layer is isolated from any framework and infrastructure. Writing cross-platform unit tests and maintaining the plugin is easier. Code coverage and predictability increase. ")])])])])])}}});export{me as default};
