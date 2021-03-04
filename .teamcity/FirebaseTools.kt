import common.DockerImageBuildType

object FirebaseTools : DockerImageBuildType(
    name = "Firebase Tools",
    dirName = "firebase-tools",
    executionTimeoutMin = 5,
)
