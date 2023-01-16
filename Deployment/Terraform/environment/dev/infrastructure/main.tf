locals {
  tags = {
    terraform   = "true"
    environment = local.environment
    workspace   = terraform.workspace
    project     = var.core_project_name
  }
}

locals {
  environment_metadata_tags = {}
}

module "core_infrastructure" {
  source = "../../../modules/app_infrastructure"

  environment = local.environment

  main_vpc_public_subnets   = var.core_main_vpc_public_subnets
  main_vpc_private_subnets  = var.core_main_vpc_private_subnets
  main_vpc_database_subnets = var.core_main_vpc_database_subnets

  # Main VPC
  main_vpc_enable            = var.core_enable_all || var.core_main_vpc_enable
  main_vpc_cidr              = var.core_main_vpc_cidr
  main_vpc_az_count_private  = var.core_main_vpc_az_count_private
  main_vpc_az_count_database = var.core_main_vpc_az_count_database
  main_vpc_az_count_public   = var.core_main_vpc_az_count_public

  main_vpc_one_nat_gateway_per_az = var.core_main_vpc_one_nat_gateway_per_az
  main_vpc_enable_nat_gateway     = var.core_main_vpc_enable_nat_gateway
  main_vpc_single_nat_gateway     = var.core_main_vpc_single_nat_gateway

  main_vpc_enable_dynamodb_endpoint                 = false
  main_vpc_enable_kms_endpoint                      = false
  main_vpc_enable_s3_endpoint                       = false
  main_vpc_enable_secretsmanager_endpoint           = false
  main_vpc_enable_ses_endpoint                      = false
  main_vpc_enable_sns_endpoint                      = false
  main_vpc_enable_sqs_endpoint                      = false
  main_vpc_enable_ssm_endpoint                      = false
  main_vpc_enable_ssmmessages_endpoint              = false

  main_eks_cluster_enable = var.core_main_eks_cluster_enable
  main_ecr_enable = var.core_main_ecr_enable

  force_delete = var.core_main_force_delete
  ecr_name = var.core_ecr_name
  image_mutability = var.core_image_mutability

  tags = merge(local.tags, local.environment_metadata_tags)
}
