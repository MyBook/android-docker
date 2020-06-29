import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.version

version = "2019.2"

project {
    buildType(Base)
    buildType(JavaJdk)
    buildType(AndroidSdk)
    buildType(AndroidBundleTool)
    buildType(Lokalise)
    buildType(FirebaseTools)
    buildType(ProjectMyBookAndroid)
}
