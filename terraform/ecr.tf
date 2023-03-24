resource "aws_ecr_repository" "aws-lambda-repo" {
  name                 = "aws-serverless-spring-boot-api"
  image_tag_mutability = "MUTABLE"


  image_scanning_configuration {
    scan_on_push = true
  }
}