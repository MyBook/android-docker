FROM phusion/baseimage:0.11

USER root

# Set user id with default value
ARG user_id=1000

# Install JAVA 8 
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update
RUN apt-get install --no-install-recommends -y software-properties-common  
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install --no-install-recommends -y openjdk-8-jdk
RUN java -version

# Install Unzip
RUN apt-get install --no-install-recommends -y unzip

# Install git
RUN apt-get install --no-install-recommends -y git

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENV ANDROID_HOME="/opt/android/sdk" 

# Make android directory
RUN mkdir -p $ANDROID_HOME 

# Create android group
RUN groupadd --gid $user_id android

# Create user for CI
RUN useradd -G android --create-home --uid $user_id --shell /bin/bash agent

# Add permisssions to android group
RUN chgrp -R android $ANDROID_HOME

RUN chmod g+w $ANDROID_HOME

# Set agent as User
USER agent

# Download and install Android SDK
ARG ANDROID_SDK_VERSION=6200805
ENV ANDROID_HOME /opt/android-sdk
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    unzip *tools*linux*.zip -d ${ANDROID_HOME}/cmdline-tools && \
    rm *tools*linux*.zip

# accept the license agreements of the SDK components
RUN mkdir -p "$ANDROID_HOME/licenses" || true
RUN echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > "$ANDROID_HOME/licenses/android-sdk-license"

# set the environment variables
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV GRADLE_HOME /opt/gradle
ENV GRADLE_USER_HOME /opt/.gradle/
ENV PATH ${PATH}:${GRADLE_HOME}/bin:${KOTLIN_HOME}/bin:${ANDROID_HOME}/tools:$ANDROID_HOME/tools/bin:${ANDROID_HOME}/cmdline-tools/tools/bin:${ANDROID_HOME}/platform-tools

# Install Android Build Tool and Libraries
RUN sdkmanager --update 1>/dev/null
RUN sdkmanager "build-tools;28.0.3" 1>/dev/null
RUN sdkmanager "build-tools;29.0.1" 1>/dev/null
RUN sdkmanager "platforms;android-28" 1>/dev/null
RUN sdkmanager "platforms;android-29" 1>/dev/null
RUN sdkmanager "platform-tools" 1>/dev/null

# Download and install Android NDK Libraries
RUN sdkmanager "ndk-bundle" 1>/dev/null
RUN sdkmanager "cmake;3.6.4111459" 1>/dev/null

# Turn off gradle daemon
RUN mkdir -p "$GRADLE_USER_HOME" 
RUN echo "org.gradle.daemon=false" >> "$GRADLE_USER_HOME/gradle.properties"
