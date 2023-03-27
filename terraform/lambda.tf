# IAM
data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "role" {
  name               = "myrole"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

module "aws-serverless-spring-boot-api-module" {
  source = "terraform-aws-modules/lambda/aws"

  function_name = "aws-serverless-spring-boot-api"
  description   = "My demo function"

  create_package = false

  lambda_role = aws_iam_role.role.arn

  memory_size = 512

  timeout     = 10

  architectures = ["arm64"]

  image_uri    = "${aws_ecr_repository.aws-lambda-repo.repository_url}:${var.imageVersion}"
  package_type = "Image"

  depends_on = [null_resource.build_image]
}


