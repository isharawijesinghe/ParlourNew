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

resource "aws_vpc_endpoint" "application_vpc_endpoint" {
  vpc_id                       = module.main_vpc.vpc_id
  vpc_endpoint_type            = "Interface"
  service_name                 = module.database_infrastructure.endpoint_service_name
  subnet_ids                   = module.main_vpc.vpc_public_subnets
  security_group_ids           = [aws_security_group.vpc_endpoint_sg.id]
  private_dns_enabled          = false
}