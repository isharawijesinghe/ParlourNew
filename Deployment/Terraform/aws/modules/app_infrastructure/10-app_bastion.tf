resource "aws_security_group" "sg_1" {
  name                         = "${var.environment}-application-bastion-sg"
  vpc_id                       = module.main_vpc.vpc_id

  # SSH access only from Bastion network
  ingress {
    description                = "${var.environment}-application-bastion-sg ssh into bastion from anywhere"
    from_port                  = 22
    to_port                    = 22
    protocol                   = "tcp"
    cidr_blocks                = ["0.0.0.0/0"]
  }

  ingress {
    description                = "${var.environment}-application-bastion-sg all within this sg"
    from_port                  = 0
    to_port                    = 0
    protocol                   = "-1"
    self                       = true
  }

  egress {
    description                = "${var.environment}-application-bastion-sg all to anywhere"
    from_port                  = 0
    to_port                    = 0
    protocol                   = "-1"
    cidr_blocks                = ["0.0.0.0/0"]
  }

}

resource "aws_instance" "bastion_instance" {
  instance_type                = var.db_main_bastion_instance_instance_type
  ami                          = var.db_main_bastion_instance_ami
  key_name                     = var.db_main_bastion_instance_key_name
  vpc_security_group_ids       = [aws_security_group.sg_1.id]
  subnet_id                    = module.main_vpc.vpc_public_subnets[0]
  associate_public_ip_address  = true
}
