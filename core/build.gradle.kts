import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    val kotlinVersion = "1.6.21"
    val springBootVersion = "3.0.2"
    val springDependencyManagementVersion = "1.0.15.RELEASE"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    jacoco
}

group = "ru.surf"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    val springDocVersion = "2.0.2"
    val mockkVersion = "1.13.4"
    val springMockkVersion = "4.0.2"
    val jUnit5Version = "5.8.1"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("net.logstash.logback:logstash-logback-encoder:7.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

    runtimeOnly("org.postgresql:postgresql")

    implementation("org.junit.jupiter:junit-jupiter:$jUnit5Version")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }

    testImplementation("com.ninja-squad:springmockk:${springMockkVersion}")
    testImplementation("io.mockk:mockk:${mockkVersion}")

    implementation(project(":domain"))
    implementation(project(":auth"))
    implementation(project(":external-files"))
    implementation(project(":meeting"))
    implementation(project(":remoting"))

    devDependencies {
        runtimeOnly("com.h2database:h2:2.1.214")
    }
}

fun devDependencies(configuration: DependencyHandlerScope.() -> Unit) {
    if (project.hasProperty("dev")) {
        DependencyHandlerScope.of(dependencies).configuration()
    }
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

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    doLast {
        archiveFile.get().asFile.apply {
            copyTo(target = File(parent, "app.jar"), overwrite = true)
        }
    }
}
