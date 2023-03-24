FROM public.ecr.aws/lambda/java:11

COPY build/classes/kotlin/main ${LAMBDA_TASK_ROOT}
COPY build/dependency/* ${LAMBDA_TASK_ROOT}/lib/


CMD ["com.mle.handler.StreamLambdaHandler::handleRequest"]

