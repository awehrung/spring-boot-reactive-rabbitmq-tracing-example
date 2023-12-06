FROM azul/zulu-openjdk-alpine:21-jre

RUN apk --no-cache add bash

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /home/appuser

COPY ./target/downstream-service.jar ./app.jar

COPY ./entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

USER appuser

EXPOSE 12002
ENTRYPOINT ["./entrypoint.sh"]
