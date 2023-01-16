data "aws_region" "current" {}
data "aws_caller_identity" "current" {}

data "aws_vpc_endpoint_service" "ses" {
  count   = var.enabled && var.enable_ses_endpoint ? 1 : 0
  service = "email-smtp"
}

data "aws_subnets" "private_subnets" {
  count = var.enabled && var.enable_ses_endpoint ? 1 : 0

  filter {
    name   = "vpc-id"
    values = [module.vpc.vpc_id]
  }

  filter {
    name   = "subnet-id"
    values = module.vpc.private_subnets
  }

  filter {
    name   = "availability-zone"
    values = try(data.aws_vpc_endpoint_service.ses[0].availability_zones, [])
  }
}
