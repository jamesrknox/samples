# Introduction

This example contains the following :

* EventFlow hello world application that simply writes input data to log adapter

![Application](https://raw.githubusercontent.com/plord12/samples/master/fragments/eventflow/helloworld/src/site/resources/images/HelloWorld.png)

* Junit test case

* Codeline structure

```
helloworld
├── src
│   ├── main
│   │   ├── configurations
│   │   ├── eventflow
│   │   │   └── com
│   │   │       └── tibco
│   │   │           └── ep
│   │   │               └── samples
│   │   │                   └── helloworld
│   │   │                       ├── helloworld.sbapp
│   │   │                       └── helloworld.sblayout
│   │   ├── java
│   │   │   └── com
│   │   │       └── tibco
│   │   │           └── ep
│   │   │               └── samples
│   │   │                   └── helloworld
│   │   └── resources
│   ├── site
│   │   ├── markdown
│   │   │   ├── index.md
│   │   │   └── using.md
│   │   ├── resources
│   │   │   └── images
│   │   │       └── HelloWorld.png
│   │   └── site.xml
│   └── test
│       ├── configurations
│       ├── java
│       │   └── com
│       │       └── tibco
│       │           └── ep
│       │               └── samples
│       │                   └── helloworld
│       │                       └── TestCase.java
│       └── resources
└── pom.xml
```