import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar


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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.auth0:jwks-rsa:0.21.3")
    implementation("com.auth0:java-jwt:4.2.2")
    implementation("com.caucho:hessian:4.0.66")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.2")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("org.postgresql:postgresql:42.5.1")

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

tasks.withType<BootJar> {
    doLast {
        archiveFile.get().asFile.apply {
            copyTo(target=File(parent, "app.jar"), overwrite=true)
        }
    }
}
