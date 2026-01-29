# Java HTTP Pub/Sub Framework

A lightweight HTTP server and publish–subscribe framework written in **pure Java**.

This project demonstrates low-level understanding of HTTP, sockets, concurrency, and modular server design — **without using frameworks** such as Spring, Jetty, or external libraries.

---

## ✨ Features

- Custom HTTP server implementation (Java sockets)
- Manual HTTP request parsing
- REST-style GET endpoints
- Publish–Subscribe messaging model
- Thread-safe topic management
- Pluggable processing agents (sum, increment, multiplication, parallel)
- JSON responses (manual serialization)
- No external dependencies

---

## 📦 Project Structure

```
java-http-pubsub-framework/
├─ src/
│  └─ main/
│     └─ java/
│        ├─ server/     # HTTP server & request parser
│        ├─ servlets/   # REST endpoints
│        ├─ pubsub/     # Topics & messages
│        ├─ agents/     # Processing agents
│        └─ demo/       # Application entry point
├─ out/                # Compiled classes (gitignored)
├─ pom.xml             # Maven project descriptor (no dependencies)
└─ README.md
```

---

## 🚀 Running the Project

### Compile

#### Windows (PowerShell)
```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName })
```

#### Linux / macOS
```bash
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
```

### Run
```bash
java -cp out demo.Main
```

The server will start at:

```
http://localhost:8080
```

---

## 🔌 API

### Publish a Value

```
GET /publish?topic=A&message=5
```

(also supports `value` instead of `message`)

**Response**
```json
{
  "status": "ok",
  "topic": "A",
  "value": 5.0
}
```

---

### Topic Statistics

```
GET /stats?topic=SUM
```

**Response**
```json
{
  "topic": "SUM",
  "messages": 1
}
```

---

### List All Topics

```
GET /topics
```

**Response**
```json
{
  "topics": ["A", "B", "SUM", "SUM_PLUS_ONE", "MUL"]
}
```

---

## 🧠 Architecture Overview

- **server**  
  Core HTTP server, socket handling, request parsing, and servlet routing.

- **servlets**  
  REST-style endpoints (`/publish`, `/stats`, `/topics`).

- **pubsub**  
  Topic and message management with thread-safe publish/subscribe logic.

- **agents**  
  Modular message processors (increment, sum, multiplication, parallel execution).

- **demo**  
  Application bootstrap and wiring.

---

## 🛠 Technologies

- Java 21
- Maven (structure only, no dependencies)
- Java Sockets
- Manual JSON serialization

---

## 📌 Notes

This project was intentionally built **without frameworks** to demonstrate a deep understanding of:

- HTTP protocol fundamentals  
- Concurrent programming  
- Server-side architecture  
- Clean modular design  

It is designed as a **portfolio project** rather than a production framework.

---

## 👤 Author

Yonatan Barazani  
Computer Science Student  
GitHub: https://github.com/YonatanBarazani
