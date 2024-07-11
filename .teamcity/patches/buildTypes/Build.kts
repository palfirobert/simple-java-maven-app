package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    check(artifactRules == "") {
        "Unexpected option value: artifactRules = $artifactRules"
    }
    artifactRules = "/target/surefire-reports/**"

    params {
        add {
            param("env.MAVEN_OPTS", "env.MAVEN_OPTS")
        }
    }

    expectSteps {
        script {
            name = "Echo"
            scriptContent = "echo 'Building JAR...'"
        }
        maven {
            name = "Custom build name"
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = bundled_3_9()
            jdkHome = "%env.JDK_17_0%"
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            name = ""
            clearConditions()
            scriptContent = """
                export PATH="%teamcity.tool.maven.DEFAULT%/bin":${'$'}{PATH}
                mvn -version
            """.trimIndent()
        }
        items.removeAt(1)
    }
}
