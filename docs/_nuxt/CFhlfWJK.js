import{u as Q,a as X}from"./pxczNYIJ.js";import{_ as Z}from"./B2PBjYl0.js";import{_ as h,a as c}from"./c6tsABtt.js";import{_ as nn}from"./D9-hoJGw.js";import{_ as tn}from"./BE7txKzx.js";import{_ as on,a as en}from"./s0z2JT-x.js";import{_ as rn,a as sn,b as ln,c as pn,d as un,e as mn,f as dn,g as an}from"./By8mKE0K.js";import{_ as fn,a as yn}from"./MJTQExI4.js";import{b as gn,c as Pn,d as xn,e as bn,f as wn,g as kn,h as Sn,i as Fn,j as vn,k as Dn,l as In,m as Hn,n as jn,o as Mn,p as Un,q as Vn,r as Nn,s as Cn}from"./BB6SEIY3.js";import{_ as Rn}from"./1ktSNDAW.js";import{_ as Tn}from"./CU-mwjLd.js";import{_ as En}from"./DQDok975.js";import{f as _n,c as On,b as t,w as r,a as l,d as o,u as $n,o as An}from"./CPdTkOzn.js";import{u as Ln}from"./CtXgoLjO.js";import"./DlAUqK2U.js";import"./BWaDds42.js";import"./D6leYXVZ.js";import"./DAH-GGfd.js";const y="Configuring Gradle to build a Javascript application with node",g="Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.",lt=_n({__name:"configuration",setup(Jn){const m=$n(),d=`${m.public.canonicalBaseUrl}${m.public.paths.configuration}`;return Ln({link:[{rel:"canonical",href:d}]}),Q({description:g,ogDescription:g,ogTitle:y,ogUrl:d,title:y}),(P,n)=>{const x=X,a=Z,s=h,e=nn,f=tn,u=on,b=rn,w=fn,k=gn,S=Pn,F=xn,v=bn,D=sn,I=ln,H=Rn,p=Tn,j=wn,M=kn,U=Sn,V=Fn,N=vn,C=yn,R=Dn,T=In,E=Hn,_=jn,O=Mn,$=Un,A=Vn,L=Nn,J=pn,q=un,B=mn,G=dn,W=an,Y=Cn,K=En,z=c,i=en;return An(),On("section",null,[t(x,null,{default:r(()=>n[0]||(n[0]=[o("Configuration")])),_:1}),t(a,null,{default:r(()=>n[1]||(n[1]=[o("Plugin DSL")])),_:1}),l("p",null,[n[3]||(n[3]=o(" The ")),t(s,null,{default:r(()=>n[2]||(n[2]=[o("frontend")])),_:1}),n[4]||(n[4]=o(" extension is the implementation of the plugin DSL, and holds all settings. "))]),t(f,{id:"plugin-dsl"},{groovy:r(()=>[l("pre",null,[t(s,null,{default:r(()=>[n[5]||(n[5]=o(`frontend {
    `)),t(e,{name:"nodeDistributionProvided"}),n[6]||(n[6]=o(` = false
    `)),t(e,{name:"nodeVersion"}),n[7]||(n[7]=o(` = '20.18.0'
    `)),t(e,{name:"nodeDistributionUrlRoot"}),n[8]||(n[8]=o(` = 'https://nodejs.org/dist/'
    `)),t(e,{name:"nodeDistributionUrlPathPattern"}),n[9]||(n[9]=o(` = 'vVERSION/node-vVERSION-ARCH.TYPE'
    `)),t(e,{name:"nodeDistributionServerUsername"}),n[10]||(n[10]=o(` = 'username'
    `)),t(e,{name:"nodeDistributionServerPassword"}),n[11]||(n[11]=o(` = 'password'
    `)),t(e,{name:"nodeInstallDirectory"}),n[12]||(n[12]=o(` = project.layout.projectDirectory.dir("node")
    `)),t(e,{name:"corepackVersion"}),n[13]||(n[13]=o(` = 'latest'

    `)),t(e,{name:"installScript"}),n[14]||(n[14]=o(` = 'install'
    `)),t(e,{name:"cleanScript"}),n[15]||(n[15]=o(` = 'run clean'
    `)),t(e,{name:"assembleScript"}),n[16]||(n[16]=o(` = 'run assemble'
    `)),t(e,{name:"checkScript"}),n[17]||(n[17]=o(` = 'run check'
    `)),t(e,{name:"publishScript"}),n[18]||(n[18]=o(` = 'run publish'

    `)),t(e,{name:"packageJsonDirectory"}),n[19]||(n[19]=o(` = projectDir
    `)),t(e,{name:"httpsProxyHost"}),n[20]||(n[20]=o(` = '127.0.0.1'
    `)),t(e,{name:"httpsProxyPort"}),n[21]||(n[21]=o(` = 443
    `)),t(e,{name:"httpsProxyUsername"}),n[22]||(n[22]=o(` = 'username'
    `)),t(e,{name:"httpsProxyPassword"}),n[23]||(n[23]=o(` = 'password'
    `)),t(e,{name:"httpProxyHost"}),n[24]||(n[24]=o(` = '127.0.0.1'
    `)),t(e,{name:"httpProxyPort"}),n[25]||(n[25]=o(` = 80
    `)),t(e,{name:"httpProxyUsername"}),n[26]||(n[26]=o(` = 'username'
    `)),t(e,{name:"httpProxyPassword"}),n[27]||(n[27]=o(` = 'password'
    `)),t(e,{name:"maxDownloadAttempts"}),n[28]||(n[28]=o(` = 1
    `)),t(e,{name:"retryHttpStatuses"}),n[29]||(n[29]=o(` = [408, 429, 500, 502, 503, 504]
    `)),t(e,{name:"retryInitialIntervalMs"}),n[30]||(n[30]=o(` = 1000
    `)),t(e,{name:"retryIntervalMultiplier"}),n[31]||(n[31]=o(` = 2.0
    `)),t(e,{name:"retryMaxIntervalMs"}),n[32]||(n[32]=o(` = 30000
    `)),t(e,{name:"verboseModeEnabled"}),n[33]||(n[33]=o(` = false
    `)),t(e,{name:"cacheDirectory"}),n[34]||(n[34]=o(` = project.layout.projectDirectory.dir(".frontend-gradle-plugin")
}`))]),_:1})])]),kotlin:r(()=>[l("pre",null,[t(s,null,{default:r(()=>[n[35]||(n[35]=o(`frontend {
    `)),t(e,{name:"nodeDistributionProvided"}),n[36]||(n[36]=o(`.set(false)
    `)),t(e,{name:"nodeVersion"}),n[37]||(n[37]=o(`.set("20.18.0")
    `)),t(e,{name:"nodeDistributionUrlRoot"}),n[38]||(n[38]=o(`.set("https://nodejs.org/dist/")
    `)),t(e,{name:"nodeDistributionUrlPathPattern"}),n[39]||(n[39]=o(`.set("vVERSION/node-vVERSION-ARCH.TYPE")
    `)),t(e,{name:"nodeDistributionServerUsername"}),n[40]||(n[40]=o(`.set("username")
    `)),t(e,{name:"nodeDistributionServerPassword"}),n[41]||(n[41]=o(`.set("password")
    `)),t(e,{name:"nodeInstallDirectory"}),n[42]||(n[42]=o(`.set(project.layout.projectDirectory.dir("node"))
    `)),t(e,{name:"corepackVersion"}),n[43]||(n[43]=o(`.set("latest")

    `)),t(e,{name:"installScript"}),n[44]||(n[44]=o(`.set("install")
    `)),t(e,{name:"cleanScript"}),n[45]||(n[45]=o(`.set("run clean")
    `)),t(e,{name:"assembleScript"}),n[46]||(n[46]=o(`.set("run assemble")
    `)),t(e,{name:"checkScript"}),n[47]||(n[47]=o(`.set("run check")
    `)),t(e,{name:"publishScript"}),n[48]||(n[48]=o(`.set("run publish")

    `)),t(e,{name:"packageJsonDirectory"}),n[49]||(n[49]=o(`.set(project.layout.projectDirectory)
    `)),t(e,{name:"httpsProxyHost"}),n[50]||(n[50]=o(`.set("127.0.0.1")
    `)),t(e,{name:"httpsProxyPort"}),n[51]||(n[51]=o(`.set(443)
    `)),t(e,{name:"httpsProxyUsername"}),n[52]||(n[52]=o(`.set("username")
    `)),t(e,{name:"httpsProxyPassword"}),n[53]||(n[53]=o(`.set("password")
    `)),t(e,{name:"httpProxyHost"}),n[54]||(n[54]=o(`.set("127.0.0.1")
    `)),t(e,{name:"httpProxyPort"}),n[55]||(n[55]=o(`.set(80)
    `)),t(e,{name:"httpProxyUsername"}),n[56]||(n[56]=o(`.set("username")
    `)),t(e,{name:"httpProxyPassword"}),n[57]||(n[57]=o(`.set("password")
    `)),t(e,{name:"maxDownloadAttempts"}),n[58]||(n[58]=o(`.set(1)
    `)),t(e,{name:"retryHttpStatuses"}),n[59]||(n[59]=o(`.set(setOf(408, 429, 500, 502, 503, 504))
    `)),t(e,{name:"retryInitialIntervalMs"}),n[60]||(n[60]=o(`.set(1000)
    `)),t(e,{name:"retryIntervalMultiplier"}),n[61]||(n[61]=o(`.set(2.0)
    `)),t(e,{name:"retryMaxIntervalMs"}),n[62]||(n[62]=o(`.set(30000)
    `)),t(e,{name:"verboseModeEnabled"}),n[63]||(n[63]=o(`.set(false)
    `)),t(e,{name:"cacheDirectory"}),n[64]||(n[64]=o(`.set(project.layout.projectDirectory.dir(".frontend-gradle-plugin"))
}`))]),_:1})])]),_:1}),t(a,null,{default:r(()=>n[65]||(n[65]=[o("Properties")])),_:1}),l("section",null,[t(u,null,{default:r(()=>n[66]||(n[66]=[o("Node.js settings")])),_:1}),t(b),t(w),t(k),t(S),t(F),t(v),t(D),t(I)]),l("section",null,[t(u,{id:"script-settings"},{default:r(()=>n[67]||(n[67]=[o("Script settings")])),_:1}),n[101]||(n[101]=l("p",null," The value for each property hereafter is provided as arguments of the package manager executable. ",-1)),l("p",null,[n[70]||(n[70]=o(" Under Unix-like O/S, white space characters ")),t(s,null,{default:r(()=>n[68]||(n[68]=[o("' '")])),_:1}),n[71]||(n[71]=o(" in an argument value must be escaped with a backslash character ")),t(s,null,{default:r(()=>n[69]||(n[69]=[o("'\\'")])),_:1}),n[72]||(n[72]=o(". Under Windows O/S, the whole argument must be enclosed between double-quotes. "))]),l("ul",null,[l("li",null,[n[74]||(n[74]=o(" Example on Unix-like O/S: ")),t(s,null,{default:r(()=>n[73]||(n[73]=[o("assembleScript = 'run assemble single\\ argument'")])),_:1})]),l("li",null,[n[76]||(n[76]=o(" Example on Windows O/S: ")),t(s,null,{default:r(()=>n[75]||(n[75]=[o(`assembleScript = 'run assemble "single argument"'`)])),_:1})])]),l("p",null,[n[83]||(n[83]=o(" Design of the plugin's tasks running a ")),t(s,null,{default:r(()=>n[77]||(n[77]=[o("npm")])),_:1}),n[84]||(n[84]=o("/")),t(s,null,{default:r(()=>n[78]||(n[78]=[o("pnpm")])),_:1}),n[85]||(n[85]=o("/")),t(s,null,{default:r(()=>n[79]||(n[79]=[o("yarn")])),_:1}),n[86]||(n[86]=o(" executable (e.g. ")),t(H,{name:"assembleFrontend"}),n[87]||(n[87]=o(" task) rely on the assumption the ")),t(s,null,{default:r(()=>n[80]||(n[80]=[o("package.json")])),_:1}),n[88]||(n[88]=o(" file contains all script definitions, and is the single resource defining how to build the frontend application, execute unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the ")),t(s,null,{default:r(()=>n[81]||(n[81]=[o("scripts")])),_:1}),n[89]||(n[89]=o(" section, and avoid as much as possible using the properties below to run complex commands. Keeping these scripts in one place should also ease finding out where they are located. In an ideal situation, the properties below shall all have a value such as ")),t(s,null,{default:r(()=>n[82]||(n[82]=[o("run <script-name>")])),_:1}),n[90]||(n[90]=o(", and nothing more. Example: "))]),t(f,{id:"script-property-example"},{groovy:r(()=>[l("pre",null,[t(s,null,{default:r(()=>[t(p,null,{default:r(()=>n[91]||(n[91]=[o("// Instead of:")])),_:1}),n[94]||(n[94]=o(`
assembleScript = 'run webpack -- --config webpack.config.js --profile'

`)),t(p,null,{default:r(()=>n[92]||(n[92]=[o("// Prefer:")])),_:1}),n[95]||(n[95]=o(`
assembleScript = 'run build'
`)),t(p,null,{default:r(()=>n[93]||(n[93]=[o(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)])),_:1})]),_:1})])]),kotlin:r(()=>[l("pre",null,[t(s,null,{default:r(()=>[t(p,null,{default:r(()=>n[96]||(n[96]=[o("// Instead of:")])),_:1}),n[99]||(n[99]=o(`
assembleScript.set("run webpack -- --config webpack.config.js --profile")

`)),t(p,null,{default:r(()=>n[97]||(n[97]=[o("// Prefer:")])),_:1}),n[100]||(n[100]=o(`
assembleScript.set("run build")
`)),t(p,null,{default:r(()=>n[98]||(n[98]=[o(`// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }`)])),_:1})]),_:1})])]),_:1}),t(j),t(M),t(U),t(V),t(N)]),l("section",null,[t(u,null,{default:r(()=>n[102]||(n[102]=[o("Other settings")])),_:1}),t(C),t(R),t(T),t(E),t(_),t(O),t($),t(A),t(L),t(J),t(q),t(B),t(G),t(W),t(Y),t(K)]),l("section",null,[t(u,{id:"proxy-resolution-process"},{default:r(()=>[n[104]||(n[104]=o(" About proxy resolution ")),t(z,{path:`${P.$config.public.paths.configuration}#app`,class:"small text-info"},{default:r(()=>n[103]||(n[103]=[o("↑")])),_:1},8,["path"])]),_:1}),l("p",null,[n[107]||(n[107]=o(" As a prerequisite, the distribution server's IP address or domain name must not match an entry specified in the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[105]||(n[105]=[o("http.nonProxyHosts")])),_:1})]),_:1}),n[108]||(n[108]=o(" network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own settings in priority, and finally on the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[106]||(n[106]=[o("network properties")])),_:1})]),_:1}),n[109]||(n[109]=o(". The exact behaviour at runtime is introduced below: "))]),l("ul",null,[l("li",null,[n[123]||(n[123]=o(" The distribution download URL uses the ")),t(s,null,{default:r(()=>n[110]||(n[110]=[o("https")])),_:1}),n[124]||(n[124]=o(" protocol: ")),l("ol",null,[l("li",null,[n[112]||(n[112]=o(" If the ")),t(e,{name:"httpsProxyHost"}),n[113]||(n[113]=o(" property is not ")),t(s,null,{default:r(()=>n[111]||(n[111]=[o("null")])),_:1}),n[114]||(n[114]=o(", the plugin uses the IP address or domain name defined with this property and the port defined with the ")),t(e,{name:"httpsProxyPort"}),n[115]||(n[115]=o(" property. "))]),l("li",null,[n[119]||(n[119]=o(" If the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[116]||(n[116]=[o("https.proxyHost")])),_:1})]),_:1}),n[120]||(n[120]=o(" network property is not ")),t(s,null,{default:r(()=>n[117]||(n[117]=[o("null")])),_:1}),n[121]||(n[121]=o(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[118]||(n[118]=[o("https.proxyPort")])),_:1})]),_:1}),n[122]||(n[122]=o(" network property. "))])])]),l("li",null,[n[138]||(n[138]=o(" The distribution download URL uses the ")),t(s,null,{default:r(()=>n[125]||(n[125]=[o("http")])),_:1}),n[139]||(n[139]=o(" protocol: ")),l("ol",null,[l("li",null,[n[127]||(n[127]=o(" If the ")),t(e,{name:"httpProxyHost"}),n[128]||(n[128]=o(" property is not ")),t(s,null,{default:r(()=>n[126]||(n[126]=[o("null")])),_:1}),n[129]||(n[129]=o(", the plugin uses the IP address or domain name defined with this property and the port defined with the ")),t(e,{name:"httpProxyPort"}),n[130]||(n[130]=o(" property. "))]),l("li",null,[n[134]||(n[134]=o(" If the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[131]||(n[131]=[o("http.proxyHost")])),_:1})]),_:1}),n[135]||(n[135]=o(" network property is not ")),t(s,null,{default:r(()=>n[132]||(n[132]=[o("null")])),_:1}),n[136]||(n[136]=o(", the plugin uses the IP address or domain name defined with this property and the port defined with the VM ")),t(i,null,{default:r(()=>[t(s,null,{default:r(()=>n[133]||(n[133]=[o("http.proxyPort")])),_:1})]),_:1}),n[137]||(n[137]=o(" network property. "))])])])])])])}}});export{lt as default};
