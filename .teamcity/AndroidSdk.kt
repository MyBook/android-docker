import common.DockerImageBuildType

object AndroidSdk : DockerImageBuildType(
    name = "Android SDK",
    dirName = "android-sdk",
    parent = JavaJdk,
    executionTimeoutMin = 20,
)
