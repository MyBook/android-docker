FROM mybook-android-android-sdk:git-branch-master

# Install common used packages
RUN apt update; \
    apt install --no-install-recommends -y git; \
    apt-get clean; \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
