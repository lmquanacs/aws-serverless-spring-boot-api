FROM amazoncorretto:11 as build

COPY . app/
WORKDIR app
RUN ./gradlew build --no-daemon

FROM public.ecr.aws/lambda/java:11 as package

# COPY --from=build app/build/libs/*all.jar ${LAMBDA_TASK_ROOT}/lib/
COPY --from=build app/build/classes/kotlin/main ${LAMBDA_TASK_ROOT}
COPY --from=build app/build/dependency/* ${LAMBDA_TASK_ROOT}/lib/

CMD ["com.mle.seed.handler.StreamLambdaHandler::handleRequest"]

