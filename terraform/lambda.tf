module "aws-serverless-spring-boot-api-module" {
  source = "terraform-aws-modules/lambda/aws"

  function_name = "aws-serverless-spring-boot-api"
  description   = "My demo function"

  create_package = false

  memory_size = 512

  image_uri    = "${aws_ecr_repository.aws-lambda-repo.repository_url}:${var.imageVersion}"
  package_type = "Image"
}