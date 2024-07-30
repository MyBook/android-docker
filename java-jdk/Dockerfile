ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:base-$PARENT_IMAGE_TAG

RUN apt update && \
    apt install wget && \
    wget -O- https://apt.corretto.aws/corretto.key | apt-key add - && \
    apt purge -y wget && \
    add-apt-repository 'deb https://apt.corretto.aws stable main' && \
    apt update && \
    apt install --no-install-recommends -y java-17-amazon-corretto-jdk && \
    apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENV JAVA_HOME /usr/lib/jvm/java-15-amazon-corretto
