import common.DockerImageBuildType

object JavaJdk : DockerImageBuildType(
    Params(
        name = "Java Development Kit",
        imageName = "java-jdk",
        parent = Base,
        executionTimeoutMin = 5,
    )
)
