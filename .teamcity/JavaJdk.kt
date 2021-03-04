import common.DockerImageBuildType

object JavaJdk : DockerImageBuildType(
    name = "Java Development Kit",
    imageName = "java-jdk",
    parent = Base,
    executionTimeoutMin = 5,
)
