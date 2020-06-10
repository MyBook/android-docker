package common

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

fun BuildSteps.addBuildDockerImageSteps(
        imageName: String,
        parentImageName: String? = null
) {

    val repositoryHost = "%docker.registry.host%"
    val repositoryName = "ci-mybook-android-sdk"
    val repositoryPath = "$repositoryHost/$repositoryName"
    val imageRemotePath = "$repositoryPath:$imageName"

    val pullParentImageCommand = parentImageName?.let { parentImage ->
        """
            parent_image_tag_version=$parentImage-git-branch-${'$'}safe_branch_name
            docker pull $repositoryPath:${'$'}parent_image_tag_version
            docker tag $repositoryPath:${'$'}parent_image_tag_version $repositoryName:${'$'}parent_image_tag_version
        """.trimIndent()
    } ?: ""

    val buildArgs = mutableMapOf<String, String>()

    parentImageName?.let {
        buildArgs += "PARENT_IMAGE_TAG" to "git-branch-${'$'}safe_branch_name"
    }

    val buildArgsString = buildArgs.entries
            .joinToString(
                    separator = " "
            ) { "--build-arg ${it.key}=${it.value}" }

    script {
        name = "Pull existing image from Registry"
        scriptContent = """
                vcs_branch=${DslContext.settingsRoot.paramRefs.buildVcsBranch}
                
                # Remove 'refs/heads/' prefix
                vcs_branch=${'$'}(echo "${'$'}vcs_branch" | sed "s/refs\/heads\///")
                
                # Replace '/' symbol with '-' in branch name
                safe_branch_name=${'$'}(echo "${'$'}vcs_branch" | sed "s/\//-/")
                echo ${'$'}safe_branch_name > safe_branch_name
                echo "Safe branch name is [${'$'}safe_branch_name]"
                
                docker pull $imageRemotePath || true
                docker pull $imageRemotePath-latest || true
                docker pull $imageRemotePath-git-branch-master || true
                docker pull $imageRemotePath-git-branch-${'$'}safe_branch_name || true
                
                $pullParentImageCommand
                
            """.trimIndent()
    }

    script {
        name = "Build docker image"
        scriptContent = """
                vcs_commit_id=${DslContext.settingsRoot.paramRefs.buildVcsNumber}
                vcs_branch=${DslContext.settingsRoot.paramRefs.buildVcsBranch}
                
                # Remove 'refs/heads/' prefix
                vcs_branch=${'$'}(echo "${'$'}vcs_branch" | sed "s/refs\/heads\///")
                
                image_tag_git_commit_postfix=git-hash-${'$'}vcs_commit_id
                
                # Create tag name with Git hash in it
                image_tag_with_commit=$repositoryName:$imageName-${'$'}image_tag_git_commit_postfix
                echo "Image tag with commit is [${'$'}image_tag_with_commit]"
                
                # Replace '/' symbol with '-' in branch name
                safe_branch_name=${'$'}(cat safe_branch_name)
                echo "Safe branch name is [${'$'}safe_branch_name]"
                
                image_tag_with_branch=$repositoryName:$imageName-git-branch-${'$'}safe_branch_name
                echo "Image tag with branch is [${'$'}image_tag_with_branch]"
                
                echo "Build image"
                echo "$buildArgsString"
                docker build $buildArgsString \
                    --tag=${'$'}image_tag_with_commit \
                    $imageName
                
                echo "Add tag with branch name"
                docker tag ${'$'}image_tag_with_commit ${'$'}image_tag_with_branch
                
                echo "Save additional tags into file 'tags_to_push'"
                echo "$repositoryHost/${'$'}image_tag_with_commit" >> tags_to_push
                echo "$repositoryHost/${'$'}image_tag_with_branch" >> tags_to_push
                
                echo "Tags to push:"
                for tag in ${'$'}(cat tags_to_push); do echo "  ${'$'}tag"; done
                
                # Add tags to image that must be pushed in next steps
                for tag in ${'$'}(cat tags_to_push); do docker tag ${'$'}image_tag_with_commit ${'$'}tag; done
            """.trimIndent()
    }

    script {
        name = "Push image to remote Docker Registry"
        scriptContent = "for tag in ${'$'}(cat tags_to_push); do docker push ${'$'}tag; done"
    }

}
