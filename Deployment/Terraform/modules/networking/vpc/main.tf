locals {
  subnets = cidrsubnets(var.vpc_cidr, [2, 2, 2, 4, 4, 4, 6, 6, 6]...)

  private_subnets  = coalesce(var.private_subnets, slice(slice(local.subnets, 0, 3), 0, length(var.azs_private)))
  database_subnets = coalesce(var.database_subnets, slice(slice(local.subnets, 3, 6), 0, length(var.azs_database)))
  public_subnets   = coalesce(var.public_subnets, slice(slice(local.subnets, 6, 9), 0, length(var.azs_public)))

  azs = distinct(concat(var.azs_private, var.azs_database, var.azs_public))

  max_subnet_length = max(
    length(local.private_subnets),
    length(local.database_subnets),
  )

  nat_gateway_count = var.vpc_enable_nat_gateway ? (var.vpc_single_nat_gateway ? 1 : var.vpc_one_nat_gateway_per_az ? length(local.azs) : local.max_subnet_length) : 0
}

module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "3.19.0"

  create_vpc = var.enabled

  name = var.vpc_name
  cidr = var.vpc_cidr
  azs  = local.azs

  # Subnets configuration
  public_subnets   = local.public_subnets
  private_subnets  = local.private_subnets
  database_subnets = local.database_subnets

  # Route Tables configuration
  create_database_subnet_route_table = var.create_database_subnet_route_table

  # Route Tables: INFRA-2075 / INFRA-2046: Add Internet Egress route to DB Subnets on Review environments.
  create_database_nat_gateway_route = terraform.workspace != "default"

  # NATs configuration
  enable_nat_gateway     = var.vpc_enable_nat_gateway
  one_nat_gateway_per_az = var.vpc_one_nat_gateway_per_az
  single_nat_gateway     = var.vpc_single_nat_gateway

  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = var.tags
}

module "vpc_endpoints" {
  source  = "terraform-aws-modules/vpc/aws//modules/vpc-endpoints"
  version = "3.19.0"

  create = var.enabled

  vpc_id             = module.vpc.vpc_id
  security_group_ids = aws_security_group.vpc_endpoint_sg[*].id

  tags = merge(var.tags, {
    "Name" = var.vpc_name
  })

  endpoints = {
    for key, endpoint_config in {

      # Access to DynamoDB
      dynamodb = {
        enabled             = var.enable_dynamodb_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.dynamodb"
        service_type        = "Gateway"
        route_table_ids     = concat(module.vpc.private_route_table_ids, module.vpc.public_route_table_ids)
        private_dns_enabled = true
      }

      kms = {
        enabled             = var.enable_kms_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.kms"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      logs = {
        enabled             = var.enable_logs_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.logs"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      monitoring = {
        enabled             = var.enable_monitoring_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.monitoring"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      s3 = {
        enabled             = var.enable_s3_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.s3"
        service_type        = "Gateway"
        route_table_ids     = concat(module.vpc.private_route_table_ids, module.vpc.public_route_table_ids, module.vpc.database_route_table_ids)
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      secretsmanager = {
        enabled             = var.enable_secretsmanager_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.secretsmanager"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      ses = {
        enabled             = var.enable_ses_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.email-smtp"
        subnet_ids          = try(tolist(data.aws_subnets.private_subnets[0].ids), [])
        private_dns_enabled = true
      }

      sns = {
        enabled             = var.enable_sns_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.sns"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      sqs = {
        enabled             = var.enable_sqs_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.sqs"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      ssm = {
        enabled             = var.enable_ssm_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.ssm"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

      # SSM Messages endpoint
      # This endpoint is required only if you are connecting to your instances through a secure data channel
      # using Session Manager.
      ssmmessages = {
        enabled             = var.enable_ssmmessages_endpoint
        service_name        = "com.amazonaws.${data.aws_region.current.name}.ssmmessages"
        private_dns_enabled = true
        subnet_ids          = module.vpc.private_subnets
      }

    } : key => endpoint_config if var.enabled && lookup(endpoint_config, "enabled", false)

  }
}

