import common.DockerImageBuildType

object AndroidBundleTool : DockerImageBuildType(
    name = "Android BundleTool",
    imageName = "android-bundletool",
    executionTimeoutMin = 5,
)
