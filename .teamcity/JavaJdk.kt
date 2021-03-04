import common.DockerImageBuildType

object JavaJdk : DockerImageBuildType(
    Params(
        name = "Java Development Kit",
        imageName = "java-jdk",
        parent = Params.ParentBuildType(Base, "base"),
        executionTimeoutMin = 5,
    )
)
