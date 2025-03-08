## Integration Testing Strategy

* Use Testcontainers
* Generate native image
* Validate with Integration Tests

---

## Additional Dependencies

```text
    <dependency>
        <groupId>org.springframework.restdocs</groupId>
        <artifactId>spring-restdocs-restassured</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.apache.maven.shared</groupId>
        <artifactId>maven-invoker</artifactId>
        <version>3.3.0</version>
        <scope>test</scope>
    </dependency>
```

---

## [Demo - example](https://github.com/dashaun/testing-native-images)

---

## Add Failsafe Plugin for integration tests

```text
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-failsafe-plugin</artifactId>
        <version>3.5.2</version>
        <executions>
            <execution>
                <goals>
                    <goal>integration-test</goal>
                    <goal>verify</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
```