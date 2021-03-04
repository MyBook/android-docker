import common.DockerImageBuildType

object ProjectMyBookAndroid : DockerImageBuildType(
    name = "Project MyBook Android",
    description = "Image for MyBook Android project",
    imageName = "project-mybook-android-build",
    parent = AndroidSdk,
    executionTimeoutMin = 10,
)
