import common.DockerImageBuildType

object Base : DockerImageBuildType(
    name = "Base",
    imageName = "base",
    executionTimeoutMin = 5,
)
