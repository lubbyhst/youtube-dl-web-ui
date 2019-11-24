FROM openjdk:11.0.5-jdk

USER root
RUN mkdir -p /opt/ytdlui/download

WORKDIR /opt/ytdlui/

RUN apt update \
    && apt install -y curl ffmpeg python python-pip\
    && rm -rf /var/lib/apt/lists/*

COPY jar/app.jar /opt/ytdlui/app.jar

# This is on a separate line because youtube-dl needs to be frequently updated
RUN curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
RUN chmod a+rx /usr/local/bin/youtube-dl

EXPOSE 8888

CMD ["java", "-jar", "/opt/ytdlui/app.jar"]