FROM phusion/baseimage:0.9.17

SHELL ["bash", "-c"]

# Install common used packages
RUN apt update; \
    apt install --no-install-recommends -y unzip bsdtar git; \
    apt-get clean; \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
