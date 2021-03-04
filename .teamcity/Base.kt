import common.DockerImageBuildType

object Base : DockerImageBuildType(
    name = "Base",
    dirName = "base",
    executionTimeoutMin = 5,
)
