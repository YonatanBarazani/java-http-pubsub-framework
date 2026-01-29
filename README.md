Java HTTP Pub/Sub Framework

A lightweight HTTP server and publish–subscribe framework written in pure Java, without external libraries.

This project demonstrates low-level understanding of:

HTTP over raw sockets

Request parsing

Concurrency and threading

REST-style API design

Modular server architecture

The entire system is built from scratch for educational and demonstrational purposes.

Features

Custom HTTP server implementation (no frameworks)

Manual HTTP request parsing

URI prefix–based servlet routing

REST-style GET endpoints

Publish–Subscribe messaging system

Thread-safe topic management

Pluggable processing agents:

Sum

Increment

Multiplication

Parallel processing

JSON responses (manual serialization)

Zero external dependencies

Project Structure
java-http-pubsub-framework/
│
├─ src/
│  └─ main/
│     └─ java/
│        ├─ server/        # HTTP server & request parser
│        ├─ servlets/      # REST endpoints
│        ├─ pubsub/        # Topics & messages
│        ├─ agents/        # Processing agents
│        └─ demo/          # Application entry point
│
├─ out/                    # Compiled classes (gitignored)
├─ sources.txt             # Java source list (optional)
└─ README.md

API
Publish a value
GET /publish?topic=A&message=5


(also supports value instead of message)

Response

{"status":"ok","topic":"A","value":5.0}

Topic statistics
GET /stats?topic=SUM


Response

{"topic":"SUM","messages":1}

List all topics
GET /topics


Response

{"topics":["A","B","SUM","SUM_PLUS_ONE","MUL"]}

Running the Project
Requirements

Java 21+

Compile (Windows PowerShell)
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null

$files = Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName }
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllLines("sources.txt", $files, $utf8NoBom)

cmd /c "javac -encoding UTF-8 -d out @sources.txt"

Run
cmd /c "java -cp out demo.Main"


Server will start at:

http://localhost:8080

Example Usage
(iwr "http://localhost:8080/publish?topic=A&message=5" -UseBasicParsing).Content
(iwr "http://localhost:8080/stats?topic=SUM" -UseBasicParsing).Content
(iwr "http://localhost:8080/topics" -UseBasicParsing).Content

Design Notes

No frameworks (Spring, Jetty, etc.) were used intentionally

HTTP handling is implemented directly using sockets

JSON is built manually to keep full control over output

Designed to be readable, modular, and extendable

Servlets can be added or removed without changing the server core

Why This Project

This project was built to demonstrate:

Deep understanding of how HTTP servers work internally

Ability to design clean APIs without relying on libraries

Concurrency and thread management

Practical software architecture in Java

It is suitable both as an academic project and as a portfolio piece.

License

Educational / demonstration use.