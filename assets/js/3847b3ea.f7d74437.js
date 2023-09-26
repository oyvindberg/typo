"use strict";(self.webpackChunksite=self.webpackChunksite||[]).push([[581],{3905:(e,t,r)=>{r.d(t,{Zo:()=>c,kt:()=>y});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function a(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?a(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):a(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function l(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},a=Object.keys(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var s=n.createContext({}),p=function(e){var t=n.useContext(s),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},c=function(e){var t=p(e.components);return n.createElement(s.Provider,{value:t},e.children)},d="mdxType",u={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,a=e.originalType,s=e.parentName,c=l(e,["components","mdxType","originalType","parentName"]),d=p(r),m=o,y=d["".concat(s,".").concat(m)]||d[m]||u[m]||a;return r?n.createElement(y,i(i({ref:t},c),{},{components:r})):n.createElement(y,i({ref:t},c))}));function y(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=r.length,i=new Array(a);i[0]=m;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[d]="string"==typeof e?e:o,i[1]=l;for(var p=2;p<a;p++)i[p]=r[p];return n.createElement.apply(null,i)}return n.createElement.apply(null,r)}m.displayName="MDXCreateElement"},1959:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>u,frontMatter:()=>a,metadata:()=>l,toc:()=>p});var n=r(7462),o=(r(7294),r(3905));const a={title:"Getting started"},i=void 0,l={unversionedId:"setup",id:"setup",title:"Getting started",description:"Database library",source:"@site/docs/setup.md",sourceDirName:".",slug:"/setup",permalink:"/typo/docs/setup",draft:!1,tags:[],version:"current",frontMatter:{title:"Getting started"},sidebar:"tutorialSidebar",previous:{title:"SQL DSL",permalink:"/typo/docs/what-is/dsl"},next:{title:"Limitations",permalink:"/typo/docs/limitations"}},s={},p=[{value:"Database library",id:"database-library",level:2},{value:"Getting started with DSL",id:"getting-started-with-dsl",level:2},{value:"example script",id:"example-script",level:2},{value:"sbt plugin",id:"sbt-plugin",level:2}],c={toc:p},d="wrapper";function u(e){let{components:t,...r}=e;return(0,o.kt)(d,(0,n.Z)({},c,r,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h2",{id:"database-library"},"Database library"),(0,o.kt)("p",null,"Note that you're supposed to ",(0,o.kt)("a",{parentName:"p",href:"/typo/docs/other-features/flexible"},"bring your own database library"),". You choose either anorm or doobie in ",(0,o.kt)("inlineCode",{parentName:"p"},"Options")," (see below),\nand you need to have that added to your build as well."),(0,o.kt)("h2",{id:"getting-started-with-dsl"},"Getting started with DSL"),(0,o.kt)("p",null,"If you want to use the ",(0,o.kt)("a",{parentName:"p",href:"/typo/docs/what-is/dsl"},"SQL DSL"),", you enable it by ",(0,o.kt)("a",{parentName:"p",href:"/typo/docs/customization/overview"},"customizing")," Typo by setting ",(0,o.kt)("inlineCode",{parentName:"p"},"enableDsl = true"),"."),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-scala"},'import typo.Options\nOptions(\n  pkg = "mypkg",\n  dbLib = None,\n  enableDsl = true\n)\n')),(0,o.kt)("p",null,"You also need to add a dependency to your build in that case, which varies by database library:"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"for doobie: ",(0,o.kt)("a",{parentName:"li",href:"https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-doobie"},"https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-doobie")),(0,o.kt)("li",{parentName:"ul"},"for anorm: ",(0,o.kt)("a",{parentName:"li",href:"https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-anorm"},"https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-anorm"))),(0,o.kt)("h2",{id:"example-script"},"example script"),(0,o.kt)("p",null,"The Typo code generator is shipped as a library, the easiest way to get started is something like this scala-cli script:"),(0,o.kt)("p",null,"put it in ",(0,o.kt)("inlineCode",{parentName:"p"},"gen-db.sc")," and run ",(0,o.kt)("inlineCode",{parentName:"p"},"scala-cli gen-db.sc")),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-scala"},'//\n// remember to give the project a github star if you like it <3\n//\n//> using dep "com.olvind.typo::typo:0.3.1"\n//> using scala "3.3.0"\n\nimport typo.*\n\n// adapt to your instance and credentials\nimplicit val c: java.sql.Connection =\n  java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:6432/postgres?user=postgres&password=password")\n\nval options = Options(\n  // customize package name for generated code\n  pkg = "org.foo.generated",\n  // pick your database library\n  dbLib = Some(DbLibName.Anorm),\n  jsonLibs = Nil,\n  // many more possibilities for customization here\n  // ...\n)\n\n// current folder, where you run the script from\nval location = java.nio.file.Path.of(sys.props("user.dir"))\n\n// destination folder. All files in this dir will be overwritten!\nval targetDir = location.resolve("myproject/src/main/scala/org/foo/generated")\n\n// where Typo will look for sql files\nval scriptsFolder = location.resolve("sql")\n\ngenerateFromDb(options, scriptsPaths = List(scriptsFolder))\n  .overwriteFolder(folder = targetDir)\n\n// add changed files to git, so you can keep them under control\n//scala.sys.process.Process(List("git", "add", targetDir.toString)).!!\n')),(0,o.kt)("h2",{id:"sbt-plugin"},"sbt plugin"),(0,o.kt)("p",null,"It's natural to think an sbt plugin would be a good match for Typo. This will likely be added in the future."))}u.isMDXComponent=!0}}]);