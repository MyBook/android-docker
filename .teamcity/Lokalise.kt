import common.addBuildDockerImageSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Lokalise : BuildType({

    name = "Lokalise"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {

        addBuildDockerImageSteps("lokalise")

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
