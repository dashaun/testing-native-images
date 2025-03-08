### Get started

```bash
curl https://start.spring.io/starter.tgz \
-d dependencies=web,actuator,testcontainers,native \
-d javaVersion=23 \
-d bootVersion=3.4.3 \
-d type=maven-project | tar -xzf -

```

---

## Friends don't let friends use Dockerfile.

---

```bash
# Generate OCI Image
./mvnw spring-boot:build-image \
-Dspring-boot.build-image.imageName=dashaun/devnexus2025:default

docker images | grep devnexus2025
```

---

```bash
# Generate OCI "Native" Image
./mvnw -Pnative spring-boot:build-image

# Generate OCI "Native" AMD64 Image
./mvnw -Pnative spring-boot:build-image \
-Dspring-boot.build-image.imageName=dashaun/devnexus2025:native

docker images | grep devnexus2025
```