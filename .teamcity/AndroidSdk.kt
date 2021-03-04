import common.DockerImageBuildType

object AndroidSdk : DockerImageBuildType(
    Params(
        name = "Android SDK",
        imageName = "android-sdk",
        parent = JavaJdk,
        executionTimeoutMin = 20,
    )
)
