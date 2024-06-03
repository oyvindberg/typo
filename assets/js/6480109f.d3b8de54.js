"use strict";(self.webpackChunksite=self.webpackChunksite||[]).push([[9571],{5027:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>d,contentTitle:()=>r,default:()=>h,frontMatter:()=>s,metadata:()=>a,toc:()=>l});var i=o(4848),n=o(8453);const s={title:"Hello, ZIO",description:"Typo ported to ZIO",slug:"hello-zio",authors:[{name:"\xd8yvind Raddum Berg",url:"https://github.com/oyvindberg",image_url:"https://github.com/oyvindberg.png"}],tags:["typo","zio"],image:"https://i.imgur.com/mErPwqL.png",hide_table_of_contents:!1},r=void 0,a={permalink:"/typo/blog/hello-zio",source:"@site/blog/2023-11-24-hello-zio.md",title:"Hello, ZIO",description:"Typo ported to ZIO",date:"2023-11-24T00:00:00.000Z",tags:[{label:"typo",permalink:"/typo/blog/tags/typo"},{label:"zio",permalink:"/typo/blog/tags/zio"}],readingTime:1.8,hasTruncateMarker:!0,authors:[{name:"\xd8yvind Raddum Berg",url:"https://github.com/oyvindberg",image_url:"https://github.com/oyvindberg.png",imageURL:"https://github.com/oyvindberg.png"}],frontMatter:{title:"Hello, ZIO",description:"Typo ported to ZIO",slug:"hello-zio",authors:[{name:"\xd8yvind Raddum Berg",url:"https://github.com/oyvindberg",image_url:"https://github.com/oyvindberg.png",imageURL:"https://github.com/oyvindberg.png"}],tags:["typo","zio"],image:"https://i.imgur.com/mErPwqL.png",hide_table_of_contents:!1},unlisted:!1,prevItem:{title:"The compilation cost of implicits",permalink:"/typo/blog/the-cost-of-implicits"}},d={authorsImageUrls:[void 0]},l=[{value:"Never heard of Typo?",id:"never-heard-of-typo",level:3},{value:"Maturity",id:"maturity",level:3},{value:"Implemented missing features in <code>zio-jdbc</code>",id:"implemented-missing-features-in-zio-jdbc",level:3}];function c(e){const t={a:"a",code:"code",h3:"h3",p:"p",...(0,n.R)(),...e.components};return(0,i.jsxs)(i.Fragment,{children:[(0,i.jsxs)(t.p,{children:["Thanks to the efforts of Jules Ivanic in ",(0,i.jsx)(t.a,{href:"https://github.com/oyvindberg/typo/pull/57",children:"PR #57"}),",\nTypo now supports using ",(0,i.jsx)(t.code,{children:"zio-jdbc"})," as a database library."]}),"\n",(0,i.jsx)(t.h3,{id:"never-heard-of-typo",children:"Never heard of Typo?"}),"\n",(0,i.jsxs)(t.p,{children:["You can check out the ",(0,i.jsx)(t.a,{href:"https://oyvindberg.github.io/typo/docs/",children:"introduction"}),".\nEssentially it's a code generator for database access code, which makes PostgreSQL integration type-safe and wonderful to use."]}),"\n",(0,i.jsx)(t.h3,{id:"maturity",children:"Maturity"}),"\n",(0,i.jsxs)(t.p,{children:["Note that ",(0,i.jsx)(t.code,{children:"zio-jdbc"})," is a bit less mature than ",(0,i.jsx)(t.code,{children:"doobie"})," and ",(0,i.jsx)(t.code,{children:"anorm"}),", so it's a bit more likely to be some rough edges.\nIn particular, it handles nullable values ",(0,i.jsx)(t.a,{href:"https://github.com/zio/zio-jdbc/issues/188",children:"imperfectly"}),".\nWe fixed a bunch of issues while working on this PR, so it should be pretty close."]}),"\n",(0,i.jsxs)(t.h3,{id:"implemented-missing-features-in-zio-jdbc",children:["Implemented missing features in ",(0,i.jsx)(t.code,{children:"zio-jdbc"})]}),"\n",(0,i.jsxs)(t.p,{children:[(0,i.jsx)(t.code,{children:"zio-jdbc"})," does not support postgres arrays, and it does not support\nthe ",(0,i.jsx)(t.a,{href:"/docs/other-features/streaming-inserts",children:"COPY API for streaming inserts"}),"."]}),"\n",(0,i.jsx)(t.p,{children:"Typo outputs code which implements both of these features."}),"\n",(0,i.jsxs)(t.p,{children:["This can likely be upstreamed as a postgres integration module in ",(0,i.jsx)(t.code,{children:"zio-jdbc"})," at some point."]})]})}function h(e={}){const{wrapper:t}={...(0,n.R)(),...e.components};return t?(0,i.jsx)(t,{...e,children:(0,i.jsx)(c,{...e})}):c(e)}},8453:(e,t,o)=>{o.d(t,{R:()=>r,x:()=>a});var i=o(6540);const n={},s=i.createContext(n);function r(e){const t=i.useContext(s);return i.useMemo((function(){return"function"==typeof e?e(t):{...t,...e}}),[t,e])}function a(e){let t;return t=e.disableParentContext?"function"==typeof e.components?e.components(n):e.components||n:r(e.components),i.createElement(s.Provider,{value:t},e.children)}}}]);