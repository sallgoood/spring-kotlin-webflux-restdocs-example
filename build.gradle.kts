import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.0.M5"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.3.41"
	kotlin("plugin.spring") version "1.3.41"
}

group = "sall.good"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val snippetsDir: String = "$buildDir/generated-snippets"

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks {
	test {
		useJUnitPlatform()
		outputs.dir(snippetsDir)
	}

	asciidoctor {
		sourceDir = file("src/main/asciidoc")
		outputDir = file("src/main/resources/static/docs")

		options(mapOf("doctype" to "book",
				"backend" to "html"))

		attributes(
				mapOf(
						"source-highlighter" to "coderay",
						"snippets"        to snippetsDir)
		)

		dependsOn(test)
	}

	jar {
		dependsOn(asciidoctor)
	}
}
