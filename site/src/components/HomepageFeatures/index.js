import React from "react";
import clsx from "clsx";
import CodeBlock from "@theme/CodeBlock";
import styles from "./styles.module.css";

const FeatureList = [
    {
        title: "SQL First",
        icon: "📝",
        description: (
            <>
                Write SQL in <code>.sql</code> files with full IDE support. No ORMs or query builders required - just pure SQL with type-safe parameters.
            </>
        ),
        codeExample: `-- users.sql
SELECT * FROM users
WHERE email = :email!`,
        codeLanguage: "sql",
    },
    {
        title: "Type-Safe Everything",
        icon: "🔒",
        description: (
            <>
                Complete type safety from database to application. Foreign keys become specific ID types, nullable columns become <code>Option[T]</code>.
            </>
        ),
        codeExample: `case class UserId(value: Long)
case class User(
  id: UserId,
  email: String,
  name: Option[String]
)`,
    },
    {
        title: "Zero Boilerplate",
        icon: "⚡",
        description: (
            <>
                Generates repositories with CRUD operations, streaming queries, and batch inserts. Works with Anorm, Doobie, and ZIO-JDBC.
            </>
        ),
        codeExample: `UserRepo.insert(user)
UserRepo.selectById(userId)
UserRepo.updateEmail(userId, email)
UserRepo.selectAll.stream`,
    },
    {
        title: "Functional Relational Mapping",
        icon: "🚀",
        description: (
            <>
                Not an ORM - it's FRM. Maps your database schema to immutable case classes without runtime overhead or magic. Fast compilation, zero reflection.
            </>
        ),
        codeExample: `// FRM: Pure functions over data
// vs ORM: Complex object hierarchies
// vs hand-written SQL: Verbose boilerplate
// vs jOOQ: Better testing story`,
        codeLanguage: "scala",
    },
    {
        title: "Stream Like a Pro",
        icon: "🌊",
        description: (
            <>
                Built-in streaming support for large datasets. Process millions of rows without breaking a sweat using your favorite streaming library.
            </>
        ),
        codeExample: `// Stream millions of rows efficiently
UserRepo.selectAll.stream
  .filter(_.active)
  .mapAsync(enrichUser)
  .runWith(Sink.foreach(process))`,
    },
    {
        title: "Powerful Query DSL",
        icon: "🎯",
        description: (
            <>
                Optional type-safe DSL for complex queries. Build dynamic queries with compile-time guarantees and autocomplete support.
            </>
        ),
        codeExample: `select
  .from(users)
  .join(posts).on(_.id, _.userId)
  .where(_.email.like("%@typo%"))
  .orderBy(_.createdAt.desc)
  .limit(10)`,
    },
];

function Feature({icon, title, description, codeExample, codeLanguage = "scala", index}) {
    return (
        <div className={styles.featureCard} style={{"--index": index}}>
            <div>
                <h3 className={styles.featureTitle}>{title}</h3>
                <div className={styles.featureDescription}>{description}</div>
                {codeExample && (
                    <div className={styles.codeExample}>
                        <CodeBlock language={codeLanguage} className={styles.codeBlock}>
                            {codeExample}
                        </CodeBlock>
                    </div>
                )}
            </div>
        </div>
    );
}

export default function HomepageFeatures() {
    return (
        <section className={styles.features}>
            <div className="container">
                <h2 className={styles.featuresTitle}>Why Developers Love Typo</h2>
                <div className={styles.featureGrid}>
                    {FeatureList.map((props, idx) => (
                        <Feature key={idx} index={idx} {...props} />
                    ))}
                </div>
            </div>
        </section>
    );
}
