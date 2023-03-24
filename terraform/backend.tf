terraform {
  backend "s3" {
    bucket  = "com.mle.terraform-states"
    key     = "github/aws-serverless-spring-boot-api/state.tfstate"
    region  = "ap-southeast-2"
    profile = "lambda"
    encrypt = "true"
  }
}
