FROM alpine:3.16.3

RUN apk --no-cache add \
    openjdk17-jre \
    bash 

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /home/appuser

COPY ./target/upstream-service.jar ./app.jar

COPY ./entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

USER appuser

EXPOSE 12001
ENTRYPOINT ["./entrypoint.sh"]
