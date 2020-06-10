import common.addBuildDockerImageSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object JavaJdk : BuildType({

    name = "Java Development Kit"

    params {
        param("env.BUILD_BRANCH", "")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    failureConditions {
        executionTimeoutMin = 5
    }

    features {
        swabra {
            forceCleanCheckout = true
            verbose = true
        }
    }

    steps {

        addBuildDockerImageSteps(
                "java-jdk",
                "PARENT_IMAGE_TAG" to "${'$'}image_tag_with_branch"
        )

    }

})
