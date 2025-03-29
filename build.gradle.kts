plugins {
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id "org.sonarqube" version "6.0.1.5171"
}

sonar {
  properties {
    property "sonar.projectKey", "itssdlc"
    property "sonar.projectName", "ITSSDLC FINAL OUTPUT"
  }
}
