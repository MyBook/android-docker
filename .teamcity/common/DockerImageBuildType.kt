package common

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

open class DockerImageBuildType(params: Params) : BuildType({

    name = params.name
    description = params.description

    params.parent?.let { dependsOn ->
        dependencies {
            snapshot(dependsOn.buildType) {
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
            params.imageName,
            params.parent?.imageName,
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

    data class Params(
        val name: String,
        val description: String,
        val imageName: String,
        val parent: ParentBuildType?,
        val executionTimeoutMin: Int,
    ) {

        data class ParentBuildType(
            val buildType: BuildType,
            val imageName: String,
        )

    }

}
