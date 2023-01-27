locals {
  public_sg_tags = {
    terraform   = "true"
  }
}


resource "aws_security_group" "cassandra_public_sg" {
  vpc_id = module.main_vpc.vpc_id
  name   = "${var.environment}-cassandra-public-subnet-instance-sg"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_security_group_rule" "cassandra_public_9042" {
  type              = "ingress"
  from_port         = 9042
  to_port           = 9042
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.cassandra_public_sg.id
}
