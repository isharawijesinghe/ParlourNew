#tfsec:ignore:aws-ec2-add-description-to-security-group
resource "aws_security_group" "vpc_endpoint_sg" {
  count = var.enabled ? 1 : 0

  name   = "${var.vpc_name}-endpoint-sg"
  vpc_id = module.vpc.vpc_id

  #tfsec:ignore:aws-vpc-add-description-to-security-group-rule tfsec:ignore:aws-vpc-no-public-egress-sgr
  egress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] #tfsec:ignore:AWS009
  }

  #tfsec:ignore:aws-vpc-add-description-to-security-group-rule tfsec:ignore:aws-vpc-no-public-egress-sgr
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  tags = merge(var.tags, {
    Name = "${var.vpc_name}-endpoint-sg"
  })
}

