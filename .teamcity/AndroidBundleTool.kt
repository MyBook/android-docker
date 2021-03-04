import common.DockerImageBuildType

object AndroidBundleTool : DockerImageBuildType(
    name = "Android BundleTool",
    dirName = "android-bundletool",
    executionTimeoutMin = 5,
)
