#data "terraform_remote_state" "generics" {
#  backend   = "s3"
#  workspace = "default"
#
#  config = {
#    encrypt        = true
#    bucket         = "st-terraform-state-dev"
#    key            = "dev/generics-infrastructure.tfstate"
#    region         = "eu-west-1"
#    dynamodb_table = "st-terraform-state-lock-dev"
#    role_arn       = local.assume_role_arn # Required for DEV
#  }
#}
#
#data "terraform_remote_state" "grafana" {
#  backend   = "s3"
#  workspace = "default"
#
#  config = {
#    encrypt        = true
#    bucket         = "st-terraform-state-dev"
#    key            = "dev/grafana.tfstate"
#    region         = "eu-west-1"
#    dynamodb_table = "st-terraform-state-lock-dev"
#    role_arn       = local.assume_role_arn # Required for DEV
#  }
#}
#
#data "terraform_remote_state" "amplify_resource_updater" {
#  backend   = "s3"
#  workspace = "default"
#
#  config = {
#    encrypt        = true
#    bucket         = "st-terraform-state-dev"
#    key            = "dev/services/amplify-resource-updater.tfstate"
#    region         = "eu-west-1"
#    dynamodb_table = "st-terraform-state-lock-dev"
#    role_arn       = local.assume_role_arn # Required for DEV
#  }
#}
