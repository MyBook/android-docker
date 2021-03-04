package common

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

open class DockerImageBuildType(params: Params) : BuildType({

    name = params.name

    params.description?.let {
        description = it
    }

    params.parent?.let { dependsOn ->
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
        root(DslContext.settingsRoot)
    }

    steps {

        addBuildDockerImageSteps(
            imageName = params.imageName,
            parentImageName = params.parent?.imageName,
        )

    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    failureConditions {
        executionTimeoutMin = params.executionTimeoutMin
    }

    features {
        swabra {
            forceCleanCheckout = true
            verbose = true
        }
    }

}) {

    private val imageName: String = params.imageName

    data class Params(
        val name: String,
        val description: String? = null,
        val imageName: String,
        val parent: DockerImageBuildType? = null,
        val executionTimeoutMin: Int,
    )

}
