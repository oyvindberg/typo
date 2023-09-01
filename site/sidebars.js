// @ts-check

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
    tutorialSidebar: [
        {
            type: "category",
            label: "What is typo?",
            collapsible: true,
            collapsed: false,
            items: [
                {type: "doc", id: "readme"},
                {type: "doc", id: "what-is/sql-is-king"},
                {type: "doc", id: "what-is/relations"},
                {type: "doc", id: "what-is/dsl"},
            ],
        },
        {type: "doc", id: "setup"},
        {
            type: "category",
            label: "Type-safety",
            collapsible: true,
            collapsed: false,
            items: [
                {type: "doc", id: "type-safety/id-types"},
                {type: "doc", id: "type-safety/string-enums"},
                {type: "doc", id: "type-safety/domains"},
                {type: "doc", id: "type-safety/arrays"},
                {type: "doc", id: "type-safety/date-time"},
                {type: "doc", id: "type-safety/defaulted-types"},
                {type: "doc", id: "type-safety/typo-types"},
                {type: "doc", id: "type-safety/type-flow"},
                {type: "doc", id: "type-safety/user-selected-types"},
            ],
        },
        {
            type: "category",
            label: "Other features",
            collapsible: true,
            collapsed: false,
            items: [
                {type: "doc", id: "other-features/scala-js-ready"},
                {type: "doc", id: "other-features/testing-with-stubs"},
                {type: "doc", id: "other-features/testing-with-random-values"},
                {type: "doc", id: "other-features/json"},
                {type: "doc", id: "other-features/faster-compilation"},
                {type: "doc", id: "other-features/flexible"},
                {type: "doc", id: "other-features/clickable-links"},
                {type: "doc", id: "other-features/constraints"},
            ],
        },
        {type: "doc", id: "customization"},
        {type: "doc", id: "patterns"}
    ]
};

module.exports = sidebars;
