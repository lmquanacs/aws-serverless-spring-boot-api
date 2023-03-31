import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.unbrokendome.gradle.plugins.testsets.dsl.testSets

plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id ("com.avast.gradle.docker-compose") version "0.16.11"
	id ("org.unbroken-dome.test-sets") version "4.0.0"
	kotlin("jvm") version "1.8.0"
	kotlin("plugin.spring") version "1.8.0"
}

group = "com.mle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

// Integration test registry
testSets {
	create("intTest") {
		this.dirName = "intTest"
	}
}

val intTestImplementation = "intTestImplementation"

dependencies {
	// implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
	//implementation("org.springframework.boot:spring-boot-starter-web") {
		// not starting as a webserver
	//	exclude(group="org.springframework.boot", module = "spring-boot-starter-tomcat")
	//}

	implementation("org.springframework.boot:spring-boot-starter-webflux") {
		// not starting as a webserver
		exclude(group="org.springframework.boot", module = "spring-boot-starter-reactor-netty")
	}

	implementation("org.springframework.boot:spring-boot-starter-validation")


	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")


	implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
	implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot2:1.9.1")

	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	// AWS
	// https://mvnrepository.com/artifact/software.amazon.awssdk/dynamodb
	implementation("software.amazon.awssdk:dynamodb:2.20.35")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// inteTest
	intTestImplementation("org.springframework.boot:spring-boot-starter-test")
	intTestImplementation("io.kotest:kotest-runner-junit5:5.5.5")
	intTestImplementation("org.springframework.boot:spring-boot-starter-reactor-netty")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}

tasks.create(name="copyRuntimeDependencies", type = Copy::class) {
	from(configurations.runtimeClasspath)
	into("build/dependency")
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}

tasks.get("intTest").dependsOn("composeUp")

tasks.build {
	// finalizedBy(tasks.shadowJar)
	finalizedBy("copyRuntimeDependencies")
}

dockerCompose {
	file("docker-compose.yml")
	setProjectName("aws_serverless_spring_boot_api")
}

tasks.create("createDynamoDbTable", Exec::class) {
	commandLine("sh", "scripts/create-tables.sh")
}

tasks.composeUp {
	finalizedBy("createDynamoDbTable")
}