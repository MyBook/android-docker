import common.DockerImageBuildType

object AndroidBundleTool : DockerImageBuildType(
    Params(
        name = "Android BundleTool",
        imageName = "android-bundletool",
        executionTimeoutMin = 5,
    )
)
