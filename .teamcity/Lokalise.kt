import common.DockerImageBuildType

object Lokalise : DockerImageBuildType(
    Params(
        name = "Lokalise",
        imageName = "lokalise",
        parent = Base,
        executionTimeoutMin = 20,
    )
)
