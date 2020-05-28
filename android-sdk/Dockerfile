FROM mybook-android-java-jdk:git-branch-master

ENV ANDROID_HOME /opt/android/sdk

RUN mkdir -p $ANDROID_HOME 

# https://stackoverflow.com/questions/60440509/android-command-line-tools-sdkmanager-always-shows-warning-could-not-create-se
RUN VERSION=6200805; \
    FILE=commandlinetools-linux-${VERSION}_latest.zip; \
    URL=https://dl.google.com/android/repository/$FILE; \
    curl $URL | bsdtar --extract --verbose --file - --directory $ANDROID_HOME; \
    mkdir -p $ANDROID_HOME/cmdline-tools; \
    mv $ANDROID_HOME/tools $ANDROID_HOME/cmdline-tools/latest; \
    chmod +x $ANDROID_HOME/cmdline-tools/latest/bin/*; \
    chmod -R o+w $ANDROID_HOME

ENV ANDROID_BUILD_TOOLS_VERSION 29.0.3

ENV PATH $ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$ANDROID_HOME/build-tools/$ANDROID_BUILD_TOOLS_VERSION:$PATH

RUN yes | sdkmanager --update

# accept the license agreements of the SDK components
RUN yes | sdkmanager --licenses

RUN sdkmanager --update 1>/dev/null

RUN sdkmanager "platform-tools" 1>/dev/null
RUN sdkmanager "extras;android;m2repository" 1>/dev/null
RUN sdkmanager "extras;google;m2repository" 1>/dev/null
RUN sdkmanager "platforms;android-29" 1>/dev/null
RUN sdkmanager "build-tools;$ANDROID_BUILD_TOOLS_VERSION" 1>/dev/null