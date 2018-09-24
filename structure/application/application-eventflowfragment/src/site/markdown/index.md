# Structure : Application

How to structure a maven aggregate project with EventFlow fragment and application archive - EventFlow Fragment.

```
.
├── application-application
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── configurations
│       │   │   ├── app.conf
│       │   │   └── defaultnode.conf
│       │   └── docker
│       │       ├── application
│       │       │   └── Dockerfile
│       │       └── base
│       │           ├── Dockerfile
│       │           └── start-node
│       ├── site
│       │   ├── markdown
│       │   │   ├── index.md
│       │   │   └── using.md
│       │   └── site.xml
│       └── test
│           ├── configurations
│           ├── java
│           │   └── com
│           │       └── tibco
│           │           └── ep
│           │               └── samples
│           │                   └── application
│           └── resources
├── application-eventflowfragment
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── configurations
│       │   ├── eventflow
│       │   │   └── com
│       │   │       └── tibco
│       │   │           └── ep
│       │   │               └── samples
│       │   │                   └── application
│       │   │                       ├── application_eventflowfragment.sbapp
│       │   │                       └── application_eventflowfragment.sblayout
│       │   ├── java
│       │   │   └── com
│       │   │       └── tibco
│       │   │           └── ep
│       │   │               └── samples
│       │   │                   └── application
│       │   └── resources
│       ├── site
│       │   ├── markdown
│       │   │   ├── index.md
│       │   │   └── using.md
│       │   └── site.xml
│       └── test
│           ├── configurations
│           ├── java
│           │   └── com
│           │       └── tibco
│           │           └── ep
│           │               └── samples
│           │                   └── application
│           │                       └── TestCase.java
│           └── resources
└── pom.xml
```
