import{_ as X,u as Z,a as ee}from"./BkGJl2ZQ.js";import{_ as ne}from"./CxKyWrla.js";import{_ as m,a as te}from"./zck6Z6fF.js";import{_ as f}from"./BHt_xBA0.js";import{_ as oe}from"./Bag9INXo.js";import{_ as re,a as se}from"./COc4Puty.js";import{_ as w,b as ae,c as le,d as ie,e as pe,f as ue,g as ce,h as _e,i as de,j as me,k as fe,l as he,m as ye,n as ge,o as be,p as Pe,q as xe,r as we,s as ke}from"./g2CN4m-W.js";import{_ as k}from"./DlAUqK2U.js";import{o as h,f as S,w as t,a as r,d as e,b as n,g as Se,c as Fe,u as ve}from"./0eoLPSdn.js";import{_ as De,a as Ve,b as je,c as He}from"./D6IId-Na.js";import{_ as Ue}from"./DQimxJDu.js";import{_ as Ie}from"./DysKOmB8.js";import{_ as Ee}from"./C-3WmzZG.js";import{_ as Ne}from"./BPoizGhu.js";import{u as Ce}from"./abRkgXpJ.js";import"./D4IM-IFr.js";import"./CtBl-4bT.js";const Te={},$e={class:"mb-3"};function Le(y,d){const l=X,a=m,u=f,c=w;return h(),S(c,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:t(()=>[r("p",null,[e("Whether the "),n(l),e(" distribution is already provided, and shall not be downloaded.")]),r("ol",null,[r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("false")]),_:1}),e(", at least the "),n(u,{name:"nodeVersion"}),e(" property must be set. ")]),r("li",null,[e(" When "),n(a,null,{default:t(()=>[e("true")]),_:1}),e(", the plugin relies on the following locations in this exact order to find "),n(a,null,{default:t(()=>[e("node")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(a,null,{default:t(()=>[e("npx")]),_:1}),e(" executables: "),r("ol",$e,[r("li",null,[e(" The directory pointed by the "),n(u,{name:"nodeInstallDirectory"}),e(" property, if set. ")]),r("li",null,[e(" The directory pointed by the "),n(a,null,{default:t(()=>[e("FGP_NODEJS_HOME")]),_:1}),e(" environment variable, if set. ")]),r("li",null,[e("Any directory in the "),n(a,null,{default:t(()=>[e("PATH")]),_:1}),e(" environment variable.")])]),e(" Other "),n(a,null,{default:t(()=>[e("node*")]),_:1}),e(" properties should not be used for clarity. ")])])]),_:1})}const Me=k(Te,[["render",Le]]),Oe={};function Re(y,d){const l=Ue,a=f,u=m,c=w;return h(),S(c,{name:"yarnVersion",type:"java.lang.String",required:!1,example:"3.0.0","task-names":["installYarn"]},{default:t(()=>[r("p",null,[e(" Version of "),n(l),e(" that will be installed. The property is required when "),n(a,{name:"yarnEnabled"}),e(" property is "),n(u,null,{default:t(()=>[e("true")]),_:1}),e(". ")])]),_:1})}const Ae=k(Oe,[["render",Re]]),P="Configuring Gradle to build a Javascript application with node",x="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",an=Se({__name:"configuration",setup(y){const d=ve(),l=`${d.public.canonicalBaseUrl}${d.public.paths.configuration}`;return Ce({link:[{rel:"canonical",href:l}]}),Z({description:x,ogDescription:x,ogTitle:P,ogUrl:l,title:P}),(a,u)=>{const c=ee,g=ne,s=m,o=f,b=oe,_=re,F=Me,v=De,D=ae,V=le,j=ie,H=pe,U=Ve,I=je,E=Ae,N=Ie,i=Ee,C=ue,T=ce,$=_e,L=de,M=me,O=He,R=fe,A=he,Y=ye,q=ge,J=be,W=Pe,B=xe,G=we,K=ke,z=Ne,Q=te,p=se;return h(),Fe("section",null,[n(c,null,{default:t(()=>[e("Configuration")]),_:1}),n(g,null,{default:t(()=>[e("Plugin DSL")]),_:1}),r("p",null,[e(" The "),n(s,null,{default:t(()=>[e("frontend")]),_:1}),e(" extension is the implementation of the plugin DSL, and holds all settings. ")]),n(b,{id:"plugin-dsl"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[e(`frontend {
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
}`)]),_:1})])]),_:1}),n(g,null,{default:t(()=>[e("Properties")]),_:1}),r("section",null,[n(_,null,{default:t(()=>[e("Node.js settings")]),_:1}),n(F),n(v),n(D),n(V),n(j),n(H),n(U)]),r("section",null,[n(_,null,{default:t(()=>[e("Yarn settings")]),_:1}),n(I),n(E)]),r("section",null,[n(_,{id:"script-settings"},{default:t(()=>[e("Script settings")]),_:1}),r("p",null,[e(" Depending on the value of the "),n(o,{name:"yarnEnabled"}),e(" property, the value for each property hereafter is provided as argument either of the "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e(" executable or the "),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable. ")]),r("p",null,[e(" Under Unix-like O/S, white space characters "),n(s,null,{default:t(()=>[e("' '")]),_:1}),e(" in an argument value must be escaped with a backslash character "),n(s,null,{default:t(()=>[e("'\\'")]),_:1}),e(". Under Windows O/S, the whole argument must be enclosed between double-quotes. ")]),r("ul",null,[r("li",null,[e(" Example on Unix-like O/S: "),n(s,null,{default:t(()=>[e("assembleScript = 'run assemble single\\ argument'")]),_:1})]),r("li",null,[e(" Example on Windows O/S: "),n(s,null,{default:t(()=>[e(`assembleScript = 'run assemble "single argument"'`)]),_:1})])]),r("p",null,[e(" Design of the plugin's tasks running a "),n(s,null,{default:t(()=>[e("npm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("pnpm")]),_:1}),e("/"),n(s,null,{default:t(()=>[e("yarn")]),_:1}),e(" executable (e.g. "),n(N,{name:"assembleFrontend"}),e(" task) rely on the assumption the "),n(s,null,{default:t(()=>[e("package.json")]),_:1}),e(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the "),n(s,null,{default:t(()=>[e("scripts")]),_:1}),e(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as "),n(s,null,{default:t(()=>[e("run <script-name>")]),_:1}),e(", and nothing more. Example: ")]),n(b,{id:"script-property-example"},{groovy:t(()=>[r("pre",null,[n(s,null,{default:t(()=>[n(i,null,{default:t(()=>[e("// Instead of:")]),_:1}),e(`
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
// }`)]),_:1})]),_:1})])]),_:1}),n(C),n(T),n($),n(L),n(M)]),r("section",null,[n(_,null,{default:t(()=>[e("Other settings")]),_:1}),n(O),n(R),n(A),n(Y),n(q),n(J),n(W),n(B),n(G),n(K),n(z)]),r("section",null,[n(_,{id:"proxy-resolution-process"},{default:t(()=>[e(" About proxy resolution "),n(Q,{path:`${a.$config.public.paths.configuration}#app`,class:"small text-info"},{default:t(()=>[e("↑")]),_:1},8,["path"])]),_:1}),r("p",null,[e(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.nonProxyHosts")]),_:1})]),_:1}),e(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("network properties")]),_:1})]),_:1}),e(". The exact behaviour at runtime is introduced below: ")]),r("ul",null,[r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("https")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpsProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpsProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("https.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])]),r("li",null,[e(" The distribution download URL uses the "),n(s,null,{default:t(()=>[e("http")]),_:1}),e(" protocol: "),r("ol",null,[r("li",null,[e(" If the "),n(o,{name:"httpProxyHost"}),e(" property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the "),n(o,{name:"httpProxyPort"}),e(" property. ")]),r("li",null,[e(" If the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyHost")]),_:1})]),_:1}),e(" network property is not "),n(s,null,{default:t(()=>[e("null")]),_:1}),e(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM "),n(p,null,{default:t(()=>[n(s,null,{default:t(()=>[e("http.proxyPort")]),_:1})]),_:1}),e(" network property. ")])])])])])])}}});export{an as default};
