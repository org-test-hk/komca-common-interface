plugins {
    java
    id("org.springframework.boot") version "3.2.11"
    id("io.spring.dependency-management") version "1.1.6"
    id("maven-publish")
}

group = "kr.or.komca"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            // url은 https://maven.pkg.github.com/YOUR_ORGANIZATION/githun repository 이름
            url = uri("https://maven.pkg.github.com/org-test-hk/komca-common-interface")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            version = System.getenv("NEW_VERSION") ?: "0.0.1-SNAPSHOT"
            groupId = "kr.or.komca"
            artifactId = "interface"

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}
