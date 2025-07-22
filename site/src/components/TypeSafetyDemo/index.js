import React from "react";
import CodeBlock from "@theme/CodeBlock";
import styles from "./styles.module.css";

export default function TypeSafetyDemo() {
    return (
        <section className={styles.demo}>
            <div className="container">
                <h2 className={styles.demoTitle}>See Type Safety in Action</h2>
                <div className={styles.demoContainer}>
                    <div className={styles.sqlSide}>
                        <h3>PostgreSQL Schema</h3>
                        <div className={styles.codeWrapper}>
                            <CodeBlock language="sql">
{`CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  name VARCHAR(100)
);

CREATE TABLE posts (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL 
    REFERENCES users(id),
  title VARCHAR(200) NOT NULL,
  created_at TIMESTAMP DEFAULT NOW()
);`}
                            </CodeBlock>
                        </div>
                    </div>
                    <div className={styles.arrow}>
                        <span>→</span>
                        <span className={styles.arrowText}>Typo generates</span>
                    </div>
                    <div className={styles.scalaSide}>
                        <h3>Type-Safe Scala Code</h3>
                        <div className={styles.codeWrapper}>
                            <CodeBlock language="scala">
{`// Strongly typed ID types
case class UserId(value: Long)
case class PostId(value: Long)

// Row classes with proper types
case class UserRow(
  id: UserId,
  email: String,
  name: Option[String]
)

case class PostRow(
  id: PostId,
  userId: UserId,  // Not just Long!
  title: String,
  createdAt: TypoLocalDateTime
)

// Type-safe repositories
UserRepo.selectById(UserId(42))
PostRepo.selectByUserId(userId)`}
                            </CodeBlock>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}