package sall.good.springkotlinwebfluxrestdocsexample

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@Extensions(ExtendWith(SpringExtension::class), ExtendWith(RestDocumentationExtension::class))
@SpringBootTest
internal class ControllerTests {

    @Autowired
    lateinit var context: ApplicationContext

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(Preprocessors.prettyPrint()))
                .build()
    }

    @Test
    fun hello() {
        webTestClient.get().uri("/hello").exchange()
                .expectStatus().isOk.expectBody()
                .consumeWith(WebTestClientRestDocumentation.document("hello-world"))
    }
}
