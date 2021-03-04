import common.DockerImageBuildType

object Lokalise : DockerImageBuildType(
    name = "Lokalise",
    dirName = "lokalise",
    parent = Base,
    executionTimeoutMin = 20,
)
