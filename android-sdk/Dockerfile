ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:java-jdk-$PARENT_IMAGE_TAG

ENV ANDROID_HOME /opt/android/sdk

RUN mkdir -p $ANDROID_HOME

# https://stackoverflow.com/questions/60440509/android-command-line-tools-sdkmanager-always-shows-warning-could-not-create-se
RUN VERSION=6858069; \
    FILE=commandlinetools-linux-${VERSION}_latest.zip; \
    URL=https://dl.google.com/android/repository/$FILE; \
    curl $URL | bsdtar --extract --verbose --file - --directory $ANDROID_HOME && \
    mv $ANDROID_HOME/cmdline-tools $ANDROID_HOME/cmdline-tools.bak && \
    mkdir -p $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools.bak $ANDROID_HOME/cmdline-tools/latest && \
    chmod +x $ANDROID_HOME/cmdline-tools/latest/bin/* && \
    chmod o+w -R $ANDROID_HOME

ENV PATH $ANDROID_HOME/cmdline-tools/latest/bin:$PATH

# accept the license agreements of the SDK components
RUN yes | sdkmanager --licenses

RUN sdkmanager --update 1>/dev/null

RUN sdkmanager "tools" 1>/dev/null
ENV PATH $ANDROID_HOME/tools:$PATH

RUN sdkmanager "platform-tools" 1>/dev/null
ENV PATH $ANDROID_HOME/platform-tools:$PATH
