import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val awssdkBomVersion = "2.15.0"

plugins {
    id("org.springframework.boot") version "3.0.2"
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

    val springDocVersion = "2.0.2"
    val apacheCommonsCodecVersion = "1.15"
    val hibernateVersion = "6.1.7.Final"
    val apachePoiVersion = "3.17"
    val postgreSQLVersion = "42.5.1"
    val kLoggingVersion = "0.4.6"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

    implementation("commons-codec:commons-codec:$apacheCommonsCodecVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb:flyway-core")

    implementation("org.hibernate:hibernate-envers:$hibernateVersion")
    implementation(platform("software.amazon.awssdk:bom:$awssdkBomVersion"))
    implementation("software.amazon.awssdk:s3")
    implementation("org.apache.poi:poi-ooxml:$apachePoiVersion")
    implementation("org.postgresql:postgresql:$postgreSQLVersion")
    implementation("io.klogging:klogging-jvm:$kLoggingVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation(project(":domain"))
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

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    doLast {
        archiveFile.get().asFile.apply {
            copyTo(target = File(parent, "app.jar"), overwrite = true)
        }
    }
}
