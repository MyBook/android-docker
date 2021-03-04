package common

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

open class DockerImageBuildType(
    name: String,
    description: String? = null,
    parent: DockerImageBuildType? = null,
    executionTimeoutMin: Int,
    dirName: String,
) : BuildType({

    val dockerImageName: String = dirName

    this.name = name

    description?.let {
        this.description = it
    }

    parent?.let { dependsOn ->
        dependencies {
            snapshot(dependsOn) {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }
        }
    }

    params {
        param("env.BUILD_BRANCH", "")
    }

    vcs {
        root(
            DslContext.settingsRoot,
            "+:$dirName",
        )
    }

    steps {

        addBuildDockerImageSteps(
            imageName = dockerImageName,
            parentImageName = parent?.dockerImageName,
        )

    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    failureConditions {
        this.executionTimeoutMin = executionTimeoutMin
    }

    features {
        swabra {
            forceCleanCheckout = true
            verbose = true
        }
    }

}) {

    private val dockerImageName: String = dirName

}
