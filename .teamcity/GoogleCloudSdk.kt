import common.DockerImageBuildType

object GoogleCloudSdk : DockerImageBuildType(
    name = "Google Cloud SDK",
    dirName = "google-cloud-sdk",
    executionTimeoutMin = 5,
)
