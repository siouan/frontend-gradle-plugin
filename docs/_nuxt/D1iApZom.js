import{_ as Q,u as X,a as Z}from"./BkGJl2ZQ.js";import{_ as ee}from"./CxKyWrla.js";import{_ as f,a as ne}from"./zck6Z6fF.js";import{_ as h}from"./BHt_xBA0.js";import{_ as te}from"./Bag9INXo.js";import{_ as oe,a as re}from"./COc4Puty.js";import{_ as w}from"./DysKOmB8.js";import{_ as se}from"./BnmEXoAC.js";import{a as le,_ as k,b as ae,c as ie,d as pe,e as ue,f as ce,g as _e,h as de,i as me,j as fe,k as he,l as ye,m as ge,n as be,o as Pe,p as xe,q as we,r as ke,s as Fe}from"./g2CN4m-W.js";import{_ as F}from"./DlAUqK2U.js";import{o as y,f as S,w as t,a as r,d as e,b as n,g as Se,c as De,u as ve}from"./0eoLPSdn.js";import{_ as je,a as Ue}from"./DgvL49CU.js";import{_ as He}from"./C-3WmzZG.js";import{_ as Ie}from"./BPoizGhu.js";import{u as Ne}from"./abRkgXpJ.js";import"./D4IM-IFr.js";import"./CtBl-4bT.js";const Ve={},Ce={class:"mb-3"};function $e(g,_){const a=Q,l=f,c=w,i=h,d=se,s=le,o=k;return y(),S(o,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:t(()=>[r("p",null,[e("Whether the "),n(a),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(l,null,{default:t(()=>[e("false")]),_:1}),e(", task "),n(c,{name:"installNode"}),e(" downloads and installs a "),n(a),e(" distribution using properties "),n(i,{name:"nodeVersion"}),e(", "),n(i,{name:"nodeDistributionUrlRoot"}),e(", "),n(i,{name:"nodeDistributionUrlPathPattern"}),e(". ")]),r("li",null,[e(" When "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(l,null,{default:t(()=>[e("node")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(l,null,{default:t(()=>[e("yarn")]),_:1}),e(" executables: "),r("ol",Ce,[r("li",null,[e(" The directory pointed by the "),n(i,{name:"nodeInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e(" The directory pointed by the "),n(l,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable, if set. ")])]),n(s,null,{default:t(()=>[e("CAUTION: globally installed distribution is modified when using the plugin and "),n(d),e(". Executables "),n(l,null,{default:t(()=>[e("npm")]),_:1}),e(", "),n(l,null,{default:t(()=>[e("pnpm")]),_:1}),e(", "),n(l,null,{default:t(()=>[e("yarn")]),_:1}),e(" that may already exists within "),n(a),e(" install directory are overwritten when task "),n(c,{name:"installPackageManager"}),e(" is run.")]),_:1}),e(" Other "),n(l,null,{default:t(()=>[e("node*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const Ee=F(Ve,[["render",$e]]),Te={};function Me(g,_){const a=h,l=f,c=k;return y(),S(c,{name:"nodeInstallDirectory",type:"java.io.File",required:!1,"default-value":"null","task-names":["installNode","resolvePackageManager","installPackageManager","installFrontend","cleanFrontend","assembleFrontend","checkFrontend","publishFrontend"]},{default:t(()=>[r("p",null,[e(" When property "),n(a,{name:"nodeDistributionProvided"}),e(" is "),n(l,null,{default:t(()=>[e("false")]),_:1}),e(", the downloaded distribution is installed in the directory pointed by this property (defaults to "),n(l,null,{default:t(()=>[e('file("${projectDir}/node")')]),_:1}),e("). When property "),n(a,{name:"nodeDistributionProvided"}),e(" is "),n(l,null,{default:t(()=>[e("true")]),_:1}),e(", this property may be used to point the directory where the distribution is already installed. If "),n(l,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin tries to find the installation directory with the "),n(l,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable. ")])]),_:1})}const Oe=F(Te,[["render",Me]]),Le=r("p",null," The value for each property hereafter is provided as arguments of the package manager executable. ",-1),P="Configuring Gradle to build a Javascript application with node",x="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",rn=Se({__name:"configuration",setup(g){const _=ve(),a=`${_.public.canonicalBaseUrl}${_.public.paths.configuration}`;return Ne({link:[{rel:"canonical",href:a}]}),X({description:x,ogDescription:x,ogTitle:P,ogUrl:a,title:P}),(l,c)=>{const i=Z,d=ee,s=f,o=h,b=te,m=oe,D=Ee,v=je,j=ae,U=ie,H=pe,I=ue,N=Oe,V=w,p=He,C=ce,$=_e,E=de,T=me,M=fe,O=Ue,L=he,R=ye,W=ge,J=be,A=Pe,q=xe,B=we,G=ke,Y=Fe,K=Ie,z=ne,u=re;return y(),De("section",null,[n(i,null,{default:t(()=>[e("Configuration")]),_:1}),n(d,null,{default:t(()=>[e("Plugin DSL")]),_:1}),r("p",null,[e(" The "),n(s,null,{default:t(()=>[e("frontend")]),_:1}),e(" extension is the implementation of the plugin DSL, and holds all settings. ")]),n(b,{id:"plugin-dsl"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
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
}`)]),_:1})])]),_:1}),n(d,null,{default:t(()=>[e("Properties")]),_:1}),r("section",null,[n(m,null,{default:t(()=>[e("Node.js settings")]),_:1}),n(D),n(v),n(j),n(U),n(H),n(I),n(N)]),r("section",null,[n(m,{id:"script-settings"},{default:t(()=>[e("Script settings")]),_:1}),Le,r("p",null,[e(" Under Unix-like O/S, white space characters "),n(s,null,{default:t(()=>[e("' '")]),_:1}),e(" in an argument value must be escaped with a backslash character "),n(s,null,{default:t(()=>[e("'\\'")]),_:1}),e(". Under Windows O/S, the whole argument must be enclosed between double-quotes. ")]),r("ul",null,[r("li",null,[e(" Example on Unix-like O/S: "),n(s,null,{default:t(()=>[e("assembleScript = 'run assemble single\\ argument'")]),_:1})]),r("li",null,[e(" Example on Windows O/S: "),n(s,null,{default:t(()=>[e(`assembleScript = 'run assemble "single argument"'`)]),_:1})])]),r("p",null,[e(" Design of the plugin's tasks running a "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable (e.g. "),n(V,{name:"assembleFrontend"}),e(" task) rely on the assumption the "),n(s,null,{default:t(()=>[e("package.json")]),_:1}),e(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the "),n(s,null,{default:t(()=>[e("scripts")]),_:1}),e(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as "),n(s,null,{default:t(()=>[e("run <script-name>")]),_:1}),e(", and nothing more. Example: ")]),n(b,{id:"script-property-example"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(p,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`),n(p,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript = 'run build'
`),n(p,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),kotlin:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(p,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`),n(p,null,{default:t(()=>[e("// Prefer:")]),_:1}),e(`
assembleScript.set("run build")
`),n(p,null,{default:t(()=>[e(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)]),_:1})]),_:1})])]),_:1}),n(C),n($),n(E),n(T),n(M)]),r("section",null,[n(m,null,{default:t(()=>[e("Other settings")]),_:1}),n(O),n(L),n(R),n(W),n(J),n(A),n(q),n(B),n(G),n(Y),n(K)]),r("section",null,[n(m,{id:"proxy-resolution-process"},{default:t(()=>[e(" About proxy resolution "),n(z,{path:`${l.$config.public.paths.configuration}#app`,class:"small text-info"},{default:t(()=>[e("↑")]),_:1},8,["path"])]),_:1}),r("p",null,[e(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.nonProxyHosts")]),_:1})]),_:1}),e(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("network properties")]),_:1})]),_:1}),e(". The exact behaviour at runtime is introduced below: ")]),r("ul",null,[r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("https")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpsProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpsProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])]),r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("http")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(u,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])])])])])}}});export{rn as default};
