import common.DockerImageBuildType

object FirebaseTools : DockerImageBuildType(
    name = "Firebase Tools",
    imageName = "firebase-tools",
    executionTimeoutMin = 5,
)
