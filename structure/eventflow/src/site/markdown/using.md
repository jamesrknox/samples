# Using

This component is available via maven.  Include in your pom.xml file the
following :-

```xml
    <project>
        ...
        <dependencies>
            <dependency>
                <groupId>com.tibco.ep.samples</groupId>
                <artifactId>helloworld</artifactId>
                <type>ep-eventflow-fragment</type>
            </dependency>
            ...
        </dependencies>
        ...
        <dependencyManagement>
            <dependencies>
                <dependency>
                     <groupId>com.tibco.ep.samples</groupId>
                     <artifactId>helloworld</artifactId>
                     <version>1.0.0</version>
                 </dependency>
                 ...
             </dependencies>
        </dependencyManagement>
        ...
    </project>
```
