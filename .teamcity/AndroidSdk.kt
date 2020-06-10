import common.addBuildDockerImageSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object AndroidSdk : BuildType({

    name = "Android SDK"

    params {
        param("env.BUILD_BRANCH", "")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {

        addBuildDockerImageSteps("android-sdk")

    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    failureConditions {
        executionTimeoutMin = 20
    }

    features {
        swabra {
            forceCleanCheckout = true
            verbose = true
        }
    }

})
