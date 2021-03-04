import common.DockerImageBuildType

object AndroidSdk : DockerImageBuildType(
    Params(
        name = "Android SDK",
        imageName = "android-sdk",
        parent = Params.ParentBuildType(JavaJdk, "java-jdk"),
        executionTimeoutMin = 20,
    )
)
