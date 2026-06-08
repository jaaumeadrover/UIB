# 🎓 BSc Computer Science — Year 3 | Semester 1

This repository hosts the academic coursework, laboratory implementations, and core software engineering projects completed during the first semester of the third year of the Computer Science undergraduate curriculum at UIB.

---

## 📋 First Semester Curriculum Index

| Module | Focus Core | Covered Topics & Milestones | Status |
| :--- | :--- | :--- | :--- |
| **[Base de Dades II (BD II)](#-base-de-dades-ii-database-systems-ii)** | Data Architectures | Transaction management, recovery models, and non-relational storage | 🟢 Completed |
| **[Compiladors (Compiler Design)](#-compiladors-compiler-design)** | Language Processing | Lexical analysis, AST parsing, code generation, and LL/LR parsers | 🟢 Completed |
| **[Concurrent (Concurrency)](#-concurrent-concurrency-programming)** | Parallel Software | Threads, semaphores, monitors, actor models, and deadlocks | 🟢 Completed |
| **[Gestió de Projectes (GP)](#-gestió-de-projectes-gp-project-management)** | Software Management | Risk analysis, cost budgeting, scheduling, and agile metrics | 🟢 Completed |
| **[Intel·ligència Artificial (IA)](#-intel·ligència-artificial-ia-artificial-intelligence)** | Heuristics & Search | State-space trees, A* algorithm, logic agents, and game theory | 🟢 Completed |

---

## 🗓️ 1st Semester Modules

### 🗄️ Base de Dades II (Database Systems II)
**Objective:** Implement system-level database logic focusing on optimization, heavy transaction throughput, and data consistency under high concurrency.
* **Key Challenges:** Designing collision-free concurrent transaction schedules and structuring disaster recovery protocols.
* **Approach:** Scripting write-ahead logs (WAL), constructing multi-version concurrency controls (MVCC), index tuning, and building scalable NoSQL/document data architectures.
* **Tech Stack:** PostgreSQL / Oracle, MongoDB, SQL.

---

### ⚙️ Compiladors (Compiler Design)
**Objective:** Construct an end-to-end language compiler engine transforming high-level custom code syntax into validated executable target machine configurations.
* **Key Challenges:** Managing abstract context-free parsing rules and generating clean, un-bloated assembly or intermediate instructions.
* **Approach:** Writing custom lexical analyzers (scanners), Abstract Syntax Tree (AST) token generation, symbol table lookups, checking type-scoping bounds, and compiling through LL/LR parsers.
* **Tech Stack:** Java / C++, ANTLR, Lex / Yacc.

---

### 🧵 Concurrent (Concurrency Programming)
**Objective:** Manage simultaneous software execution execution loops running safely on multi-core computing nodes without data corruption or memory leaks.
* **Key Challenges:** Debugging race conditions, preventing execution locks, and managing shared data resources.
* **Approach:** Thread scheduling routines, configuring hardware-level Semaphores, designing structural thread Monitors, implementing asynchronous Message Passing loops, and managing deadlock resolution trees.
* **Tech Stack:** Java (Concurrency Utilities) / C (POSIX Threads).

---

### 📊 Gestió de Projectes (GP) (Project Management)
**Objective:** Structure, estimate, budget, and manage complex software engineering software releases matching real-world target delivery metrics.
* **Key Challenges:** Balancing multi-variable cost allocations against scope creep and team velocity bottlenecks.
* **Approach:** Designing formal Work Breakdown Structures (WBS), optimizing path dependencies (CPM/PERT maps), implementing risk matrix calculations, and organizing Agile Scrum dashboards.
* **Tech Stack:** Jira / Trello, MS Project, Git.

---

### 🤖 Intel·ligència Artificial (IA) (Artificial Intelligence)
**Objective:** Design intelligent software agents capable of navigating highly non-deterministic state maps to arrive at optimal problem resolutions.
* **Key Challenges:** Preventing exponential memory explosion inside deep search spaces and designing non-biased mathematical heuristic weights.
* **Approach:** Constructing state-space search engines, implementing heuristic routing ($A^*$), depth/breadth tree pruning optimizations (Alpha-Beta pruning), minimax adversarial setups, and modeling Constraint Satisfaction Problems (CSP).
* **Tech Stack:** Python / Java.