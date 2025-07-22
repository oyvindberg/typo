import React from "react";
import CodeBlock from "@theme/CodeBlock";
import styles from "./styles.module.css";

export default function DSLShowcase() {
    return (
        <section className={styles.dsl}>
            <div className="container">
                <div className={styles.content}>
                    <div className={styles.textContent}>
                        <h2 className={styles.title}>
                            A DSL So Intuitive, <br />
                            It Feels Like <span className={styles.highlight}>Cheating</span>
                        </h2>
                        <p className={styles.description}>
                            Write 80% of your queries without touching SQL. The DSL is type-safe, 
                            composable, and works identically with real databases and in-memory stubs.
                        </p>
                        
                        <div className={styles.features}>
                            <div className={styles.feature}>
                                <h4>✓ Full Type Safety</h4>
                                <p>Every column, every operation, every join condition is checked at compile time.</p>
                            </div>
                            <div className={styles.feature}>
                                <h4>✓ Intelligent Auto-complete</h4>
                                <p>Your IDE knows exactly what columns are available at every step.</p>
                            </div>
                            <div className={styles.feature}>
                                <h4>✓ Composable Queries</h4>
                                <p>Build complex queries by combining simple, reusable parts.</p>
                            </div>
                        </div>
                    </div>
                    
                    <div className={styles.codeShowcase}>
                        <div className={styles.codeExample}>
                            <h4>Simple Yet Powerful</h4>
                            <CodeBlock language="scala">
{`// Find users with recent orders
val activeUsers = userRepo.select
  .where(_.verified === true)
  .where(_.createdAt > oneMonthAgo)
  .orderBy(_.lastName.asc, _.firstName.asc)
  .limit(100)`}
                            </CodeBlock>
                        </div>
                        
                        <div className={styles.codeExample}>
                            <h4>Type-Safe Joins</h4>
                            <CodeBlock language="scala">
{`// Join with compile-time safety
val userOrders = userRepo.select
  .join(orderRepo.select)
  .on(_.id, _.userId)  // Types must match!
  .where { case (user, order) => 
    user.country === "US" and
    order.total > 100
  }
  .map { case (user, order) =>
    UserOrderSummary(user.name, order.total)
  }`}
                            </CodeBlock>
                        </div>
                        
                        <div className={styles.codeExample}>
                            <h4>Complex Conditions</h4>
                            <CodeBlock language="scala">
{`// Readable, composable predicates
productRepo.select.where { p =>
  (p.category === "Electronics" or 
   p.category === "Computers") and
  p.price.between(100, 1000) and
  p.inStock === true and
  p.name.like("%Pro%")
}`}
                            </CodeBlock>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}