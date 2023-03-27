variable "env" {
  description = "Environment"
  default     = "dev"
}

variable "awsProfile" {
  description = "Profile"
  default     = "lambda"
}

variable "region" {
  description = "region"
  default     = "ap-southeast-2"
}

variable "imageVersion" {
  description = "Version"
  default     = "1.0.2_3"
}

variable "appName" {
  description = "Version"
  default     = "aws-serverless-spring-boot-api"
}