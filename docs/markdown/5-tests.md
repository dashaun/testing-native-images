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