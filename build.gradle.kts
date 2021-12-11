import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.4.4"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.4.31"
  kotlin("plugin.spring") version "1.4.31"
  kotlin("kapt") version "1.3.61"
}

group = "com.corradowaver"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  implementation("org.springframework.boot:spring-boot-configuration-processor:2.4.4")
  implementation("net.dv8tion:JDA:4.2.0_168")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
  implementation("khttp:khttp:1.0.0")

  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
  implementation("com.fasterxml.jackson.core:jackson-databind")

  // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")

  // https://mvnrepository.com/artifact/com.sedmelluq/lavaplayer
  implementation("com.sedmelluq:lavaplayer:1.3.7")

  implementation("com.amazonaws:aws-java-sdk-bom:1.12.122")
  implementation("com.amazonaws:aws-java-sdk-s3:1.12.122")
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
