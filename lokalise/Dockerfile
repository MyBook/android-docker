ARG PARENT_IMAGE_TAG=git-branch-master

FROM ci-mybook-android:base-$PARENT_IMAGE_TAG

RUN curl -sfL https://raw.githubusercontent.com/lokalise/lokalise-cli-2-go/5060ff9430b343a30b189cace8b6b96002b10d7a/install.sh | sh
