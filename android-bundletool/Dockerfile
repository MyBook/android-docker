FROM adoptopenjdk/openjdk8:jdk8u252-b09-alpine-slim

RUN VERSION=1.1.0; \
    FILE_NAME=bundletool-all-${VERSION}.jar; \
    DIR=/opt/android/bundletool; \
    FILE_PATH=$DIR/lib/$FILE_NAME; \
    FILE_URL=https://github.com/google/bundletool/releases/download/${VERSION}/${FILE_NAME}; \
    mkdir -p $DIR/lib/ && \
    apk add --no-cache curl && curl -L -o $FILE_PATH $FILE_URL && apk del curl && \
    ln -s $FILE_NAME /opt/android/bundletool/lib/bundletool.jar && \
    mkdir -p /usr/local/bin/

ADD bundletool /opt/android/bundletool/bin/bundletool

RUN ln -s /opt/android/bundletool/bin/bundletool /usr/local/bin/bundletool
