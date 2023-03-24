provider "aws" {
  region  = var.region
  profile = "lambda"
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.60.0"
    }
  }
}
