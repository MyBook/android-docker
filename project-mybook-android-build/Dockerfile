ARG PARENT_IMAGE_TAG=git-branch-master
ARG GRADDLE_VERSION="8.4"

FROM ci-mybook-android:android-sdk-$PARENT_IMAGE_TAG

# Install common used packages
RUN apt update && \
    apt install --no-install-recommends -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


# setup gradle version
RUN gradle wrapper --gradle-version ${GRADDLE_VERSION} --distribution-type all

# Configure sdk
RUN sdkmanager "build-tools;34.0.0" 1>/dev/null && \
    sdkmanager "emulator" 1>/dev/null && \
    sdkmanager "extras;android;m2repository" 1>/dev/null && \
    sdkmanager "extras;google;m2repository" 1>/dev/null && \
    sdkmanager "platforms;android-34" 1>/dev/null && \
    sdkmanager "cmake;3.10.2.4988404" 1>/dev/null && \
    sdkmanager "ndk;21.3.6528147" 1>/dev/null \

ENV ANDROID_NDK $ANDROID_HOME/ndk-bundle
