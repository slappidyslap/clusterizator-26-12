import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  application
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.javamodularity.moduleplugin") version "1.8.15"
  id("org.openjfx.javafxplugin") version "0.0.13"
  id("org.beryx.jlink") version "3.1.1"
}

group = "kg.musabaev.clusterizator"
version = "0.9.0"

repositories {
  mavenCentral()
  maven {
    url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    mavenContent {
      snapshotsOnly()
    }
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

application {
  mainModule.set("kg.musabaev.cluserizator")
  mainClass.set("kg.musabaev.cluserizator.Launcher")
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "21"
  }
}

tasks.withType<Jar> {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
  useJUnitPlatform()
}

val javaFxVer = "21.0.5"
val mvvmFxVer by extra("1.9.0-SNAPSHOT")

javafx {
  version = javaFxVer
  modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

dependencies {
  implementation(files("lib/scenicview.jar"))

  implementation("com.alibaba.fastjson2:fastjson2:2.0.53")

//  implementation("com.vladsch.javafx-webview-debugger:javafx-webview-debugger:0.8.0")
  implementation("de.saxsys:mvvmfx:$mvvmFxVer")
  implementation("de.saxsys:mvvmfx-easydi:$mvvmFxVer")
}

jlink {
  addExtraDependencies("javafx")
  options.set(listOf(
    "--strip-debug",
    "--compress", "zip-6",
    "--no-header-files",
    "--no-man-pages"))
  launcher {
    name = "clusterizator"
  }
  imageZip.set(file("build/distributions/${name}-${version}-${javafx.platform.classifier}.zip"))

  /*jpackage {
    val installerType = project.findProperty("installerType") // we will pass this from the command line (example: -PinstallerType=msi)

    when (installerType) {
      "msi" -> {
        installerOptions =  listOf(
          "--verbose",
          "--win-per-user-install", "--win-dir-chooser",
          "--win-menu", "--win-shortcut",)
      }
      "deb" -> {
        installerOptions = listOf(
          "--linux-deb-maintainer", "musabaeveldiar@gmail.com", "--linux-shortcut")
      }
      "rpm" -> {
        installerOptions = listOf(
          "--linux-rpm-license-type", "GPLv3", "--linux-shortcut")
      }
      "dmg" -> {
        installerOptions = listOf(
          "--mac-package-name", "Clusterizator",
          "--mac-package-identifier", "kg.musabaev.clusterizator")
      }
    }
  }*/
}

tasks.named("jlinkZip") {
  group = "distribution"
}

tasks.register("processVisJsLibToProd") {
  group = "other"

  val resourcesViewFolder = file("src/main/resources/kg/musabaev/cluserizator/view")
  copy {
    from("lib") {
      include("vis-network.min.js")
    }
    into(resourcesViewFolder)
  }

  // change url to lib
  val htmlFile = file("$resourcesViewFolder/graphView.html")

  val htmlContent = htmlFile.readText()
  val visJsLibUrl = "../../../../../../../lib/vis-network.min.js"

  val updatedHtmlContent = htmlContent.replace(visJsLibUrl, "vis-network.min.js")

  htmlFile.writeText(updatedHtmlContent)
}

tasks.register("cleanVisJsLibProdProcess") {
  group = "other"

  val resourcesViewFolder = file("src/main/resources/kg/musabaev/cluserizator/view")
  delete(file("$resourcesViewFolder/vis-network.min.js"))

  val htmlFile = file("$resourcesViewFolder/graphView.html")
  if (!htmlFile.exists()) {
    throw GradleException("File graphView.html does not exist!")
  }

  val htmlContent = htmlFile.readText()
  val visJsLibUrl = "../../../../../../../lib/vis-network.min.js"

  val updatedHtmlContent = htmlContent.replace("vis-network.min.js", visJsLibUrl)

  htmlFile.writeText(updatedHtmlContent)
}
