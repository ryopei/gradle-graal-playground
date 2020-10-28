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
    implementation(kotlin("stdlib"))
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
