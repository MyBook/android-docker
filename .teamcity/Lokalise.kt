import common.DockerImageBuildType

object Lokalise : DockerImageBuildType(
    name = "Lokalise",
    imageName = "lokalise",
    parent = Base,
    executionTimeoutMin = 20,
)
