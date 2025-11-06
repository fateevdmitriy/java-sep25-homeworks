import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("com.google.guava:guava:+")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("HelloOtus")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.java.prof.homeworks.hw1.HelloOtus"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

tasks.test {
    useJUnitPlatform()
}
