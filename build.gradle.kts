import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	// id("com.github.johnrengelman.shadow") version "7.1.2"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.mle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
	implementation("org.springframework.boot:spring-boot-starter-web") {
		// not starting as a webserver
		exclude(group="org.springframework.boot", module = "spring-boot-starter-tomcat")
	}
	implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
	implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot2:1.9.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
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

tasks.build {
	dependsOn("copyRuntimeDependencies")
}


