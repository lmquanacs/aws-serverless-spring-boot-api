

resource "null_resource" "build_image" {
  triggers = {
    x=var.imageVersion
  }
  provisioner "local-exec" {
    command = <<-EOT
      export APP_VERSION='${var.imageVersion}'
      export REPO_URI=${split("/", aws_ecr_repository.aws-lambda-repo.repository_url)[0]}
      export APP_NAME=${var.appName}
      export AWS_PROFILE=${var.awsProfile}
      cd ..
      sh scripts/aws-login.sh
      sh scripts/build.sh
    EOT
  }
}