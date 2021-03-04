import common.DockerImageBuildType

object ProjectMyBookAndroid : DockerImageBuildType(
    Params(
        name = "Project MyBook Android",
        description = "Image for MyBook Android project",
        imageName = "project-mybook-android-build",
        parent = Params.ParentBuildType(AndroidSdk, "android-sdk"),
        executionTimeoutMin = 10,
    )
)
