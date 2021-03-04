import common.DockerImageBuildType

object AndroidEmulator : DockerImageBuildType(
    name = "Android Emulator (API 30, Google Services, x86)",
    dirName = "android-emulator-30-google-x86",
    executionTimeoutMin = 25,
)
