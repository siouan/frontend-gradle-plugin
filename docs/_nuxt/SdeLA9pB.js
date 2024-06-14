import{_ as z,u as Q,a as X}from"./B3Uso2j1.js";import{_ as Z}from"./QmxwIEzA.js";import{_ as m,a as ee}from"./BOqwjjew.js";import{_ as h}from"./BSZ3xRWO.js";import{_ as ne}from"./BEHa1Srm.js";import{_ as te,a as oe}from"./BtNWQ_Pt.js";import{_ as x}from"./DBovWzHr.js";import{_ as re}from"./DnGS1Mv3.js";import{a as se,_ as w,b as le,c as ae,d as ie,e as pe,f as ue,g as ce,h as _e,i as de,j as me,k as he,l as fe,m as ye,n as ge,o as Pe,p as be,q as xe,r as we,s as ke}from"./CzQp-vWA.js";import{_ as k}from"./DlAUqK2U.js";import{o as f,f as F,w as t,a as r,d as e,b as n,g as Fe,c as Se}from"./D71axJVe.js";import{_ as De,a as ve}from"./BR6FXd7y.js";import{_ as je}from"./DM2uf8uD.js";import{_ as Ue}from"./CzwlO-jy.js";import{u as He}from"./CUOE0wSc.js";import"./BfsWJKyW.js";import"./B1Cn1jyM.js";const Ie={},Ne={class:"mb-3"};function Ve(y,d){const a=z,l=m,i=x,s=h,o=re,_=se,p=w;return f(),F(p,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:t(()=>[r("p",null,[e("Whether the "),n(a),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(l,null,{default:t(()=>[e("false")]),_:1}),e(", task "),n(i,{name:"installNode"}),e(" downloads and installs a "),n(a),e(" distribution using properties "),n(s,{name:"nodeVersion"}),e(", "),n(s,{name:"nodeDistributionUrlRoot"}),e(", "),n(s,{name:"nodeDistributionUrlPathPattern"}),e(". ")]),r("li",null,[e(" When "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(l,null,{default:t(()=>[e("node")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("yarn")]),_:1}),e(" executables: "),r("ol",Ne,[r("li",null,[e(" The directory pointed by the "),n(s,{name:"nodeInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e(" The directory pointed by the "),n(l,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable, if set. ")])]),n(_,null,{default:t(()=>[e("CAUTION: globally installed distribution is modified when using the plugin and "),n(o),e(". Executables "),n(l,null,{default:t(()=>[e("npm")]),_:1}),e(", "),n(l,null,{default:t(()=>[e("pnpm")]),_:1}),e(", "),n(l,null,{default:t(()=>[e("yarn")]),_:1}),e(" that may already exists within "),n(a),e(" install directory are overwritten when task "),n(i,{name:"installPackageManager"}),e(" is run.")]),_:1}),e(" Other "),n(l,null,{default:t(()=>[e("node*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const Ee=k(Ie,[["render",Ve]]),Te={};function Ce(y,d){const a=h,l=m,i=w;return f(),F(i,{name:"nodeInstallDirectory",type:"java.io.File",required:!1,"default-value":"null","task-names":["installNode","resolvePackageManager","installPackageManager","installFrontend","cleanFrontend","assembleFrontend","checkFrontend","publishFrontend"]},{default:t(()=>[r("p",null,[e(" When property "),n(a,{name:"nodeDistributionProvided"}),e(" is "),n(l,null,{default:t(()=>[e("false")]),_:1}),e(", the downloaded distribution is installed in the directory pointed by this property (defaults to "),n(l,null,{default:t(()=>[e('file("${projectDir}/node")')]),_:1}),e("). When property "),n(a,{name:"nodeDistributionProvided"}),e(" is "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(", this property may be used to point the directory where the distribution is already installed. If "),n(l,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin tries to find the installation directory with the "),n(l,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable. ")])]),_:1})}const $e=k(Te,[["render",Ce]]),Me=r("p",null," The value for each property hereafter is provided as arguments of the package manager executable. ",-1),g="https://siouan.github.io/frontend-gradle-plugin/configuration",P="Configuring Gradle to build a Javascript application with node",b="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",tn=Fe({__name:"configuration",setup(y){return He({link:[{rel:"canonical",href:g}]}),Q({description:b,ogDescription:b,ogTitle:P,ogUrl:g,title:P}),(d,a)=>{const l=X,i=Z,s=m,o=h,_=ne,p=te,S=Ee,D=De,v=le,j=ae,U=ie,H=pe,I=$e,N=x,u=je,V=ue,E=ce,T=_e,C=de,$=me,M=ve,O=he,L=fe,R=ye,W=ge,J=Pe,A=be,q=xe,G=we,B=ke,Y=Ue,K=ee,c=oe;return f(),Se("section",null,[n(l,null,{default:t(()=>[e("Configuration")]),_:1}),n(i,null,{default:t(()=>[e("Plugin DSL")]),_:1}),r("p",null,[e(" The "),n(s,null,{default:t(()=>[e("frontend")]),_:1}),e(" extension is the implementation of the plugin DSL, and holds all settings. ")]),n(_,{id:"plugin-dsl"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
    `),n(o,{name:"nodeDistributionProvided"}),e(` = false
    `),n(o,{name:"nodeVersion"}),e(` = '18.16.0'
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(` = 'https://nodejs.org/dist/'
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(` = 'vVERSION/node-vVERSION-ARCH.TYPE'
    `),n(o,{name:"nodeDistributionServerUsername"}),e(` = 'username'
    `),n(o,{name:"nodeDistributionServerPassword"}),e(` = 'password'
    `),n(o,{name:"nodeInstallDirectory"}),e(` = project.layout.projectDirectory.dir("node")

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
    `),n(o,{name:"nodeVersion"}),e(`.set("18.16.0")
    `),n(o,{name:"nodeDistributionUrlRoot"}),e(`.set("https://nodejs.org/dist/")
    `),n(o,{name:"nodeDistributionUrlPathPattern"}),e(`.set("vVERSION/node-vVERSION-ARCH.TYPE")
    `),n(o,{name:"nodeDistributionServerUsername"}),e(`.set("username")
    `),n(o,{name:"nodeDistributionServerPassword"}),e(`.set("password")
    `),n(o,{name:"nodeInstallDirectory"}),e(`.set(project.layout.projectDirectory.dir("node"))

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
}`)]),_:1})])]),_:1}),n(i,null,{default:t(()=>[e("Properties")]),_:1}),r("section",null,[n(p,null,{default:t(()=>[e("Node.js settings")]),_:1}),n(S),n(D),n(v),n(j),n(U),n(H),n(I)]),r("section",null,[n(p,{id:"script-settings"},{default:t(()=>[e("Script settings")]),_:1}),Me,r("p",null,[e(" Under Unix-like O/S, white space characters "),n(s,null,{default:t(()=>[e("' '")]),_:1}),e(" in an argument value must be escaped with a backslash character "),n(s,null,{default:t(()=>[e("'\\'")]),_:1}),e(". Under Windows O/S, the whole argument must be enclosed between double-quotes. ")]),r("ul",null,[r("li",null,[e(" Example on Unix-like O/S: "),n(s,null,{default:t(()=>[e("assembleScript = 'run assemble single\\ argument'")]),_:1})]),r("li",null,[e(" Example on Windows O/S: "),n(s,null,{default:t(()=>[e(`assembleScript = 'run assemble "single argument"'`)]),_:1})])]),r("p",null,[e(" Design of the plugin's tasks running a "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable (e.g. "),n(N,{name:"assembleFrontend"}),e(" task) rely on the assumption the "),n(s,null,{default:t(()=>[e("package.json")]),_:1}),e(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the "),n(s,null,{default:t(()=>[e("scripts")]),_:1}),e(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as "),n(s,null,{default:t(()=>[e("run <script-name>")]),_:1}),e(", and nothing more. Example: ")]),n(_,{id:"script-property-example"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(u,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`),n(u,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript = 'run build'
`),n(u,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(u,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`),n(u,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript.set("run build")
`),n(u,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),_:1}),n(V),n(E),n(T),n(C),n($)]),r("section",null,[n(p,null,{default:t(()=>[e("Other settings")]),_:1}),n(M),n(O),n(L),n(R),n(W),n(J),n(A),n(q),n(G),n(B),n(Y)]),r("section",null,[n(p,{id:"proxy-resolution-process"},{default:t(()=>[e(" About proxy resolution "),n(K,{path:`${d.$config.public.paths.configuration}#app`,class:"small text-info"},{default:t(()=>[e("↑")]),_:1},8,["path"])]),_:1}),r("p",null,[e(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.nonProxyHosts")]),_:1})]),_:1}),e(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("network properties")]),_:1})]),_:1}),e(". The exact behaviour at runtime is introduced below: ")]),r("ul",null,[r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("https")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpsProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpsProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])]),r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("http")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(c,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])])])])])}}});export{tn as default};
