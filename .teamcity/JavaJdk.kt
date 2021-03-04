import common.DockerImageBuildType

object JavaJdk : DockerImageBuildType(
    name = "Java Development Kit",
    dirName = "java-jdk",
    parent = Base,
    executionTimeoutMin = 5,
)
