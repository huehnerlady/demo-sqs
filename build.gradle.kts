import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  kotlin("jvm") version "1.9.10"
  kotlin("plugin.spring") version "1.9.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework:spring-test")
  implementation("software.amazon.awssdk:sqs:2.20.63")
  runtimeOnly("software.amazon.awssdk:sts:2.20.63")
  implementation("com.dasburo:spring-cache-dynamodb:2.0.0")
  implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs:3.0.2")
  implementation("io.awspring.cloud:spring-cloud-aws-starter-dynamodb:3.0.2")
  implementation("org.springframework:spring-webmvc")
  implementation("org.springframework:spring-web")
  implementation("org.springframework:spring-context")
  testImplementation("org.testcontainers:localstack:1.19.0")
  testImplementation("org.testcontainers:junit-jupiter:1.19.0")
  testImplementation("io.mockk:mockk:+")
  testImplementation("io.kotest:kotest-assertions-core-jvm:+")
  testImplementation("io.kotest:kotest-framework-engine-jvm:+")
  testImplementation("io.kotest:kotest-property-jvm:+")
  testImplementation("io.kotest:kotest-runner-junit5-jvm:+")
  testImplementation("io.kotest.extensions:kotest-extensions-spring:+")
  testImplementation("com.ninja-squad:springmockk:+")
  testImplementation("io.kotest:kotest-assertions-core-jvm:+")
  testImplementation("io.kotest:kotest-framework-engine-jvm:+")
  testImplementation("io.kotest:kotest-property-jvm:+")
  testImplementation("io.kotest:kotest-runner-junit5-jvm:+")
  testImplementation("io.kotest.extensions:kotest-extensions-spring:+")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
