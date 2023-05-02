import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.21"
    val springBootVersion = "3.0.2"
    val springDependencyManagementVersion = "1.0.15.RELEASE"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "ru.surf"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    val springDocVersion = "2.0.2"
    val springRetryVersion = "1.3.1"
    val logbackEncoderVersion = "7.3"
    val ical4jVersion = "3.2.10"
    val mockkVersion = "1.9.3"
    val testContainersVersion = "1.17.6"

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry:$springRetryVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")

    runtimeOnly("org.postgresql:postgresql")

    implementation("org.junit.jupiter:junit-jupiter:5.8.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.mockk:mockk:${mockkVersion}")
    implementation(platform("org.testcontainers:testcontainers-bom:$testContainersVersion"))
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")

    implementation(project(":domain"))
    implementation(project(":remoting"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
   onlyIf{
       false
   }
}
