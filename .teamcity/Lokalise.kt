import common.DockerImageBuildType

object Lokalise : DockerImageBuildType(
    Params(
        name = "Lokalise",
        imageName = "lokalise",
        parent = Params.ParentBuildType(Base, "base"),
        executionTimeoutMin = 20,
    )
)
