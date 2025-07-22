import React from "react";
import CodeBlock from "@theme/CodeBlock";
import styles from "./styles.module.css";

export default function TestingShowcase() {
    return (
        <section className={styles.testing}>
            <div className="container">
                <div className={styles.header}>
                    <h2 className={styles.title}>
                        Two Testing Superpowers in One Tool
                    </h2>
                    <p className={styles.subtitle}>
                        Choose your testing approach: lightning-fast in-memory stubs for rapid iteration, 
                        or database-backed tests with automatic test data generation that eliminates the 
                        "lingering state" problem every team struggles with.
                    </p>
                </div>

                <div className={styles.showcaseGrid}>
                    <div className={styles.showcaseItem}>
                        <div className={styles.showcaseHeader}>
                            <span className={styles.showcaseNumber}>01</span>
                            <h3>In-Memory Testing That Actually Works</h3>
                        </div>
                        <p className={styles.showcaseDescription}>
                            Unlike other ORMs, Typo's generated stubs aren't just dumb maps. 
                            They support the full DSL, including joins, filtering, and ordering.
                        </p>
                        <CodeBlock language="scala" className={styles.showcaseCode}>
{`// No database connection needed!
class UserServiceTest extends FunSuite {
  test("find active premium users") {
    val users = UserRepoMock.empty
    val subs = SubscriptionRepoMock.empty
    
    // Insert test data
    users.insertMany(testUsers)
    subs.insertMany(testSubscriptions)
    
    // Your actual business logic works!
    val premiumUsers = users.select
      .where(_.active === true)
      .join(subs.select)
      .on((u, s) => u.id === s.userId)
      .where(_._2.plan === "premium")
      .map(_._1)
    
    // Runs instantly!
    assert(premiumUsers.size == 3)
  }
}`}
                        </CodeBlock>
                    </div>

                    <div className={styles.showcaseItem}>
                        <div className={styles.showcaseHeader}>
                            <span className={styles.showcaseNumber}>02</span>
                            <h3>Database Tests with Zero Background State</h3>
                        </div>
                        <p className={styles.showcaseDescription}>
                            <code>TestInsert</code> generates random data for all fields, but you override exactly 
                            what you care about. You're only forced to provide foreign keys, so you naturally 
                            build valid data graphs with the right shapes.
                        </p>
                        <CodeBlock language="scala" className={styles.showcaseCode}>
{`// All data is random except what you specify
test("user with specific email domain") {
  val insert = new TestInsert(new Random(0))
  
  // Random name, phone, etc. Only email matters
  val user = insert.user(email = "test@company.com")
  
  // Random category data, only need the ID
  val category = insert.category()
  
  // Random product data, but specific price
  val product = insert.product(
    categoryId = category.id,  // forced - builds valid graph
    price = BigDecimal("19.99")  // only what we care about
  )
  
  // Test the specific scenario
  val order = orderService.createOrder(user.id, product.id)
  
  // No accidental dependencies on random data!
  assert(order.userEmail == "test@company.com")
  assert(order.total == BigDecimal("19.99"))
}

// Valid foreign keys enforced!
// Everything else is random!
// Test exactly what matters!`}
                        </CodeBlock>
                    </div>

                    <div className={styles.showcaseItem}>
                        <div className={styles.showcaseHeader}>
                            <span className={styles.showcaseNumber}>03</span>
                            <h3>Property-Based Testing Support</h3>
                        </div>
                        <p className={styles.showcaseDescription}>
                            Combine Typo with ScalaCheck for powerful property-based tests. 
                            Generate arbitrary valid data that respects your schema constraints.
                        </p>
                        <CodeBlock language="scala" className={styles.showcaseCode}>
{`// Typo + ScalaCheck = ❤️
forAll(genValidUser) { user =>
  val repo = UserRepoMock.empty
  repo.insert(user)
  
  // Properties always hold
  repo.selectById(user.id) should contain(user)
  repo.selectAll.size shouldBe 1
  
  // Even complex properties!
  repo.select
    .where(_.email === user.email)
    .head.id shouldBe user.id
}`}
                        </CodeBlock>
                    </div>
                </div>

                <div className={styles.stats}>
                    <div className={styles.stat}>
                        <div className={styles.statNumber}>1000x</div>
                        <div className={styles.statLabel}>Faster than DB tests</div>
                    </div>
                    <div className={styles.stat}>
                        <div className={styles.statNumber}>100%</div>
                        <div className={styles.statLabel}>DSL compatibility</div>
                    </div>
                    <div className={styles.stat}>
                        <div className={styles.statNumber}>0</div>
                        <div className={styles.statLabel}>External dependencies</div>
                    </div>
                </div>
            </div>
        </section>
    );
}