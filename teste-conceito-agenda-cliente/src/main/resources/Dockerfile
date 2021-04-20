FROM openjdk:8

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

ARG GCP_CREDENTIAL_FILE
COPY ${GCP_CREDENTIAL_FILE} /home/credential_gcp.json

ENV GOOGLE_APPLICATION_CREDENTIALS=/home/credential_gcp.json
ENV GOOGLE_PROJECT_ID=poc-jc
ENV GOOGLE_TOPIC_SINCRONO=agenda-pubsub-sincrono
ENV GOOGLE_SUBSCRIPTION_SINCRONO=agenda-pubsub-sincrono-sub
ENV GOOGLE_TOPIC_ASSINCRONO=agenda-pubsub-assincrono
ENV GOOGLE_SUBSCRIPTION_ASSINCRONO=agenda-pubsub-assincrono-sub
ENV GOOGLE_DATASTORE_NAMESPACE=agendas
ENV GOOGLE_BFF_KEY=123456dev
ENV GOOGLE_APIGEE_TRANSACTIONHISTORY_ADDRESS=https://api-test.claro.com.br/mobile/v1/customerhistoryrefills
ENV GOOGLE_APIGEE_CUSTOMERBALANCEANDDATE_ADDRESS=https://api-test.claro.com.br/mobile/v1/customerbalancesrecharge

ENV GOOGLE_APIGEE_OAUTH2_TOKEN_ADDRESS=https://api-test.claro.com.br/oauth2/v1/token
ENV GOOGLE_APIGEE_OAUTH2_TOKEN_X_CLIENT_AUTH=Basic OXJWWkNrcjZ4RnVYbFdSWUNLU1hwN1ZtUHk0TEdWSVk6RVNoQWM5ZG95Z2V3Wng4Qw==
ENV GOOGLE_APIGEE_OAUTH2_TOKEN_X_WWW_FORM_URLENCODED_KEY=grant_type
ENV GOOGLE_APIGEE_OAUTH2_TOKEN_X_WWW_FORM_URLENCODED_VALUE=client_credentials