import{u as f}from"./abRkgXpJ.js";import{A as m,o as p,c as i,l as d,g as h,z as k,i as c,f as x,w as b,d as g,t as M,k as l}from"./0eoLPSdn.js";import{_ as S}from"./DlAUqK2U.js";import{b as $}from"./zck6Z6fF.js";function C(t,a){const{title:e,titleTemplate:o,...s}=t;return f({title:e,titleTemplate:o,_flatMeta:s},{...a,transform(n){const r=m({...n._flatMeta});return delete n._flatMeta,{...n,meta:r}}})}const B={};function j(t,a){return p(),i("h1",null,[d(t.$slots,"default")])}const D=S(B,[["render",j]]),_="https://nodejs.org",N=h({__name:"nodejs-link",props:{path:{default:"/"},label:{default:null}},setup(t){const{t:a}=k(),e=t,o=c(()=>e.label||a("navigation.nodejs.label")),s=c(()=>e.path?`${_}${e.path}`:_);return(n,r)=>{const u=$;return p(),x(u,{href:l(s)},{default:b(()=>[g(M(l(o)),1)]),_:1},8,["href"])}}});export{N as _,D as a,C as u};