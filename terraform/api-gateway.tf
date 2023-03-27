# API Gateway
resource "aws_api_gateway_rest_api" "api" {
  name = "proxy-api"
}

resource "aws_api_gateway_resource" "api_resource" {
  path_part   = "{proxy+}"
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  rest_api_id = aws_api_gateway_rest_api.api.id
}

resource "aws_api_gateway_method" "api_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.api_resource.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "lambda_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.api_resource.id
  http_method             = aws_api_gateway_method.api_method.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = module.aws-serverless-spring-boot-api-module.lambda_function_invoke_arn
}

resource "aws_lambda_permission" "apigw_lambda_perm" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = module.aws-serverless-spring-boot-api-module.lambda_function_name
  principal     = "apigateway.amazonaws.com"

  # More: http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-control-access-using-iam-policies-to-invoke-api.html
  source_arn = "arn:aws:execute-api:${var.region}:${data.aws_caller_identity.current.account_id}:${aws_api_gateway_rest_api.api.id}/*/${aws_api_gateway_method.api_method.http_method}${aws_api_gateway_resource.api_resource.path}"
}

# Deployment

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api.id

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.api.body))
  }

  lifecycle {
    create_before_destroy = true
  }

  depends_on = [aws_api_gateway_method.api_method]
}

resource "aws_api_gateway_stage" "app_deployment_stage" {
  deployment_id = aws_api_gateway_deployment.deployment.id
  rest_api_id   = aws_api_gateway_rest_api.api.id

  stage_name    = "app"
}

resource "aws_api_gateway_method_settings" "stage_setting" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  stage_name  = aws_api_gateway_stage.app_deployment_stage.stage_name
  method_path = "*/*"

  settings {
    metrics_enabled = false
#    logging_level   = "INFO"
  }
}

# API KEY
resource "aws_api_gateway_api_key" "access_key" {
  name = "access-api-key"
}

resource "aws_api_gateway_usage_plan" "usage_plan" {
  name = "example-usage-plan"

  api_stages {
    api_id = aws_api_gateway_rest_api.api.id
    stage  = aws_api_gateway_stage.app_deployment_stage.stage_name
  }
  depends_on = [aws_api_gateway_stage.app_deployment_stage]
}

resource "aws_api_gateway_usage_plan_key" "plan_key" {
  key_id = aws_api_gateway_api_key.access_key.id
  key_type = "API_KEY"
  usage_plan_id = aws_api_gateway_usage_plan.usage_plan.id
}
