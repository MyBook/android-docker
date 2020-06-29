import common.addBuildDockerImageSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object ProjectMyBookAndroid : BuildType({

    name = "Project MyBook Android"
    description = "Image for MyBook Android project"

    dependencies {
        snapshot(AndroidSdk) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }

    params {
        param("env.BUILD_BRANCH", "")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {

        addBuildDockerImageSteps(
            "project-mybook-android-build",
            "android-sdk"
        )

    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    failureConditions {
        executionTimeoutMin = 10
    }

    features {
        swabra {
            forceCleanCheckout = true
            verbose = true
        }
    }

})
