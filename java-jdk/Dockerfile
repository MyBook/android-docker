ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:base-$PARENT_IMAGE_TAG

RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt update && \
    apt install --no-install-recommends -y software-properties-common && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
    apt install --no-install-recommends -y openjdk-8-jdk-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
