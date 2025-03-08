package com.example.demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@Testcontainers
public abstract class AbstractAppTests {
    static Logger logger = LoggerFactory.getLogger(AbstractAppTests.class);

    abstract int getPort();

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris()
                                        .removePort()))
                .build();
    }

    @Test
    public void shouldReturnHealthy() {
        Response response = given(this.spec)
                .filter(document("get"))
                .when()
                .port(getPort())
                .get("/actuator/health")
                .then()
                .assertThat().statusCode(is(200))
                .extract().response();
        logger.info("Body: " + response.getBody().asString());
        assert response.getBody().asString().contains("UP");
    }

}
