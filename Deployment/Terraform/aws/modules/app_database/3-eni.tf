locals {
  eni_tags = {
    name = "${var.environment}-cassandra-instance-eni"
    terraform   = "true"
  }
}

// First availability zone eni
module "az1_cassandra_seed_eni" {
  count = var.enable_az1_cassandra_seed ? 1 : 0
  source  = "../eni"
  eni_subnet_id       = module.main_vpc.vpc_private_subnets[0] //First private subnet
  eni_private_ips     = var.main_az1_eni_private_ips //Pre configured db private ip --> for seed 1
  eni_security_groups = [aws_security_group.cassandra_private_sg.id] //List of security group
  eni_tags = local.eni_tags
}

// second availability zone eni
module "az2_cassandra_seed_eni" {
  count = var.enable_az2_cassandra_seed ? 1 : 0
  source  = "../eni"
  eni_subnet_id       = module.main_vpc.vpc_private_subnets[1] //Second private subnet
  eni_private_ips     = var.main_az2_eni_private_ips //Pre configured db private ip --> for seed 1
  eni_security_groups = [aws_security_group.cassandra_private_sg.id] //List of security group
  eni_tags = local.eni_tags
}


module "bastion_host_eni" {
  count = var.enable_bastion_host ? 1 : 0
  source  = "../eni"
  eni_subnet_id       = module.main_vpc.vpc_public_subnets[0] //Second private subnet
  eni_private_ips     = var.main_bastion_host_private_ips //Pre configured db private ip --> for seed 1
  eni_security_groups = [aws_security_group.cassandra_public_sg.id] //List of security group
  eni_tags = local.eni_tags
}