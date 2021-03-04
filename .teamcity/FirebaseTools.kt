import common.DockerImageBuildType

object FirebaseTools : DockerImageBuildType(
    Params(
        name = "Firebase Tools",
        imageName = "firebase-tools",
        executionTimeoutMin = 5,
    )
)
