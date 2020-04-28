FROM phusion/baseimage:0.9.17

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

# set the environment variables
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV GRADLE_HOME /opt/gradle
ENV GRADLE_USER_HOME /opt/.gradle/
ENV ANDROID_HOME /opt/android-sdk

ENV PATH=$GRADLE_HOME/bin:$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/cmdline-tools/tools/bin:$ANDROID_HOME/platform-tools:$PATH

# Make android directory
RUN mkdir -p $ANDROID_HOME 
RUN mkdir -p $GRADLE_HOME
RUN mkdir -p $GRADLE_USER_HOME

# Download and install Android SDK
ARG ANDROID_SDK_VERSION=6200805
ARG COMMAND_LINE_TOOLS=commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip 
RUN mkdir -p $ANDROID_HOME/cmdline-tools && \
	curl --silent --show-error -o /var/tmp/$COMMAND_LINE_TOOLS https://dl.google.com/android/repository/$COMMAND_LINE_TOOLS && \
    unzip /var/tmp/$COMMAND_LINE_TOOLS -d $ANDROID_HOME/cmdline-tools && \
    rm /var/tmp/$COMMAND_LINE_TOOLS 

# accept the license agreements of the SDK components
RUN mkdir -p "$ANDROID_HOME/licenses" || true
RUN echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > "$ANDROID_HOME/licenses/android-sdk-license"

# Turn off gradle daemon
RUN echo "org.gradle.daemon=false" >> "$GRADLE_USER_HOME/gradle.properties"

# Create android group
RUN groupadd --gid $user_id android

# Create user for CI
RUN useradd -G android --create-home --uid $user_id --shell /bin/bash agent

# Add permisssions to android group
RUN chgrp -R android $ANDROID_HOME

RUN chmod g+w $ANDROID_HOME

# Set agent as User
USER agent

# Install Android Build Tool and Libraries
RUN sdkmanager --update 1>/dev/null
RUN sdkmanager "build-tools;28.0.3" 1>/dev/null
RUN sdkmanager "build-tools;29.0.3" 1>/dev/null
RUN sdkmanager "platforms;android-28" 1>/dev/null
RUN sdkmanager "platforms;android-29" 1>/dev/null
RUN sdkmanager "platform-tools" 1>/dev/null

