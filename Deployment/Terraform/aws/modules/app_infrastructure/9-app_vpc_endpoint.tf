locals {
  s3_tags = {
    name = "${var.environment}-S3-Vpc-endpoint"
    terraform   = "true"
  }

  ecr_dkr_tags = {
    name = "${var.environment}-ecr-dkr-endpoint"
    terraform   = "true"
  }

  ecr_api_tags = {
    name = "${var.environment}-ecr-api-endpoint"
    terraform   = "true"
  }

  ec2_tags = {
    name = "${var.environment}-ec2-endpoint"
    terraform   = "true"
  }

  logs_tags = {
    name = "${var.environment}-logs-endpoint"
    terraform   = "true"
  }

  sts_tags = {
    name = "${var.environment}-sts-endpoint"
    terraform   = "true"
  }

}

resource "aws_security_group" "vpc_endpoint_sg" {
  name                         = "${var.environment}-application-endpoint-sg"
  vpc_id                       = module.main_vpc.vpc_id

  # SSH access only from Bastion network
  ingress {
    description                = "${var.environment}-application-vpc-endpoint-sg from bastion CIDR"
    from_port                  = 22
    to_port                    = 22
    protocol                   = "tcp"
    cidr_blocks                = ["0.0.0.0/0"]
  }

  egress {
    description                = "${var.environment}-application-vpc-endpoint-sg all to anywhere"
    from_port                  = 0
    to_port                    = 0
    protocol                   = "-1"
    cidr_blocks                = ["0.0.0.0/0"]
  }
}

resource "aws_security_group_rule" "allow_tcp_within_vpc" {
  type              = "ingress"
  from_port         = 9042
  to_port           = 9042
  protocol          = "tcp"
  cidr_blocks       = [module.main_vpc.vpc_cidr_block]
  security_group_id = aws_security_group.vpc_endpoint_sg.id
}

resource "aws_security_group_rule" "allow_tcp_within_443_vpc" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = [module.main_vpc.vpc_cidr_block]
  security_group_id = aws_security_group.vpc_endpoint_sg.id
}


//Create VPC endpoint for network load balancer to access into cassandra cluster
resource "aws_vpc_endpoint" "application_vpc_endpoint" {
  count = var.enable_db_infrastructure ? 1: 0
  vpc_id                       = module.main_vpc.vpc_id
  vpc_endpoint_type            = "Interface"
  service_name                 = module.database_infrastructure[0].endpoint_service_name
  subnet_ids                   = module.main_vpc.vpc_public_subnets
  security_group_ids           = [aws_security_group.vpc_endpoint_sg.id]
  private_dns_enabled          = false
}

//Create VPC endpoint to access S3
resource "aws_vpc_endpoint" "s3" {
  vpc_id          = module.main_vpc.vpc_id
  vpc_endpoint_type = "Gateway"
  service_name    = "com.amazonaws.${var.aws_region}.s3"
  route_table_ids = module.main_vpc.vpc_private_route_table_ids
  tags = local.s3_tags
}

//Create VPC endpoint to access ECR DKR
resource "aws_vpc_endpoint" "ecr-dkr-endpoint" {
  vpc_id              =module.main_vpc.vpc_id
  private_dns_enabled = true
  service_name        = "com.amazonaws.${var.aws_region}.ecr.dkr"
  vpc_endpoint_type   = "Interface"
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
  subnet_ids = module.main_vpc.vpc_private_subnets
  tags = local.ecr_dkr_tags
}

//Create VPC endpoint to access ECR API
resource "aws_vpc_endpoint" "ecr-api-endpoint" {
  vpc_id       = module.main_vpc.vpc_id
  service_name = "com.amazonaws.${var.aws_region}.ecr.api"
  vpc_endpoint_type = "Interface"
  private_dns_enabled = true
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
  subnet_ids = module.main_vpc.vpc_private_subnets
  tags = local.ecr_api_tags
}

//Create VPC endpoint to access EC2
resource "aws_vpc_endpoint" "ec2-endpoint" {
  vpc_id       = module.main_vpc.vpc_id
  service_name = "com.amazonaws.${var.aws_region}.ec2"
  vpc_endpoint_type = "Interface"
  private_dns_enabled = true
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
  subnet_ids = module.main_vpc.vpc_private_subnets
  tags = local.ec2_tags
}

//Create VPC endpoint to access Logs
resource "aws_vpc_endpoint" "logs-endpoint" {
  vpc_id       = module.main_vpc.vpc_id
  service_name = "com.amazonaws.${var.aws_region}.logs"
  vpc_endpoint_type = "Interface"
  private_dns_enabled = true
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
  subnet_ids = module.main_vpc.vpc_private_subnets
  tags = local.logs_tags
}

//Create VPC endpoint to access STS
resource "aws_vpc_endpoint" "sts-endpoint" {
  vpc_id       = module.main_vpc.vpc_id
  service_name = "com.amazonaws.${var.aws_region}.sts"
  vpc_endpoint_type = "Interface"
  private_dns_enabled = true
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
  subnet_ids = module.main_vpc.vpc_private_subnets
  tags = local.sts_tags
}

resource "aws_vpc_endpoint" "astra_vpc_endpoint" {
  vpc_id             = module.main_vpc.vpc_id
  service_name       = astra_private_link.astra_private_link.service_name
  vpc_endpoint_type  = "Interface"
  subnet_ids         = module.main_vpc.vpc_private_subnets
  security_group_ids = [aws_security_group.vpc_endpoint_sg.id]
}