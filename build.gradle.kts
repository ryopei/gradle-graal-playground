plugins {
    kotlin("jvm") version "1.4.10"
    id("com.palantir.graal") version "0.7.1-20-g113a84d"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val ktor_version = "1.4.1"
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")
}

application {
    mainClassName = "net.sunaba.graal.AppKt"
}

graal {
    graalVersion("20.2.0")
    javaVersion("11")
    windowsVsVersion("2019")
    windowsVsEdition("Community")
    mainClass(application.mainClassName)
    outputName("app")
    option("--no-fallback")
    option("--enable-all-security-services")
    option("--report-unsupported-elements-at-runtime")
    option("-H:ReflectionConfigurationFiles=reflect-config.json")
    option("-H:ResourceConfigurationFiles=resource-config.json")
    option("--initialize-at-build-time=io.ktor,kotlinx,kotlin,org.slf4j")
}

tasks {
    create("runNative") {
        group = "application"
        dependsOn("nativeImage")
        doLast {
            exec {
                this.setCommandLine("${project.buildDir}/graal/${graal.outputName.get()}")
            }
        }
    }
}
