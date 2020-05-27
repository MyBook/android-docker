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

# set the environment variables
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV ANDROID_HOME /opt/android/sdk

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
ARG COMMAND_LINE_TOOLS=commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip 
RUN curl --silent --show-error -o /var/tmp/$COMMAND_LINE_TOOLS https://dl.google.com/android/repository/$COMMAND_LINE_TOOLS && \
    unzip /var/tmp/$COMMAND_LINE_TOOLS -d $ANDROID_HOME && \
    rm /var/tmp/$COMMAND_LINE_TOOLS 
    
# Add to PATH Android SDK
ENV PATH=$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools:$PATH

# accept the license agreements of the SDK components
RUN yes | sdkmanager --update
RUN yes | sdkmanager --licenses

# Install Android Build Tool and Libraries
RUN sdkmanager --update 1>/dev/null

RUN sdkmanager "build-tools;29.0.3" 1>/dev/null
RUN sdkmanager "platforms;android-29" 1>/dev/null
RUN sdkmanager "platform-tools" 1>/dev/null
RUN sdkmanager "extras;google;m2repository" 1>/dev/null
RUN sdkmanager "extras;android;m2repository" 1>/dev/null
