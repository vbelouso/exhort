package com.redhat.ecosystemappeng.routes;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.redhat.ecosystemappeng.extensions.WiremockV2Extension;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@QuarkusTestResource(WiremockV2Extension.class)
public class RouteV2Test {
    
    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Disabled
    void testStackAnalysisReport() throws IOException {
       
        given()
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .multiPart("manifest", "dependencies.txt", Files.readAllBytes(Path.of(getClass().getClassLoader().getResource("dependencies.txt").getPath())), MediaType.APPLICATION_OCTET_STREAM)
            .formParam("ecosystem", "maven")
            .formParam("file_path", "/tmp/bin")
        .when()
            .post("/api/v2/stack-analyses")
        .then()
            .assertThat().statusCode(200);
    }

    @Test
    void testStackAnalysis() {
        given()
            .when()
            .get("/api/v2/stack-analyses/1234")
            .then().assertThat().statusCode(200);
        
        given()
            .when()
            .get("/api/v2/stack-analyses/9999")
            .then().assertThat().statusCode(404);    
    
    }

    @Test
    void testComponentAnalysis() {
        given()
            .when()
                .get("/api/v2/component-analyses/maven/log4j:log4j/1.2.7")
            .then()
                .assertThat().statusCode(200);
        given()
            .when()
                .get("/api/v2/component-analyses/maven/notfound:package/x.y.z")
            .then()
                .assertThat().statusCode(404);
    }
}