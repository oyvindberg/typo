"use strict";(self.webpackChunksite=self.webpackChunksite||[]).push([[515],{3905:(e,n,t)=>{t.d(n,{Zo:()=>p,kt:()=>y});var s=t(7294);function r(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function o(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);n&&(s=s.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,s)}return t}function a(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?o(Object(t),!0).forEach((function(n){r(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):o(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function i(e,n){if(null==e)return{};var t,s,r=function(e,n){if(null==e)return{};var t,s,r={},o=Object.keys(e);for(s=0;s<o.length;s++)t=o[s],n.indexOf(t)>=0||(r[t]=e[t]);return r}(e,n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(s=0;s<o.length;s++)t=o[s],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(r[t]=e[t])}return r}var d=s.createContext({}),l=function(e){var n=s.useContext(d),t=n;return e&&(t="function"==typeof e?e(n):a(a({},n),e)),t},p=function(e){var n=l(e.components);return s.createElement(d.Provider,{value:n},e.children)},c="mdxType",u={inlineCode:"code",wrapper:function(e){var n=e.children;return s.createElement(s.Fragment,{},n)}},m=s.forwardRef((function(e,n){var t=e.components,r=e.mdxType,o=e.originalType,d=e.parentName,p=i(e,["components","mdxType","originalType","parentName"]),c=l(t),m=r,y=c["".concat(d,".").concat(m)]||c[m]||u[m]||o;return t?s.createElement(y,a(a({ref:n},p),{},{components:t})):s.createElement(y,a({ref:n},p))}));function y(e,n){var t=arguments,r=n&&n.mdxType;if("string"==typeof e||r){var o=t.length,a=new Array(o);a[0]=m;var i={};for(var d in n)hasOwnProperty.call(n,d)&&(i[d]=n[d]);i.originalType=e,i[c]="string"==typeof e?e:r,a[1]=i;for(var l=2;l<o;l++)a[l]=t[l];return s.createElement.apply(null,a)}return s.createElement.apply(null,t)}m.displayName="MDXCreateElement"},6976:(e,n,t)=>{t.r(n),t.d(n,{assets:()=>d,contentTitle:()=>a,default:()=>u,frontMatter:()=>o,metadata:()=>i,toc:()=>l});var s=t(7462),r=(t(7294),t(3905));const o={title:"Generated code for relations"},a=void 0,i={unversionedId:"what-is/relations",id:"what-is/relations",title:"Generated code for relations",description:"Typo takes the chore out of writing repository code to access your PostgreSQL database relations by automatically",source:"@site/docs/what-is/relations.md",sourceDirName:"what-is",slug:"/what-is/relations",permalink:"/typo/docs/what-is/relations",draft:!1,tags:[],version:"current",frontMatter:{title:"Generated code for relations"},sidebar:"tutorialSidebar",previous:{title:"SQL is king!",permalink:"/typo/docs/what-is/sql-is-king"},next:{title:"SQL DSL",permalink:"/typo/docs/what-is/dsl"}},d={},l=[{value:"Tables",id:"tables",level:2},{value:"Primary Key Types",id:"primary-key-types",level:3},{value:"Row Class",id:"row-class",level:3},{value:"Repository interface",id:"repository-interface",level:3},{value:"Simplified Insertion",id:"simplified-insertion",level:3},{value:"Readonly repositories",id:"readonly-repositories",level:3},{value:"Views",id:"views",level:2}],p={toc:l},c="wrapper";function u(e){let{components:n,...t}=e;return(0,r.kt)(c,(0,s.Z)({},p,t,{components:n,mdxType:"MDXLayout"}),(0,r.kt)("p",null,"Typo takes the chore out of writing repository code to access your PostgreSQL database relations by automatically\ngenerating it for you. Whether you're dealing with tables or views, Typo's generated code simplifies database\ninteraction, saving you time and effort. "),(0,r.kt)("p",null,"It incorporates crucial database information such as strongly typed primary key types, comments, check constraints, and foreign keys.\nThis not only simplifies your code but also empowers developers with a deep understanding of the database structure. "),(0,r.kt)("p",null,"With Typo, you can spend more time focusing on your application logic and less time on repetitive database access code, all while\nhaving the tools to perform CRUD operations efficiently. "),(0,r.kt)("h2",{id:"tables"},"Tables"),(0,r.kt)("p",null,"For tables, Typo generates comprehensive repository code. Take for instance this table: "),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-sql"},"create table address\n(\n    addressid       serial\n        constraint \"PK_Address_AddressID\" primary key,\n    addressline1    varchar(60)                          not null,\n    addressline2    varchar(60),\n    city            varchar(30)                          not null,\n    stateprovinceid integer                              not null\n        constraint \"FK_Address_StateProvince_StateProvinceID\" references stateprovince,\n    postalcode      varchar(15)                          not null,\n    spatiallocation bytea,\n    rowguid         uuid      default uuid_generate_v1() not null,\n    modifieddate    timestamp default now()              not null\n);\n\ncomment on table address is 'Street address information for customers, employees, and vendors.';\ncomment on column address.addressid is 'Primary key for Address records.';\ncomment on column address.addressline1 is 'First street address line.';\ncomment on column address.addressline2 is 'Second street address line.';\ncomment on column address.city is 'Name of the city.';\ncomment on column address.stateprovinceid is 'Unique identification number for the state or province. Foreign key to StateProvince table.';\ncomment on column address.postalcode is 'Postal code for the street address.';\ncomment on column address.spatiallocation is 'Latitude and longitude of this address.';\n")),(0,r.kt)("h3",{id:"primary-key-types"},"Primary Key Types"),(0,r.kt)("p",null,"Typo generates strongly typed ",(0,r.kt)("a",{parentName:"p",href:"/typo/docs/type-safety/id-types"},"primary key types"),", ensuring correct usage and enforcement of data integrity.\nYou also get types for composite primary keys."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"/** Type for the primary key of table `person.address` */\ncase class AddressId(value: Int) extends AnyVal\nobject AddressId {\n  // ...instances\n}\n")),(0,r.kt)("h3",{id:"row-class"},"Row Class"),(0,r.kt)("p",null,"You'll receive a meticulously crafted row case class that precisely mirrors your table structure.\nThe field names are beautified (see ",(0,r.kt)("a",{parentName:"p",href:"/typo/docs/customization/customize-naming"},"Customize naming")," for how to tweak naming),\nand the corresponding types are correct."),(0,r.kt)("p",null,"Relevant column comments, check constraints and foreign keys are clearly marked,\nmaking it easy to understand the purpose of every column."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"import adventureworks.customtypes.TypoLocalDateTime\nimport adventureworks.person.address.AddressId\nimport adventureworks.person.stateprovince.StateprovinceId\nimport java.util.UUID\n\ncase class AddressRow(\n  /** Primary key for Address records. */\n  addressid: AddressId,\n  /** First street address line. */\n  addressline1: /* max 60 chars */ String,\n  /** Second street address line. */\n  addressline2: Option[/* max 60 chars */ String],\n  /** Name of the city. */\n  city: /* max 30 chars */ String,\n  /** Unique identification number for the state or province. Foreign key to StateProvince table.\n      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */\n  stateprovinceid: StateprovinceId,\n  /** Postal code for the street address. */\n  postalcode: /* max 15 chars */ String,\n  /** Latitude and longitude of this address. */\n  spatiallocation: Option[Array[Byte]],\n  rowguid: UUID,\n  modifieddate: TypoLocalDateTime\n)\n")),(0,r.kt)("h3",{id:"repository-interface"},"Repository interface"),(0,r.kt)("p",null,"Typo generates a repository interface tailored to your table, providing methods efficient CRUD (Create, Read, Update,\nDelete) operations at your fingertips."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"import adventureworks.person.address.{AddressFields, AddressRow}\nimport java.sql.Connection\nimport typo.dsl.{DeleteBuilder, SelectBuilder, UpdateBuilder}\n\ntrait AddressRepo {\n  def delete(addressid: AddressId)(implicit c: Connection): Boolean\n  def delete: DeleteBuilder[AddressFields, AddressRow]\n  def insert(unsaved: AddressRow)(implicit c: Connection): AddressRow\n  def insert(unsaved: AddressRowUnsaved)(implicit c: Connection): AddressRow\n  def select: SelectBuilder[AddressFields, AddressRow]\n  def selectAll(implicit c: Connection): List[AddressRow]\n  def selectById(addressid: AddressId)(implicit c: Connection): Option[AddressRow]\n  def selectByIds(addressids: Array[AddressId])(implicit c: Connection): List[AddressRow]\n  def update(row: AddressRow)(implicit c: Connection): Boolean\n  def update: UpdateBuilder[AddressFields, AddressRow]\n  def upsert(unsaved: AddressRow)(implicit c: Connection): AddressRow\n}\n")),(0,r.kt)("h3",{id:"simplified-insertion"},"Simplified Insertion"),(0,r.kt)("p",null,"Since Typo understands auto-increment IDs and default values, you can effortlessly insert new rows\nwithout the need for complex code. A special structure is provided for creating unsaved rows with default values."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"import adventureworks.customtypes.Defaulted\n\n/** This class corresponds to a row in table `person.address` which has not been persisted yet */\ncase class AddressRowUnsaved(\n  /** First street address line. */\n  addressline1: /* max 60 chars */ String,\n  /** Second street address line. */\n  addressline2: Option[/* max 60 chars */ String],\n  /** Name of the city. */\n  city: /* max 30 chars */ String,\n  /** Unique identification number for the state or province. Foreign key to StateProvince table.\n      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */\n  stateprovinceid: StateprovinceId,\n  /** Postal code for the street address. */\n  postalcode: /* max 15 chars */ String,\n  /** Latitude and longitude of this address. */\n  spatiallocation: Option[Array[Byte]],\n  /** Default: nextval('person.address_addressid_seq'::regclass)\n      Primary key for Address records. */\n  addressid: Defaulted[AddressId] = Defaulted.UseDefault,\n  /** Default: uuid_generate_v1() */\n  rowguid: Defaulted[UUID] = Defaulted.UseDefault,\n  /** Default: now() */\n  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault\n)\n")),(0,r.kt)("h3",{id:"readonly-repositories"},"Readonly repositories"),(0,r.kt)("p",null,"If you have a bunch of tables you just want to read, you can ",(0,r.kt)("a",{parentName:"p",href:"/typo/docs/customization/overview"},"customize"),"\nthe repositories to only expose read methods."),(0,r.kt)("h2",{id:"views"},"Views"),(0,r.kt)("p",null,"Typo also excels at simplifying code generation for views.\nWhile less code is generated for views, it's still designed to make your life easier"),(0,r.kt)("p",null,"So given the following view:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-sql"},"create view vemployee\n            (businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, phonenumber, phonenumbertype,\n             emailaddress, emailpromotion, addressline1, addressline2, city, stateprovincename, postalcode,\n             countryregionname, additionalcontactinfo)\nas\nSELECT e.businessentityid,\n       p.title,\n       p.firstname,\n       p.middlename,\n       p.lastname,\n       p.suffix,\n       e.jobtitle,\n       pp.phonenumber,\n       pnt.name AS phonenumbertype,\n       ea.emailaddress,\n       p.emailpromotion,\n       a.addressline1,\n       a.addressline2,\n       a.city,\n       sp.name  AS stateprovincename,\n       a.postalcode,\n       cr.name  AS countryregionname,\n       p.additionalcontactinfo\nFROM humanresources.employee e\n         JOIN person.person p ON p.businessentityid = e.businessentityid\n         JOIN person.businessentityaddress bea ON bea.businessentityid = e.businessentityid\n         JOIN person.address a ON a.addressid = bea.addressid\n         JOIN person.stateprovince sp ON sp.stateprovinceid = a.stateprovinceid\n         JOIN person.countryregion cr ON cr.countryregioncode::text = sp.countryregioncode::text\n         LEFT JOIN person.personphone pp ON pp.businessentityid = p.businessentityid\n         LEFT JOIN person.phonenumbertype pnt ON pp.phonenumbertypeid = pnt.phonenumbertypeid\n         LEFT JOIN person.emailaddress ea ON p.businessentityid = ea.businessentityid;\n\n")),(0,r.kt)("p",null,"You get the following row type:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"import adventureworks.customtypes.TypoXml\nimport adventureworks.person.businessentity.BusinessentityId\nimport adventureworks.public.Name\nimport adventureworks.public.Phone\n\ncase class VemployeeViewRow(\n  /** Points to [[person.person.PersonRow.businessentityid]] */\n  businessentityid: BusinessentityId,\n  /** Points to [[person.person.PersonRow.title]] */\n  title: /* max 8 chars */ String,\n  /** Points to [[person.person.PersonRow.firstname]] */\n  firstname: Name,\n  /** Points to [[person.person.PersonRow.middlename]] */\n  middlename: Name,\n  /** Points to [[person.person.PersonRow.lastname]] */\n  lastname: Name,\n  /** Points to [[person.person.PersonRow.suffix]] */\n  suffix: /* max 10 chars */ String,\n  /** Points to [[employee.EmployeeRow.jobtitle]] */\n  jobtitle: /* max 50 chars */ String,\n  /** Points to [[person.personphone.PersonphoneRow.phonenumber]] */\n  phonenumber: Option[Phone],\n  phonenumbertype: Option[Name],\n  /** Points to [[person.emailaddress.EmailaddressRow.emailaddress]] */\n  emailaddress: Option[/* max 50 chars */ String],\n  /** Points to [[person.person.PersonRow.emailpromotion]] */\n  emailpromotion: Int,\n  /** Points to [[person.address.AddressRow.addressline1]] */\n  addressline1: /* max 60 chars */ String,\n  /** Points to [[person.address.AddressRow.addressline2]] */\n  addressline2: /* max 60 chars */ String,\n  /** Points to [[person.address.AddressRow.city]] */\n  city: /* max 30 chars */ String,\n  stateprovincename: Name,\n  /** Points to [[person.address.AddressRow.postalcode]] */\n  postalcode: /* max 15 chars */ String,\n  countryregionname: Name,\n  /** Points to [[person.person.PersonRow.additionalcontactinfo]] */\n  additionalcontactinfo: TypoXml\n)\n")),(0,r.kt)("p",null,"And this repository interface, focused on selecting rows from the view."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"import adventureworks.humanresources.vemployee.{VemployeeViewFields, VemployeeViewRow}\n\ntrait VemployeeViewRepo {\n  def select: SelectBuilder[VemployeeViewFields, VemployeeViewRow]\n  def selectAll(implicit c: Connection): List[VemployeeViewRow]\n}\n")))}u.isMDXComponent=!0}}]);