ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:android-sdk-$PARENT_IMAGE_TAG

# Install common used packages
RUN apt update && \
    apt install --no-install-recommends -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN sdkmanager "extras;android;m2repository" 1>/dev/null
RUN sdkmanager "extras;google;m2repository" 1>/dev/null
RUN sdkmanager "platforms;android-30" 1>/dev/null

RUN sdkmanager "cmake;3.10.2.4988404" 1>/dev/null
RUN sdkmanager "ndk;21.3.6528147" 1>/dev/null
ENV ANDROID_NDK $ANDROID_HOME/ndk-bundle
