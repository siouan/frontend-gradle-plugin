import{_ as Q,u as X,a as Z}from"./9-PJfM6s.js";import{_ as ee}from"./DOrgdP5_.js";import{_ as d,a as ne}from"./Fh2q3ca0.js";import{_ as m}from"./BOjnpIcj.js";import{_ as te}from"./CgKqJOwV.js";import{_ as oe,a as re}from"./CPgbTKve.js";import{_ as x,b as se,c as ae,d as le,e as ie,f as pe,g as ue,h as ce,i as _e,j as de,k as me,l as fe,m as he,n as ye,o as ge,p as Pe,q as be,r as xe,s as we}from"./BRG8d5Th.js";import{_ as w}from"./DlAUqK2U.js";import{o as f,f as k,w as t,a as r,d as e,b as n,g as ke,c as Se}from"./BviPqJDo.js";import{_ as Fe,a as ve,b as De,c as Ve}from"./Dqzltwd9.js";import{_ as je}from"./DKQOUftO.js";import{_ as He}from"./D4Nz6glA.js";import{_ as Ue}from"./B9QobURR.js";import{_ as Ie}from"./CxDTPerT.js";import{u as Ee}from"./BzCyTmLR.js";import"./BM98ekuH.js";import"./a-vbRXXh.js";const Ne={},Te={class:"mb-3"};function Ce(h,_){const c=Q,a=d,l=m,s=x;return f(),k(s,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:t(()=>[r("p",null,[e("Whether the "),n(c),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("false")]),_:1}),e(", at least the "),n(l,{name:"nodeVersion"}),e(" property must be set. ")]),r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(a,null,{default:t(()=>[e("node")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npx")]),_:1}),e(" executables: "),r("ol",Te,[r("li",null,[e(" The directory pointed by the "),n(l,{name:"nodeInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e(" The directory pointed by the "),n(a,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable, if set. ")]),r("li",null,[e("Any directory in the "),n(a,null,{default:t(()=>[e("PATH")]),_:1}),e(" environment variable.")])]),e(" Other "),n(a,null,{default:t(()=>[e("node*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const $e=w(Ne,[["render",Ce]]),Le={};function Me(h,_){const c=je,a=m,l=d,s=x;return f(),k(s,{name:"yarnVersion",type:"java.lang.String",required:!1,example:"3.0.0","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" Version of "),n(c),e(" that will be installed. The property is required when "),n(a,{name:"yarnEnabled"}),e(" property is "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(". ")])]),_:1})}const Oe=w(Le,[["render",Me]]),g="https://siouan.github.io/frontend-gradle-plugin/configuration",P="Configuring Gradle to build a Javascript application with node",b="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",rn=ke({__name:"configuration",setup(h){return Ee({link:[{rel:"canonical",href:g}]}),X({description:b,ogDescription:b,ogTitle:P,ogUrl:g,title:P}),(_,c)=>{const a=Z,l=ee,s=d,o=m,y=te,u=oe,S=$e,F=Fe,v=se,D=ae,V=le,j=ie,H=ve,U=De,I=Oe,E=He,i=Ue,N=pe,T=ue,C=ce,$=_e,L=de,M=Ve,O=me,R=fe,A=he,Y=ye,q=ge,J=Pe,W=be,B=xe,G=we,K=Ie,z=ne,p=re;return f(),Se("section",null,[n(a,null,{default:t(()=>[e("Configuration")]),_:1}),n(l,null,{default:t(()=>[e("Plugin DSL")]),_:1}),r("p",null,[e(" The "),n(s,null,{default:t(()=>[e("frontend")]),_:1}),e(" extension is the implementation of the plugin DSL, and holds all settings. ")]),n(y,{id:"plugin-dsl"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
    `),n(o,{name:"nodeDistributionProvided"}),e(` = false
    `),n(o,{name:"nodeVersion"}),e(` = '14.17.3'
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(` = 'https://nodejs.org/dist/'
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(` = 'vVERSION/node-vVERSION-ARCH.TYPE'
    `),n(o,{name:"nodeDistributionServerUsername"}),e(` = 'username'
    `),n(o,{name:"nodeDistributionServerPassword"}),e(` = 'password'
    `),n(o,{name:"nodeInstallDirectory"}),e(` = project.layout.projectDirectory.dir("node")

    `),n(o,{name:"yarnEnabled"}),e(` = false
    `),n(o,{name:"yarnVersion"}),e(` = '3.0.0'

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
    `),n(o,{name:"cacheDirectory"}),e(` = project.layout.projectDirectory.dir(".frontend-gradle-plugin")
}`)]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
    `),n(o,{name:"nodeDistributionProvided"}),e(`.set(false)
    `),n(o,{name:"nodeVersion"}),e(`.set("14.17.3")
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(`.set("https://nodejs.org/dist/")
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(`.set("vVERSION/node-vVERSION-ARCH.TYPE")
    `),n(o,{name:"nodeDistributionServerUsername"}),e(`.set("username")
    `),n(o,{name:"nodeDistributionServerPassword"}),e(`.set("password")
    `),n(o,{name:"nodeInstallDirectory"}),e(`.set(project.layout.projectDirectory.dir("node"))

    `),n(o,{name:"yarnEnabled"}),e(`.set(false)
    `),n(o,{name:"yarnVersion"}),e(`.set("3.0.0")

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
    `),n(o,{name:"cacheDirectory"}),e(`.set(project.layout.projectDirectory.dir(".frontend-gradle-plugin"))
}`)]),_:1})])]),_:1}),n(l,null,{default:t(()=>[e("Properties")]),_:1}),r("section",null,[n(u,null,{default:t(()=>[e("Node.js settings")]),_:1}),n(S),n(F),n(v),n(D),n(V),n(j),n(H)]),r("section",null,[n(u,null,{default:t(()=>[e("Yarn settings")]),_:1}),n(U),n(I)]),r("section",null,[n(u,{id:"script-settings"},{default:t(()=>[e("Script settings")]),_:1}),r("p",null,[e(" Depending on the value of the "),n(o,{name:"yarnEnabled"}),e(" property, the value for each property hereafter is provided as argument either of the "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e(" executable or the "),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable. ")]),r("p",null,[e(" Under Unix-like O/S, white space characters "),n(s,null,{default:t(()=>[e("' '")]),_:1}),e(" in an argument value must be escaped with a backslash character "),n(s,null,{default:t(()=>[e("'\\'")]),_:1}),e(". Under Windows O/S, the whole argument must be enclosed between double-quotes. ")]),r("ul",null,[r("li",null,[e(" Example on Unix-like O/S: "),n(s,null,{default:t(()=>[e("assembleScript = 'run assemble single\\ argument'")]),_:1})]),r("li",null,[e(" Example on Windows O/S: "),n(s,null,{default:t(()=>[e(`assembleScript = 'run assemble "single argument"'`)]),_:1})])]),r("p",null,[e(" Design of the plugin's tasks running a "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable (e.g. "),n(E,{name:"assembleFrontend"}),e(" task) rely on the assumption the "),n(s,null,{default:t(()=>[e("package.json")]),_:1}),e(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the "),n(s,null,{default:t(()=>[e("scripts")]),_:1}),e(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as "),n(s,null,{default:t(()=>[e("run <script-name>")]),_:1}),e(", and nothing more. Example: ")]),n(y,{id:"script-property-example"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(i,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`),n(i,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript = 'run build'
`),n(i,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(i,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`),n(i,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript.set("run build")
`),n(i,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),_:1}),n(N),n(T),n(C),n($),n(L)]),r("section",null,[n(u,null,{default:t(()=>[e("Other settings")]),_:1}),n(M),n(O),n(R),n(A),n(Y),n(q),n(J),n(W),n(B),n(G),n(K)]),r("section",null,[n(u,{id:"proxy-resolution-process"},{default:t(()=>[e(" About proxy resolution "),n(z,{path:`${_.$config.public.paths.configuration}#app`,class:"small text-info"},{default:t(()=>[e("↑")]),_:1},8,["path"])]),_:1}),r("p",null,[e(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.nonProxyHosts")]),_:1})]),_:1}),e(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("network properties")]),_:1})]),_:1}),e(". The exact behaviour at runtime is introduced below: ")]),r("ul",null,[r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("https")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpsProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpsProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])]),r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("http")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])])])])])}}});export{rn as default};
