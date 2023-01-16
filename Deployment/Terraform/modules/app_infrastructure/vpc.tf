locals {
  azs = {
    main = {
      private  = slice(data.aws_availability_zones.main.names, 0, min(length(data.aws_availability_zones.main.names), var.main_vpc_az_count_private))
      database = slice(data.aws_availability_zones.main.names, 0, min(length(data.aws_availability_zones.main.names), var.main_vpc_az_count_database))
      public   = slice(data.aws_availability_zones.main.names, 0, min(length(data.aws_availability_zones.main.names), var.main_vpc_az_count_public))
    }
  }

  main_vpc_name        = "${var.environment}-main"
  //You can add multiple of VPC like backup
}

module "main_vpc" {
  source  = "../../modules/networking/vpc"
  enabled = var.main_vpc_enable

  environment = var.environment

  vpc_name = local.main_vpc_name
  vpc_cidr = var.main_vpc_cidr

  public_subnets   = var.main_vpc_public_subnets
  private_subnets  = var.main_vpc_private_subnets
  database_subnets = var.main_vpc_database_subnets

  vpc_enable_nat_gateway     = var.main_vpc_enable_nat_gateway
  vpc_one_nat_gateway_per_az = var.main_vpc_one_nat_gateway_per_az
  vpc_single_nat_gateway     = var.main_vpc_single_nat_gateway

  azs_private  = local.azs.main.private
  azs_database = local.azs.main.database
  azs_public   = local.azs.main.public

  enable_dynamodb_endpoint                 = var.main_vpc_enable_dynamodb_endpoint
  enable_kms_endpoint                      = var.main_vpc_enable_kms_endpoint
  enable_s3_endpoint                       = var.main_vpc_enable_s3_endpoint
  enable_secretsmanager_endpoint           = var.main_vpc_enable_secretsmanager_endpoint
  enable_ses_endpoint                      = var.main_vpc_enable_ses_endpoint
  enable_sns_endpoint                      = var.main_vpc_enable_sns_endpoint
  enable_sqs_endpoint                      = var.main_vpc_enable_sqs_endpoint
  enable_ssm_endpoint                      = var.main_vpc_enable_ssm_endpoint
  enable_ssmmessages_endpoint              = var.main_vpc_enable_ssmmessages_endpoint

  tags = local.main_tags

}
