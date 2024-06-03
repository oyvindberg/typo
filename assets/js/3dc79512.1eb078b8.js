"use strict";(self.webpackChunksite=self.webpackChunksite||[]).push([[2711],{2544:(e,t,s)=>{s.r(t),s.d(t,{assets:()=>i,contentTitle:()=>o,default:()=>p,frontMatter:()=>d,metadata:()=>r,toc:()=>l});var n=s(4848),a=s(8453);const d={title:"Defaulted types"},o=void 0,r={id:"type-safety/defaulted-types",title:"Defaulted types",description:"An interesting case is how to model inserting rows into tables with default values.",source:"@site/docs/type-safety/defaulted-types.md",sourceDirName:"type-safety",slug:"/type-safety/defaulted-types",permalink:"/typo/docs/type-safety/defaulted-types",draft:!1,unlisted:!1,tags:[],version:"current",frontMatter:{title:"Defaulted types"},sidebar:"tutorialSidebar",previous:{title:"Date/time types",permalink:"/typo/docs/type-safety/date-time"},next:{title:"Typo types",permalink:"/typo/docs/type-safety/typo-types"}},i={},l=[];function c(e){const t={code:"code",p:"p",pre:"pre",...(0,a.R)(),...e.components};return(0,n.jsxs)(n.Fragment,{children:[(0,n.jsxs)(t.p,{children:["An interesting case is how to model inserting rows into tables with default values.\nTypo gives you the option of doing it very explicitly with the ",(0,n.jsx)(t.code,{children:"Defaulted"})," type."]}),"\n",(0,n.jsx)(t.p,{children:"Here it is:"}),"\n",(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-scala",children:"sealed trait Defaulted[+T]\n\nobject Defaulted {\n  case class Provided[T](value: T) extends Defaulted[T]\n  case object UseDefault extends Defaulted[Nothing]\n  \n  /// json instances only. repositories transfer only values you have provided in the insert\n}\n"})}),"\n",(0,n.jsx)(t.p,{children:'It is used only in "Unsaved" row types, which are perfect for talking about not persisted rows.'}),"\n",(0,n.jsx)(t.p,{children:"For instance:"}),"\n",(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-scala",children:"import adventureworks.customtypes.Defaulted\nimport adventureworks.customtypes.TypoBytea\nimport adventureworks.customtypes.TypoLocalDateTime\nimport adventureworks.customtypes.TypoUUID\nimport adventureworks.person.stateprovince.StateprovinceId\nimport adventureworks.person.address.{AddressId, AddressRow}\n\n/** This class corresponds to a row in table `person.address` which has not been persisted yet */\ncase class AddressRowUnsaved(\n  /** First street address line. */\n  addressline1: /* max 60 chars */ String,\n  /** Second street address line. */\n  addressline2: Option[/* max 60 chars */ String],\n  /** Name of the city. */\n  city: /* max 30 chars */ String,\n  /** Unique identification number for the state or province. Foreign key to StateProvince table.\n      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */\n  stateprovinceid: StateprovinceId,\n  /** Postal code for the street address. */\n  postalcode: /* max 15 chars */ String,\n  /** Latitude and longitude of this address. */\n  spatiallocation: Option[TypoBytea],\n  /** Default: nextval('person.address_addressid_seq'::regclass)\n      Primary key for Address records. */\n  addressid: Defaulted[AddressId] = Defaulted.UseDefault,\n  /** Default: uuid_generate_v1() */\n  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,\n  /** Default: now() */\n  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault\n) {\n  def toRow(addressidDefault: => AddressId, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): AddressRow =\n    AddressRow(\n      addressline1 = addressline1,\n      addressline2 = addressline2,\n      city = city,\n      stateprovinceid = stateprovinceid,\n      postalcode = postalcode,\n      spatiallocation = spatiallocation,\n      addressid = addressid match {\n                    case Defaulted.UseDefault => addressidDefault\n                    case Defaulted.Provided(value) => value\n                  },\n      rowguid = rowguid match {\n                  case Defaulted.UseDefault => rowguidDefault\n                  case Defaulted.Provided(value) => value\n                },\n      modifieddate = modifieddate match {\n                       case Defaulted.UseDefault => modifieddateDefault\n                       case Defaulted.Provided(value) => value\n                     }\n    )\n}\nobject AddressRowUnsaved {\n  // also only json instances\n}\n"})}),"\n",(0,n.jsx)(t.p,{children:"The corresponding repo then exposes these methods:"}),"\n",(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-scala",children:"import java.sql.Connection\n\ntrait AddressRepo {\n  def insert(unsaved: AddressRow)(implicit c: Connection): AddressRow\n  def insert(unsaved: AddressRowUnsaved)(implicit c: Connection): AddressRow\n}\n"})})]})}function p(e={}){const{wrapper:t}={...(0,a.R)(),...e.components};return t?(0,n.jsx)(t,{...e,children:(0,n.jsx)(c,{...e})}):c(e)}},8453:(e,t,s)=>{s.d(t,{R:()=>o,x:()=>r});var n=s(6540);const a={},d=n.createContext(a);function o(e){const t=n.useContext(d);return n.useMemo((function(){return"function"==typeof e?e(t):{...t,...e}}),[t,e])}function r(e){let t;return t=e.disableParentContext?"function"==typeof e.components?e.components(a):e.components||a:o(e.components),n.createElement(d.Provider,{value:t},e.children)}}}]);