ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:android-sdk-$PARENT_IMAGE_TAG

# Install common used packages
RUN apt update && \
    apt install --no-install-recommends -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
