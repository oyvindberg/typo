"use strict";(self.webpackChunksite=self.webpackChunksite||[]).push([[12],{3905:(e,t,n)=>{n.d(t,{Zo:()=>c,kt:()=>m});var r=n(7294);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function a(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?a(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):a(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,o=function(e,t){if(null==e)return{};var n,r,o={},a=Object.keys(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var l=r.createContext({}),p=function(e){var t=r.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},c=function(e){var t=p(e.components);return r.createElement(l.Provider,{value:t},e.children)},d="mdxType",u={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},f=r.forwardRef((function(e,t){var n=e.components,o=e.mdxType,a=e.originalType,l=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),d=p(n),f=o,m=d["".concat(l,".").concat(f)]||d[f]||u[f]||a;return n?r.createElement(m,i(i({ref:t},c),{},{components:n})):r.createElement(m,i({ref:t},c))}));function m(e,t){var n=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=n.length,i=new Array(a);i[0]=f;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[d]="string"==typeof e?e:o,i[1]=s;for(var p=2;p<a;p++)i[p]=n[p];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}f.displayName="MDXCreateElement"},6861:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>l,contentTitle:()=>i,default:()=>u,frontMatter:()=>a,metadata:()=>s,toc:()=>p});var r=n(7462),o=(n(7294),n(3905));const a={title:"Introduction to Typo"},i=void 0,s={unversionedId:"readme",id:"readme",title:"Introduction to Typo",description:"Typo is not just another source code generator; it's your trusted partner in database development. By harnessing the",source:"@site/docs/readme.md",sourceDirName:".",slug:"/",permalink:"/typo/docs/",draft:!1,tags:[],version:"current",frontMatter:{title:"Introduction to Typo"},sidebar:"tutorialSidebar",next:{title:"SQL is king!",permalink:"/typo/docs/what-is/sql-is-king"}},l={},p=[{value:"The Motivation Behind Typo",id:"the-motivation-behind-typo",level:2},{value:"Building Safer Systems",id:"building-safer-systems",level:3},{value:"Revolutionizing the SQL to JVM Workflow",id:"revolutionizing-the-sql-to-jvm-workflow",level:3},{value:"Example video",id:"example-video",level:3},{value:"Types of Database Interactions",id:"types-of-database-interactions",level:3}],c={toc:p},d="wrapper";function u(e){let{components:t,...n}=e;return(0,o.kt)(d,(0,r.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"Typo is not just another source code generator; it's your trusted partner in database development. By harnessing the\npower of PostgreSQL schema definitions and your SQL code, Typo creates a seamless bridge between your database and your\nScala code, all while putting type-safety and developer experience (DX) front and center."),(0,o.kt)("h2",{id:"the-motivation-behind-typo"},"The Motivation Behind Typo"),(0,o.kt)("h3",{id:"building-safer-systems"},"Building Safer Systems"),(0,o.kt)("p",null,"In the world of software development, we rely on the compiler to catch errors and ensure the correctness of our code.\nBut what happens when we venture into the unpredictable realm of external data sources, like databases?"),(0,o.kt)("p",null,"Typo's core motivation is to bring contract-driven development to the database layer. Just as generating code from\nOpenAPI definitions ensures the correctness of your HTTP layer, Typo aims to deliver the same level of safety for\ndatabase interactions. It achieves this by generating precise and correct code for your tables, views, and queries, all\nguided by PostgreSQL metadata tables."),(0,o.kt)("h3",{id:"revolutionizing-the-sql-to-jvm-workflow"},"Revolutionizing the SQL to JVM Workflow"),(0,o.kt)("p",null,"The conventional workflow for SQL-to-JVM interaction often feels like a labyrinth of manual tasks and repetitive boilerplate:"),(0,o.kt)("ol",null,(0,o.kt)("li",{parentName:"ol"},"You write SQL queries."),(0,o.kt)("li",{parentName:"ol"},"IDEs may struggle to give you proper support while writing, especially if you interpolate and concatenate much"),(0,o.kt)("li",{parentName:"ol"},"Manual mapping of column names or indices to case class field names."),(0,o.kt)("li",{parentName:"ol"},"Manual mapping of column names or indices to case class field types "),(0,o.kt)("li",{parentName:"ol"},"String interpolation and type mapping may trigger cryptic errors for missing typeclass instances "),(0,o.kt)("li",{parentName:"ol"},"The compiler cannot check the mappings, forcing you into writing tests."),(0,o.kt)("li",{parentName:"ol"},"Writing and maintaining tests is tedious, and even slow to run.")),(0,o.kt)("p",null,"But here's the kicker: Whenever you refactor your code, you find yourself revisiting all of these points."),(0,o.kt)("p",null,"Typo changes the game.\nIt streamlines steps 2-7, liberates you from boilerplate, and lets you focus on what truly matters:\nbuilding robust and maintainable database applications."),(0,o.kt)("h3",{id:"example-video"},"Example video"),(0,o.kt)("p",null,"As an example of how typo frees you from these steps, consider this video where you write your SQL in an ",(0,o.kt)("inlineCode",{parentName:"p"},".sql")," file,\nand you see typo generating correct mapping code for it. Much less testing is needed, because the types and names will align perfectly."),(0,o.kt)("video",{width:"100%",controls:!0,src:"https://github.com/oyvindberg/typo/assets/247937/df7c4f2d-b118-4081-81c6-dd03dfe62ee2"}),(0,o.kt)("h3",{id:"types-of-database-interactions"},"Types of Database Interactions"),(0,o.kt)("p",null,"Interactions with the database fall into four distinct categories, each with its unique challenges and requirements:"),(0,o.kt)("ol",null,(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("strong",{parentName:"li"},"CRUD Operations"),": The bread and butter of database interactions. Typo offers ",(0,o.kt)("a",{parentName:"li",href:"/typo/docs/what-is/relations"},"repository methods")," for simple and safe CRUD, and the ",(0,o.kt)("a",{parentName:"li",href:"/typo/docs/what-is/dsl"},"SQL DSL")," can help you do it in batch."),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("strong",{parentName:"li"},"Simple Reads"),": Retrieving data from relations, often involving joins. Typo's ",(0,o.kt)("a",{parentName:"li",href:"/typo/docs/what-is/dsl"},"SQL DSL")," is your go-to for these scenarios."),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("strong",{parentName:"li"},"Complex Reads"),": Aggregations, window functions, and advanced queries. In Typo, these interactions are best handled by ",(0,o.kt)("a",{parentName:"li",href:"/typo/docs/what-is/sql-is-king"},"writing SQL files"),"."),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("strong",{parentName:"li"},"Dynamic Queries"),": Rare and resource-intensive, these custom queries require careful implementation. Typo leaves\nthis space open for you and your ",(0,o.kt)("a",{parentName:"li",href:"/typo/docs/other-features/flexible"},"existing database library"),", as more research is needed in this direction.")),(0,o.kt)("p",null,"Intrigued? Keep exploring to discover how Typo transforms your database development experience."))}u.isMDXComponent=!0}}]);