import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val awssdkBomVersion = "2.15.0"

plugins {
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "ru.surf"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {

    val springDocVersion = "1.6.9"
    val apacheCommonsCodecVersion = "1.15"
    val hibernateVersion = "5.6.14.Final"
    val apachePoiVersion = "3.17"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2:2.1.214")

    implementation("org.flywaydb:flyway-core")
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
    implementation("commons-codec:commons-codec:$apacheCommonsCodecVersion")
    implementation("org.hibernate:hibernate-envers:$hibernateVersion")
    implementation(platform("software.amazon.awssdk:bom:$awssdkBomVersion"))
    implementation("software.amazon.awssdk:s3")
    implementation("org.apache.poi:poi-ooxml:$apachePoiVersion")
    implementation(project(":domain"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
