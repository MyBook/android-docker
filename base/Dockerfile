FROM phusion/baseimage:bionic-1.0.0

SHELL ["bash", "-c"]

# Install common used packages
RUN apt update && \
    apt install --no-install-recommends -y unzip bsdtar && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
