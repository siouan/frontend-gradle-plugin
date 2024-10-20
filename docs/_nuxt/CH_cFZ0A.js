import{_ as h,u as c,a as nn}from"./D8YflT2D.js";import{_ as tn}from"./BFUaQN98.js";import{_ as y,a as on}from"./BlmrrCf-.js";import{_ as g}from"./bFTfBRhu.js";import{_ as rn}from"./oBSre_-p.js";import{_ as en,a as sn}from"./ODRuMBaB.js";import{_ as F,b as ln,c as pn,d as un,e as dn,f as mn,g as fn,h as an,i as yn,j as gn,k as bn,l as Pn,m as xn,n as wn,o as kn,p as Sn,q as Fn,r as vn,s as Dn}from"./DvfPIEfj.js";import{_ as v}from"./DlAUqK2U.js";import{o as b,e as D,w as e,a as l,d as t,b as o,f as Vn,c as jn,u as Hn}from"./Bt6BF8tg.js";import{_ as Un,a as In,b as En,c as Nn}from"./DyjbLaBl.js";import{_ as Cn}from"./BzSX6JfU.js";import{_ as Tn}from"./d46FRxqf.js";import{_ as $n}from"./B196FJuh.js";import{_ as Ln}from"./CjKVg3We.js";import{u as Mn}from"./U9e_5WVs.js";import"./CAdOYEvR.js";import"./Dd9XixDx.js";const On={},Rn={class:"mb-3"};function An(P,r){const u=h,p=y,n=g,f=F;return b(),D(f,{name:"nodeDistributionProvided",type:"boolean","default-value":"false","task-names":["installNode"]},{default:e(()=>[l("p",null,[r[0]||(r[0]=t("Whether the ")),o(u),r[1]||(r[1]=t(" distribution is already provided, and shall not be downloaded."))]),l("ol",null,[l("li",null,[r[3]||(r[3]=t(" When ")),o(p,null,{default:e(()=>r[2]||(r[2]=[t("false")])),_:1}),r[4]||(r[4]=t(", at least the ")),o(n,{name:"nodeVersion"}),r[5]||(r[5]=t(" property must be set. "))]),l("li",null,[r[19]||(r[19]=t(" When ")),o(p,null,{default:e(()=>r[6]||(r[6]=[t("true")])),_:1}),r[20]||(r[20]=t(", the plugin relies on the following locations in this exact order to find ")),o(p,null,{default:e(()=>r[7]||(r[7]=[t("node")])),_:1}),r[21]||(r[21]=t("/")),o(p,null,{default:e(()=>r[8]||(r[8]=[t("npm")])),_:1}),r[22]||(r[22]=t("/")),o(p,null,{default:e(()=>r[9]||(r[9]=[t("npx")])),_:1}),r[23]||(r[23]=t(" executables: ")),l("ol",Rn,[l("li",null,[r[10]||(r[10]=t(" The directory pointed by the ")),o(n,{name:"nodeInstallDirectory"}),r[11]||(r[11]=t(" property, if set. "))]),l("li",null,[r[13]||(r[13]=t(" The directory pointed by the ")),o(p,null,{default:e(()=>r[12]||(r[12]=[t("FGP_NODEJS_HOME")])),_:1}),r[14]||(r[14]=t(" environment variable, if set. "))]),l("li",null,[r[16]||(r[16]=t("Any directory in the ")),o(p,null,{default:e(()=>r[15]||(r[15]=[t("PATH")])),_:1}),r[17]||(r[17]=t(" environment variable."))])]),r[24]||(r[24]=t(" Other ")),o(p,null,{default:e(()=>r[18]||(r[18]=[t("node*")])),_:1}),r[25]||(r[25]=t(" properties should not be used for clarity. "))])])]),_:1})}const Yn=v(On,[["render",An]]),qn={};function Jn(P,r){const u=Cn,p=g,n=y,f=F;return b(),D(f,{name:"yarnVersion",type:"java.lang.String",required:!1,example:"3.0.0","task-names":["installYarn"]},{default:e(()=>[l("p",null,[r[1]||(r[1]=t(" Version of ")),o(u),r[2]||(r[2]=t(" that will be installed. The property is required when ")),o(p,{name:"yarnEnabled"}),r[3]||(r[3]=t(" property is ")),o(n,null,{default:e(()=>r[0]||(r[0]=[t("true")])),_:1}),r[4]||(r[4]=t(". "))])]),_:1})}const Wn=v(qn,[["render",Jn]]),k="Configuring Gradle to build a Javascript application with node",S="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",it=Vn({__name:"configuration",setup(P){const r=Hn(),u=`${r.public.canonicalBaseUrl}${r.public.paths.configuration}`;return Mn({link:[{rel:"canonical",href:u}]}),c({description:S,ogDescription:S,ogTitle:k,ogUrl:u,title:k}),(p,n)=>{const f=nn,x=tn,i=y,s=g,w=rn,a=en,V=Yn,j=Un,H=ln,U=pn,I=un,E=dn,N=In,C=En,T=Wn,$=Tn,d=$n,L=mn,M=fn,O=an,R=yn,A=gn,Y=Nn,q=bn,J=Pn,W=xn,B=wn,G=kn,K=Sn,z=Fn,Q=vn,X=Dn,Z=Ln,_=on,m=sn;return b(),jn("section",null,[o(f,null,{default:e(()=>n[0]||(n[0]=[t("Configuration")])),_:1}),o(x,null,{default:e(()=>n[1]||(n[1]=[t("Plugin DSL")])),_:1}),l("p",null,[n[3]||(n[3]=t(" The ")),o(i,null,{default:e(()=>n[2]||(n[2]=[t("frontend")])),_:1}),n[4]||(n[4]=t(" extension is the implementation of the plugin DSL, and holds all settings. "))]),o(w,{id:"plugin-dsl"},{groovy:e(()=>[l("pre",null,[o(i,null,{default:e(()=>[n[5]||(n[5]=t(`frontend {
    `)),o(s,{name:"nodeDistributionProvided"}),n[6]||(n[6]=t(` = false
    `)),o(s,{name:"nodeVersion"}),n[7]||(n[7]=t(` = '14.17.3'
    `)),o(s,{name:"nodeDistributionUrlRoot"}),n[8]||(n[8]=t(` = 'https://nodejs.org/dist/'
    `)),o(s,{name:"nodeDistributionUrlPathPattern"}),n[9]||(n[9]=t(` = 'vVERSION/node-vVERSION-ARCH.TYPE'
    `)),o(s,{name:"nodeDistributionServerUsername"}),n[10]||(n[10]=t(` = 'username'
    `)),o(s,{name:"nodeDistributionServerPassword"}),n[11]||(n[11]=t(` = 'password'
    `)),o(s,{name:"nodeInstallDirectory"}),n[12]||(n[12]=t(` = project.layout.projectDirectory.dir("node")

    `)),o(s,{name:"yarnEnabled"}),n[13]||(n[13]=t(` = false
    `)),o(s,{name:"yarnVersion"}),n[14]||(n[14]=t(` = '3.0.0'

    `)),o(s,{name:"installScript"}),n[15]||(n[15]=t(` = 'install'
    `)),o(s,{name:"cleanScript"}),n[16]||(n[16]=t(` = 'run clean'
    `)),o(s,{name:"assembleScript"}),n[17]||(n[17]=t(` = 'run assemble'
    `)),o(s,{name:"checkScript"}),n[18]||(n[18]=t(` = 'run check'
    `)),o(s,{name:"publishScript"}),n[19]||(n[19]=t(` = 'run publish'

    `)),o(s,{name:"packageJsonDirectory"}),n[20]||(n[20]=t(` = projectDir
    `)),o(s,{name:"httpsProxyHost"}),n[21]||(n[21]=t(` = '127.0.0.1'
    `)),o(s,{name:"httpsProxyPort"}),n[22]||(n[22]=t(` = 443
    `)),o(s,{name:"httpsProxyUsername"}),n[23]||(n[23]=t(` = 'username'
    `)),o(s,{name:"httpsProxyPassword"}),n[24]||(n[24]=t(` = 'password'
    `)),o(s,{name:"httpProxyHost"}),n[25]||(n[25]=t(` = '127.0.0.1'
    `)),o(s,{name:"httpProxyPort"}),n[26]||(n[26]=t(` = 80
    `)),o(s,{name:"httpProxyUsername"}),n[27]||(n[27]=t(` = 'username'
    `)),o(s,{name:"httpProxyPassword"}),n[28]||(n[28]=t(` = 'password'
    `)),o(s,{name:"verboseModeEnabled"}),n[29]||(n[29]=t(` = false
    `)),o(s,{name:"cacheDirectory"}),n[30]||(n[30]=t(` = project.layout.projectDirectory.dir(".frontend-gradle-plugin")
}`))]),_:1})])]),kotlin:e(()=>[l("pre",null,[o(i,null,{default:e(()=>[n[31]||(n[31]=t(`frontend {
    `)),o(s,{name:"nodeDistributionProvided"}),n[32]||(n[32]=t(`.set(false)
    `)),o(s,{name:"nodeVersion"}),n[33]||(n[33]=t(`.set("14.17.3")
    `)),o(s,{name:"nodeDistributionUrlRoot"}),n[34]||(n[34]=t(`.set("https://nodejs.org/dist/")
    `)),o(s,{name:"nodeDistributionUrlPathPattern"}),n[35]||(n[35]=t(`.set("vVERSION/node-vVERSION-ARCH.TYPE")
    `)),o(s,{name:"nodeDistributionServerUsername"}),n[36]||(n[36]=t(`.set("username")
    `)),o(s,{name:"nodeDistributionServerPassword"}),n[37]||(n[37]=t(`.set("password")
    `)),o(s,{name:"nodeInstallDirectory"}),n[38]||(n[38]=t(`.set(project.layout.projectDirectory.dir("node"))

    `)),o(s,{name:"yarnEnabled"}),n[39]||(n[39]=t(`.set(false)
    `)),o(s,{name:"yarnVersion"}),n[40]||(n[40]=t(`.set("3.0.0")

    `)),o(s,{name:"installScript"}),n[41]||(n[41]=t(`.set("install")
    `)),o(s,{name:"cleanScript"}),n[42]||(n[42]=t(`.set("run clean")
    `)),o(s,{name:"assembleScript"}),n[43]||(n[43]=t(`.set("run assemble")
    `)),o(s,{name:"checkScript"}),n[44]||(n[44]=t(`.set("run check")
    `)),o(s,{name:"publishScript"}),n[45]||(n[45]=t(`.set("run publish")

    `)),o(s,{name:"packageJsonDirectory"}),n[46]||(n[46]=t(`.set(project.layout.projectDirectory)
    `)),o(s,{name:"httpsProxyHost"}),n[47]||(n[47]=t(`.set("127.0.0.1")
    `)),o(s,{name:"httpsProxyPort"}),n[48]||(n[48]=t(`.set(443)
    `)),o(s,{name:"httpsProxyUsername"}),n[49]||(n[49]=t(`.set("username")
    `)),o(s,{name:"httpsProxyPassword"}),n[50]||(n[50]=t(`.set("password")
    `)),o(s,{name:"httpProxyHost"}),n[51]||(n[51]=t(`.set("127.0.0.1")
    `)),o(s,{name:"httpProxyPort"}),n[52]||(n[52]=t(`.set(80)
    `)),o(s,{name:"httpProxyUsername"}),n[53]||(n[53]=t(`.set("username")
    `)),o(s,{name:"httpProxyPassword"}),n[54]||(n[54]=t(`.set("password")
    `)),o(s,{name:"verboseModeEnabled"}),n[55]||(n[55]=t(`.set(false)
    `)),o(s,{name:"cacheDirectory"}),n[56]||(n[56]=t(`.set(project.layout.projectDirectory.dir(".frontend-gradle-plugin"))
}`))]),_:1})])]),_:1}),o(x,null,{default:e(()=>n[57]||(n[57]=[t("Properties")])),_:1}),l("section",null,[o(a,null,{default:e(()=>n[58]||(n[58]=[t("Node.js settings")])),_:1}),o(V),o(j),o(H),o(U),o(I),o(E),o(N)]),l("section",null,[o(a,null,{default:e(()=>n[59]||(n[59]=[t("Yarn settings")])),_:1}),o(C),o(T)]),l("section",null,[o(a,{id:"script-settings"},{default:e(()=>n[60]||(n[60]=[t("Script settings")])),_:1}),l("p",null,[n[63]||(n[63]=t(" Depending on the value of the ")),o(s,{name:"yarnEnabled"}),n[64]||(n[64]=t(" property, the value for each property hereafter is provided as argument either of the ")),o(i,null,{default:e(()=>n[61]||(n[61]=[t("npm")])),_:1}),n[65]||(n[65]=t(" executable or the ")),o(i,null,{default:e(()=>n[62]||(n[62]=[t("yarn")])),_:1}),n[66]||(n[66]=t(" executable. "))]),l("p",null,[n[69]||(n[69]=t(" Under Unix-like O/S, white space characters ")),o(i,null,{default:e(()=>n[67]||(n[67]=[t("' '")])),_:1}),n[70]||(n[70]=t(" in an argument value must be escaped with a backslash character ")),o(i,null,{default:e(()=>n[68]||(n[68]=[t("'\\'")])),_:1}),n[71]||(n[71]=t(". Under Windows O/S, the whole argument must be enclosed between double-quotes. "))]),l("ul",null,[l("li",null,[n[73]||(n[73]=t(" Example on Unix-like O/S: ")),o(i,null,{default:e(()=>n[72]||(n[72]=[t("assembleScript = 'run assemble single\\ argument'")])),_:1})]),l("li",null,[n[75]||(n[75]=t(" Example on Windows O/S: ")),o(i,null,{default:e(()=>n[74]||(n[74]=[t(`assembleScript = 'run assemble "single argument"'`)])),_:1})])]),l("p",null,[n[82]||(n[82]=t(" Design of the plugin's tasks running a ")),o(i,null,{default:e(()=>n[76]||(n[76]=[t("npm")])),_:1}),n[83]||(n[83]=t("/")),o(i,null,{default:e(()=>n[77]||(n[77]=[t("pnpm")])),_:1}),n[84]||(n[84]=t("/")),o(i,null,{default:e(()=>n[78]||(n[78]=[t("yarn")])),_:1}),n[85]||(n[85]=t(" executable (e.g. ")),o($,{name:"assembleFrontend"}),n[86]||(n[86]=t(" task) rely on the assumption the ")),o(i,null,{default:e(()=>n[79]||(n[79]=[t("package.json")])),_:1}),n[87]||(n[87]=t(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the ")),o(i,null,{default:e(()=>n[80]||(n[80]=[t("scripts")])),_:1}),n[88]||(n[88]=t(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as ")),o(i,null,{default:e(()=>n[81]||(n[81]=[t("run <script-name>")])),_:1}),n[89]||(n[89]=t(", and nothing more. Example: "))]),o(w,{id:"script-property-example"},{groovy:e(()=>[l("pre",null,[o(i,null,{default:e(()=>[o(d,null,{default:e(()=>n[90]||(n[90]=[t("// Instead of:")])),_:1}),n[93]||(n[93]=t(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`)),o(d,null,{default:e(()=>n[91]||(n[91]=[t("// Prefer:")])),_:1}),n[94]||(n[94]=t(`
assembleScript = 'run build'
`)),o(d,null,{default:e(()=>n[92]||(n[92]=[t(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)])),_:1})]),_:1})])]),kotlin:e(()=>[l("pre",null,[o(i,null,{default:e(()=>[o(d,null,{default:e(()=>n[95]||(n[95]=[t("// Instead of:")])),_:1}),n[98]||(n[98]=t(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`)),o(d,null,{default:e(()=>n[96]||(n[96]=[t("// Prefer:")])),_:1}),n[99]||(n[99]=t(`
assembleScript.set("run build")
`)),o(d,null,{default:e(()=>n[97]||(n[97]=[t(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)])),_:1})]),_:1})])]),_:1}),o(L),o(M),o(O),o(R),o(A)]),l("section",null,[o(a,null,{default:e(()=>n[100]||(n[100]=[t("Other settings")])),_:1}),o(Y),o(q),o(J),o(W),o(B),o(G),o(K),o(z),o(Q),o(X),o(Z)]),l("section",null,[o(a,{id:"proxy-resolution-process"},{default:e(()=>[n[102]||(n[102]=t(" About proxy resolution ")),o(_,{path:`${p.$config.public.paths.configuration}#app`,class:"small text-info"},{default:e(()=>n[101]||(n[101]=[t("↑")])),_:1},8,["path"])]),_:1}),l("p",null,[n[105]||(n[105]=t(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[103]||(n[103]=[t("http.nonProxyHosts")])),_:1})]),_:1}),n[106]||(n[106]=t(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[104]||(n[104]=[t("network properties")])),_:1})]),_:1}),n[107]||(n[107]=t(". The exact behaviour at runtime is introduced below: "))]),l("ul",null,[l("li",null,[n[121]||(n[121]=t(" The distribution download URL uses the ")),o(i,null,{default:e(()=>n[108]||(n[108]=[t("https")])),_:1}),n[122]||(n[122]=t(" protocol: ")),l("ol",null,[l("li",null,[n[110]||(n[110]=t(" If the ")),o(s,{name:"httpsProxyHost"}),n[111]||(n[111]=t(" property is not ")),o(i,null,{default:e(()=>n[109]||(n[109]=[t("null")])),_:1}),n[112]||(n[112]=t(", the plugin uses the IP address or domain name defined with this property and the port defined with the ")),o(s,{name:"httpsProxyPort"}),n[113]||(n[113]=t(" property. "))]),l("li",null,[n[117]||(n[117]=t(" If the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[114]||(n[114]=[t("https.proxyHost")])),_:1})]),_:1}),n[118]||(n[118]=t(" network property is not ")),o(i,null,{default:e(()=>n[115]||(n[115]=[t("null")])),_:1}),n[119]||(n[119]=t(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[116]||(n[116]=[t("https.proxyPort")])),_:1})]),_:1}),n[120]||(n[120]=t(" network property. "))])])]),l("li",null,[n[136]||(n[136]=t(" The distribution download URL uses the ")),o(i,null,{default:e(()=>n[123]||(n[123]=[t("http")])),_:1}),n[137]||(n[137]=t(" protocol: ")),l("ol",null,[l("li",null,[n[125]||(n[125]=t(" If the ")),o(s,{name:"httpProxyHost"}),n[126]||(n[126]=t(" property is not ")),o(i,null,{default:e(()=>n[124]||(n[124]=[t("null")])),_:1}),n[127]||(n[127]=t(", the plugin uses the IP address or domain name defined with this property and the port defined with the ")),o(s,{name:"httpProxyPort"}),n[128]||(n[128]=t(" property. "))]),l("li",null,[n[132]||(n[132]=t(" If the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[129]||(n[129]=[t("http.proxyHost")])),_:1})]),_:1}),n[133]||(n[133]=t(" network property is not ")),o(i,null,{default:e(()=>n[130]||(n[130]=[t("null")])),_:1}),n[134]||(n[134]=t(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM ")),o(m,null,{default:e(()=>[o(i,null,{default:e(()=>n[131]||(n[131]=[t("http.proxyPort")])),_:1})]),_:1}),n[135]||(n[135]=t(" network property. "))])])])])])])}}});export{it as default};
