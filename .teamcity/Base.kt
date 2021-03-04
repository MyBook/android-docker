import common.DockerImageBuildType

object Base : DockerImageBuildType(
    Params(
        name = "Base",
        imageName = "base",
        executionTimeoutMin = 5,
    )
)
