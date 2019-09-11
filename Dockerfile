FROM phusion/baseimage:0.9.17

# Set user id with default value
ARG user_id=1000

# Create user for CI
RUN groupadd agent --gid $user_id && useradd --create-home -g agent --uid $user_id --shell /bin/bash agent

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

#Download and install Android SDK
RUN mkdir -p $ANDROID_HOME 
RUN curl -o sdk.zip "https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip"
RUN unzip -q sdk.zip -d $ANDROID_HOME && rm sdk.zip
RUN mkdir -p "$ANDROID_HOME/licenses" || true
RUN echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > "$ANDROID_HOME/licenses/android-sdk-license"

# Add to PATH Android SDK
ENV PATH=$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools:$PATH

# Install Android Build Tool and Libraries
RUN sdkmanager --update
RUN sdkmanager "build-tools;28.0.3" \
    "platforms;android-28" \
    "platform-tools"

RUN sdkmanager "ndk-bundle"

RUN sdkmanager "cmake;3.6.4111459"

# Add permissions to agent 
RUN chown -R agent $ANDROID_HOME &&\
    chmod -R g+w $ANDROID_HOME &&\
    chmod +x $ANDROID_HOME/tools/bin/*

# Set agent as User
USER agent

# Turn off gradle daemon
RUN mkdir -p ~/.gradle/ && echo "org.gradle.daemon=false" >> ~/.gradle/gradle.properties
