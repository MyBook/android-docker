import common.DockerImageBuildType

object AndroidSdk : DockerImageBuildType(
    name = "Android SDK",
    imageName = "android-sdk",
    parent = JavaJdk,
    executionTimeoutMin = 20,
)
