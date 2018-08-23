# Using

This component is available via maven.  Include in your pom.xml file the
following :-

```xml
    <project>
        ...
        <dependencies>
            <dependency>
                <groupId>com.tibco.ep.samples</groupId>
                <artifactId>querytable_application</artifactId>
                <type>zip</type>
                <classifier>ep-java-fragment</classifier>
            </dependency>
            ...
        </dependencies>
        ...
        <dependencyManagement>
            <dependencies>
                <dependency>
                     <groupId>com.tibco.ep.samples</groupId>
                     <artifactId>querytable_application</artifactId>
                     <version>1.0.0</version>
                 </dependency>
                 ...
             </dependencies>
        </dependencyManagement>
        ...
    </project>
```