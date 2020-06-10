package common

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

fun BuildSteps.addBuildDockerImageSteps(
        imageName: String,
        vararg buildArgs: Pair<String, String>
) {

    val fullImageName = "ci-mybook-android-$imageName"

    val buildArgsString = buildArgs
            .joinToString(
                    separator = " \\\n",
                    postfix = " \\\n"
            ) { "--build-arg ${it.first}=${it.second}" }

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
                
                docker pull %docker.registry.host%/$fullImageName || true
                docker pull %docker.registry.host%/$fullImageName:latest || true
                docker pull %docker.registry.host%/$fullImageName:git-branch-master || true
                docker pull %docker.registry.host%/$fullImageName:git-branch-${'$'}safe_branch_name || true
            """.trimIndent()
    }

    script {
        name = "Build docker image"
        scriptContent = """
                vcs_commit_id=${DslContext.settingsRoot.paramRefs.buildVcsNumber}
                vcs_branch=${DslContext.settingsRoot.paramRefs.buildVcsBranch}
                
                # Remove 'refs/heads/' prefix
                vcs_branch=${'$'}(echo "${'$'}vcs_branch" | sed "s/refs\/heads\///")
                
                # Create tag name with Git hash in it
                image_tag_with_commit=$fullImageName:git-hash-${'$'}vcs_commit_id
                echo "Image tag with commit is [${'$'}image_tag_with_commit]"
                
                # Replace '/' symbol with '-' in branch name
                safe_branch_name=${'$'}(cat safe_branch_name)
                echo "Safe branch name is [${'$'}safe_branch_name]"
                
                image_tag_with_branch=$fullImageName:git-branch-${'$'}safe_branch_name
                echo "Image tag with branch is [${'$'}image_tag_with_branch]"
                
                # Build image
                docker build \
                    $buildArgsString
                    --tag=${'$'}image_tag_with_commit \
                    $imageName
                
                # Add tag with branch name
                docker tag ${'$'}image_tag_with_commit ${'$'}image_tag_with_branch
                
                # Save additional tags into file 'tags_to_push'
                echo "%docker.registry.host%/${'$'}image_tag_with_commit" >> tags_to_push
                echo "%docker.registry.host%/${'$'}image_tag_with_branch" >> tags_to_push
                
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