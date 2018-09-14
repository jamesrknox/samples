# Structure : Application

How to structure a maven aggregate project with EventFlow fragment and application archive.

An maven aggregate project consists of at least one application archive module and at least one fragment.  Contained
in one project allows maven to work out dependencies across maven modules and build and test in the right order.

The directory structure consists of :

* **pom.xml** - Maven Aggregator Project Object Module  that references the modules below
* **[fragment]/pom.xml** - Maven Project Object Module that controls the build, tests, installation and deployment of the project.
* **[fragment]/src/main/eventflow/[package]/** - Directory containing EventFlow applications.
* **[fragment]/src/main/java/[package]/** - Directory containing any Java source.
* **[fragment]/src/main/configurations/** - Directory containing any HOCON configuration files.  These are included in the built fragment.
* **[fragment]/src/main/resources/** - Directory containing any resources files.  These are included in the built fragment.
* **[fragment]/src/site/** - Directory containing any site documentation.  The maven build process can create html from these and publish to a web site.
* **[fragment]/src/test/java/[package]/** - Directory containing any junit test cases
* **[fragment]/src/test/configurations/** - Directory containing any HOCON configuration files used by test cases.  These are *not* included in the built fragment.
* **[fragment]/src/test/resources/** - Directory containing any resources files used by test cases.  These are *not* included in the built fragment.
* **[application]/pom.xml** - Maven Project Object Module that controls the build, tests, installation and deployment of the project.
* **[application]/src/main/configurations/** - Directory containing any HOCON configuration files.  These are included in the built application archive.
* **[application]/src/main/docker/base** - Directory containing docker file to build base image.
* **[application]/src/main/docker/application** - Directory containing docker file to build application image.
* **[application]/src/main/resources/** - Directory containing any resources files.  These are included in the built application archive.
* **[application]/src/site/** - Directory containing any site documentation.  The maven build process can create html from these and publish to a web site.
* **[application]/src/test/java/[package]/** - Directory containing any junit test cases
* **[application]/src/test/configurations/** - Directory containing any HOCON configuration files used by test cases.  These are *not* included in the built fragment.
* **[application]/src/test/resources/** - Directory containing any resources files used by test cases.  These are *not* included in the built fragment.

This sample's structure is shown below :

```
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