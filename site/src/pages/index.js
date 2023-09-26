import React from "react";
import clsx from "clsx";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import Layout from "@theme/Layout";
import HomepageFeatures from "@site/src/components/HomepageFeatures";

import styles from "./index.module.css";

function HomepageHeader() {
    const {siteConfig} = useDocusaurusContext();
    return (
        <header className={clsx("hero hero--primary", styles.heroBanner)}>
            <div className="container">
                <h1 className="hero__title">{siteConfig.title}</h1>
                <p className="hero__subtitle">{siteConfig.tagline}</p>
                <div className="container" style={{display: "flex", justifyContent: "space-around"}}>
                    <div className={styles.buttons}>
                        <Link className="button button--secondary button--lg" to="/docs">
                            Read full introduction
                        </Link>
                    </div>
                    <div className={styles.buttons}>
                        <Link className="button button--secondary button--lg" to="/docs/setup">
                            Get started
                        </Link>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default function Home() {
    const {siteConfig} = useDocusaurusContext();
    return (
        <Layout
            title="Typo"
            description="Typed PostgreSQL boilerplate generation for Scala"
        >
            <div className="mainContainer"></div>

            <HomepageHeader/>
            <main>
                <HomepageFeatures/>
            </main>
        </Layout>
    );
}
