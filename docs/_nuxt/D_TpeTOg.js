import{_ as se,u as ie,a as le}from"./BkGJl2ZQ.js";import{_ as pe}from"./CxKyWrla.js";import{_ as d,a as ue}from"./zck6Z6fF.js";import{_ as m}from"./BHt_xBA0.js";import{_ as ce}from"./Bag9INXo.js";import{_ as _e,a as de}from"./COc4Puty.js";import{_ as h,a as F,b as me,c as he,d as fe,e as ye,f as ge,g as be,h as Pe,i as xe,j as ve,k as we,l as ke,m as Se,n as Fe,o as De,p as Ue,q as Ve,r as Ie,s as Re}from"./g2CN4m-W.js";import{_ as f}from"./DlAUqK2U.js";import{o as c,f as y,w as t,a as r,d as e,b as n,g as $e,c as je,u as Ee}from"./0eoLPSdn.js";import{_ as Ne,a as Te,b as Ye,c as He}from"./D6IId-Na.js";import{_ as v}from"./DQimxJDu.js";import{_ as D}from"./DysKOmB8.js";import{_ as Le}from"./C-3WmzZG.js";import{u as Ce}from"./abRkgXpJ.js";import"./D4IM-IFr.js";import"./CtBl-4bT.js";const Oe={},Ae={class:"mb-3"};function Me(_,u){const i=se,a=d,l=m,p=h;return c(),y(p,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:t(()=>[r("p",null,[e("Whether the "),n(i),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("false")]),_:1}),e(", at least the "),n(l,{name:"nodeVersion"}),e(" property must be set. ")]),r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(a,null,{default:t(()=>[e("node")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npx")]),_:1}),e(" executables: "),r("ol",Ae,[r("li",null,[e(" The directory pointed by the "),n(l,{name:"nodeInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e("The directory pointed by the "),n(a,null,{default:t(()=>[e("NODEJS_HOME")]),_:1}),e(" environment variable, if set.")]),r("li",null,[e("Any directory in the "),n(a,null,{default:t(()=>[e("PATH")]),_:1}),e(" environment variable.")])]),e(" Other "),n(a,null,{default:t(()=>[e("node*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const We=f(Oe,[["render",Me]]),qe={};function Be(_,u){const i=v,a=d,l=m,p=h;return c(),y(p,{name:"yarnDistributionProvided",type:"boolean","default-value":"false","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e("Whether the "),n(i,{version:1}),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("false")]),_:1}),e(", at least the "),n(l,{name:"yarnVersion"}),e(" property must be set. ")]),r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(a,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable: "),r("ol",null,[r("li",null,[e(" The directory pointed by the "),n(l,{name:"yarnInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e(" The directory pointed by the "),n(a,null,{default:t(()=>[e("YARN_HOME")]),_:1}),e(" environment variable, if set. ")]),r("li",null,[e("Any directory in the "),n(a,null,{default:t(()=>[e("PATH")]),_:1}),e(" environment variable.")])]),e(" Other "),n(a,null,{default:t(()=>[e("yarn*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const Je=f(qe,[["render",Be]]),ze={};function Ge(_,u){const i=v,a=m,l=d,p=h;return c(),y(p,{name:"yarnVersion",type:"java.lang.String",required:!1,example:"1.22.10","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" Version of "),n(i,{version:1}),e(" that will be installed. The property is required when "),n(a,{name:"yarnEnabled"}),e(" property is "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(". ")])]),_:1})}const Ke=f(ze,[["render",Ge]]),Qe={};function Xe(_,u){const i=d,a=v,l=h;return c(),y(l,{name:"yarnDistributionUrlRoot",type:"java.lang.String","default-value":"https://github.com/yarnpkg/yarn/releases/download/","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" This property is used to build the exact URL to download the distribution, by appending the value of the "),n(i,null,{default:t(()=>[e("yarnDistributionUrlPathPattern")]),_:1}),e(" property to its value. By default, the plugin attempts to download the distribution from the "),n(a,{version:1}),e(" website. ")])]),_:1})}const Ze=f(Qe,[["render",Xe]]),en={},nn={class:"card my-3"},tn={class:"card-body"},on=r("h5",{class:"card-title"},"About URL pattern token",-1),rn={class:"card-text"},an={class:"card-text"};function sn(_,u){const i=m,a=d,l=D,p=v,x=h;return c(),y(x,{name:"yarnDistributionUrlPathPattern",type:"java.lang.String","default-value":"vVERSION/yarn-vVERSION.tar.gz","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" This property is used to build the exact URL to download the distribution, by appending its value to the value of the "),n(i,{name:"yarnDistributionUrlRoot"}),e(" property. This property may be set with a fixed trailing path part (e.g. "),n(a,null,{default:t(()=>[e("dist/yarn-v1.22.10.tar.gz")]),_:1}),e("), or take advantage of the automatic distribution resolution in the plugin, using specific token in the pattern such as "),n(a,null,{default:t(()=>[e("VERSION")]),_:1}),e(" (see the "),n(l,{name:"installYarn"}),e(" task). ")]),r("p",null,[e(" Then, the exact URL used by default to download the distribution is "),n(a,null,{default:t(()=>[e("https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz")]),_:1}),e(". ")]),r("div",nn,[r("div",tn,[on,r("p",rn,[e(" The "),n(i,{name:"yarnDistributionUrlRoot"}),e(" property and this property offer a convenient way to download distributions from a custom website - e.g. a website mirroring the official "),n(p,{version:1}),e(" website, and still take advantage of the automatic resolution of the exact URL. The property may contain the following token: ")]),r("ul",an,[r("li",null,[n(a,null,{default:t(()=>[e("VERSION")]),_:1}),e(": automatically replaced with the version number set in the "),n(i,{name:"yarnVersion"}),e(" property. ")])])])])]),_:1})}const ln=f(en,[["render",sn]]),pn={};function un(_,u){const i=d,a=m,l=F,p=h;return c(),y(p,{name:"yarnDistributionServerUsername",type:"java.lang.String",required:!1,"task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" This property may be used to download the distribution with a given identity (BASIC scheme server-side). When not "),n(i,null,{default:t(()=>[e("null")]),_:1}),e(", the "),n(a,{name:"yarnDistributionServerPassword"}),e(" property is also required. ")]),n(l,null,{default:t(()=>[e(" SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or an external user property, and use one or the other as the value of this property. ")]),_:1})]),_:1})}const cn=f(pn,[["render",un]]),_n={};function dn(_,u){const i=m,a=F,l=h;return c(),y(l,{name:"yarnDistributionServerPassword",type:"java.lang.String",required:!1,"task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" Set this property to authenticate the distribution download (BASIC scheme server-side). The property is ignored unless the "),n(i,{name:"yarnDistributionServerUsername"}),e(" property is defined. ")]),n(a,null,{default:t(()=>[e(" SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or an external user property, and use one or the other as the value of this property. ")]),_:1})]),_:1})}const mn=f(_n,[["render",dn]]),hn={};function fn(_,u){const i=m,a=d,l=h;return c(),y(l,{name:"yarnInstallDirectory",type:"java.io.File",required:!1,"default-value":'project.layout.projectDirectory.dir("yarn")',"task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" Directory where the downloaded distribution shall be installed, or where a provided distribution is located if the "),n(i,{name:"yarnDistributionProvided"}),e(" property is "),n(a,null,{default:t(()=>[e("true")]),_:1}),e(". ")])]),_:1})}const yn=f(hn,[["render",fn]]),k="Configuring Gradle to build a Javascript application with node",S="Choose pre-installed packages or request Node.js/Yarn distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",En=$e({__name:"configuration",setup(_){const u=Ee(),i=`${u.public.canonicalBaseUrl}${u.public.paths.configuration}`;return Ce({link:[{rel:"canonical",href:i}]}),ie({description:S,ogDescription:S,ogTitle:k,ogUrl:i,title:k}),(a,l)=>{const p=le,x=pe,s=d,o=m,w=ce,P=_e,U=We,V=Ne,I=me,R=he,$=fe,j=ye,E=Te,N=Ye,T=Je,Y=Ke,H=Ze,L=ln,C=cn,O=mn,A=yn,M=D,g=Le,W=ge,q=be,B=Pe,J=xe,z=ve,G=He,K=we,Q=ke,X=Se,Z=Fe,ee=De,ne=Ue,te=Ve,oe=Ie,re=Re,ae=ue,b=de;return c(),je("section",null,[n(p,null,{default:t(()=>[e("Configuration")]),_:1}),n(x,null,{default:t(()=>[e("Plugin DSL")]),_:1}),r("p",null,[e(" The "),n(s,null,{default:t(()=>[e("frontend")]),_:1}),e(" extension is the implementation of the plugin DSL, and holds all settings. ")]),n(w,{id:"plugin-dsl"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
    `),n(o,{name:"nodeDistributionProvided"}),e(` = false
    `),n(o,{name:"nodeVersion"}),e(` = '14.17.3'
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(` = 'https://nodejs.org/dist/'
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(` = 'vVERSION/node-vVERSION-ARCH.TYPE'
    `),n(o,{name:"nodeDistributionServerUsername"}),e(` = 'username'
    `),n(o,{name:"nodeDistributionServerPassword"}),e(` = 'password'
    `),n(o,{name:"nodeInstallDirectory"}),e(` = project.layout.projectDirectory.dir("node")

    `),n(o,{name:"yarnEnabled"}),e(` = false
    `),n(o,{name:"yarnVersion"}),e(` = '1.22.10'
    `),n(o,{name:"yarnDistributionProvided"}),e(` = false
    `),n(o,{name:"yarnDistributionUrlRoot"}),e(` = 'https://github.com/yarnpkg/yarn/releases/download/'
    `),n(o,{name:"yarnDistributionUrlPathPattern"}),e(` = 'vVERSION/yarn-vVERSION.tar.gz'
    `),n(o,{name:"yarnDistributionServerUsername"}),e(` = 'username'
    `),n(o,{name:"yarnDistributionServerPassword"}),e(` = 'password'
    `),n(o,{name:"yarnInstallDirectory"}),e(` = project.layout.projectDirectory.dir("yarn")

    `),n(o,{name:"installScript"}),e(` = 'install'
    `),n(o,{name:"cleanScript"}),e(` = 'run clean'
    `),n(o,{name:"assembleScript"}),e(` = 'run assemble'
    `),n(o,{name:"checkScript"}),e(` = 'run check'
    `),n(o,{name:"publishScript"}),e(` = 'run publish'

    `),n(o,{name:"packageJsonDirectory"}),e(` = projectDir
    `),n(o,{name:"httpsProxyHost"}),e(` = '127.0.0.1'
    `),n(o,{name:"httpsProxyPort"}),e(` = 443
    `),n(o,{name:"httpsProxyUsername"}),e(` = 'username'
    `),n(o,{name:"httpsProxyPassword"}),e(` = 'password'
    `),n(o,{name:"httpProxyHost"}),e(` = '127.0.0.1'
    `),n(o,{name:"httpProxyPort"}),e(` = 80
    `),n(o,{name:"httpProxyUsername"}),e(` = 'username'
    `),n(o,{name:"httpProxyPassword"}),e(` = 'password'
    `),n(o,{name:"verboseModeEnabled"}),e(` = false
}`)]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
    `),n(o,{name:"nodeDistributionProvided"}),e(`.set(false)
    `),n(o,{name:"nodeVersion"}),e(`.set("14.17.3")
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(`.set("https://nodejs.org/dist/")
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(`.set("vVERSION/node-vVERSION-ARCH.TYPE")
    `),n(o,{name:"nodeDistributionServerUsername"}),e(`.set("username")
    `),n(o,{name:"nodeDistributionServerPassword"}),e(`.set("password")
    `),n(o,{name:"nodeInstallDirectory"}),e(`.set(project.layout.projectDirectory.dir("node"))

    `),n(o,{name:"yarnEnabled"}),e(`.set(false)
    `),n(o,{name:"yarnVersion"}),e(`.set("1.22.10")
    `),n(o,{name:"yarnDistributionProvided"}),e(`.set(false)
    `),n(o,{name:"yarnDistributionUrlRoot"}),e(`.set("https://github.com/yarnpkg/yarn/releases/download/")
    `),n(o,{name:"yarnDistributionUrlPathPattern"}),e(`.set("vVERSION/yarn-vVERSION.tar.gz")
    `),n(o,{name:"yarnDistributionServerUsername"}),e(`.set("username")
    `),n(o,{name:"yarnDistributionServerPassword"}),e(`.set("password")
    `),n(o,{name:"yarnInstallDirectory"}),e(`.set(project.layout.projectDirectory.dir("yarn"))

    `),n(o,{name:"installScript"}),e(`.set("install")
    `),n(o,{name:"cleanScript"}),e(`.set("run clean")
    `),n(o,{name:"assembleScript"}),e(`.set("run assemble")
    `),n(o,{name:"checkScript"}),e(`.set("run check")
    `),n(o,{name:"publishScript"}),e(`.set("run publish")

    `),n(o,{name:"packageJsonDirectory"}),e(`.set(project.layout.projectDirectory)
    `),n(o,{name:"httpsProxyHost"}),e(`.set("127.0.0.1")
    `),n(o,{name:"httpsProxyPort"}),e(`.set(443)
    `),n(o,{name:"httpsProxyUsername"}),e(`.set("username")
    `),n(o,{name:"httpsProxyPassword"}),e(`.set("password")
    `),n(o,{name:"httpProxyHost"}),e(`.set("127.0.0.1")
    `),n(o,{name:"httpProxyPort"}),e(`.set(80)
    `),n(o,{name:"httpProxyUsername"}),e(`.set("username")
    `),n(o,{name:"httpProxyPassword"}),e(`.set("password")
    `),n(o,{name:"verboseModeEnabled"}),e(`.set(false)
}`)]),_:1})])]),_:1}),n(x,null,{default:t(()=>[e("Properties")]),_:1}),r("section",null,[n(P,null,{default:t(()=>[e("Node.js settings")]),_:1}),n(U),n(V),n(I),n(R),n($),n(j),n(E)]),r("section",null,[n(P,null,{default:t(()=>[e("Yarn settings")]),_:1}),n(N),n(T),n(Y),n(H),n(L),n(C),n(O),n(A)]),r("section",null,[n(P,{id:"script-settings"},{default:t(()=>[e("Script settings")]),_:1}),r("p",null,[e(" Depending on the value of the "),n(o,{name:"yarnEnabled"}),e(" property, the value for each property hereafter is provided as argument either of the "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e(" executable or the "),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable. ")]),r("p",null,[e(" Under Unix-like O/S, white space characters "),n(s,null,{default:t(()=>[e("' '")]),_:1}),e(" in an argument value must be escaped with a backslash character "),n(s,null,{default:t(()=>[e("'\\'")]),_:1}),e(". Under Windows O/S, the whole argument must be enclosed between double-quotes. ")]),r("ul",null,[r("li",null,[e(" Example on Unix-like O/S: "),n(s,null,{default:t(()=>[e("assembleScript = 'run assemble single\\ argument'")]),_:1})]),r("li",null,[e(" Example on Windows O/S: "),n(s,null,{default:t(()=>[e(`assembleScript = 'run assemble "single argument"'`)]),_:1})])]),r("p",null,[e(" Design of the plugin's tasks running a "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable (e.g. "),n(M,{name:"assembleFrontend"}),e(" task) rely on the assumption the "),n(s,null,{default:t(()=>[e("package.json")]),_:1}),e(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the "),n(s,null,{default:t(()=>[e("scripts")]),_:1}),e(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as "),n(s,null,{default:t(()=>[e("run <script-name>")]),_:1}),e(", and nothing more. Example: ")]),n(w,{id:"script-property-example"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(g,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`),n(g,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript = 'run build'
`),n(g,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(g,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`),n(g,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript.set("run build")
`),n(g,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),_:1}),n(W),n(q),n(B),n(J),n(z)]),r("section",null,[n(P,null,{default:t(()=>[e("Other settings")]),_:1}),n(G),n(K),n(Q),n(X),n(Z),n(ee),n(ne),n(te),n(oe),n(re)]),r("section",null,[n(P,{id:"proxy-resolution-process"},{default:t(()=>[e(" About proxy resolution "),n(ae,{path:`${a.$config.public.paths.configuration}#app`,class:"small text-info"},{default:t(()=>[e("↑")]),_:1},8,["path"])]),_:1}),r("p",null,[e(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.nonProxyHosts")]),_:1})]),_:1}),e(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("network properties")]),_:1})]),_:1}),e(". The exact behaviour at runtime is introduced below: ")]),r("ul",null,[r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("https")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpsProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpsProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])]),r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("http")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(b,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])])])])])}}});export{En as default};