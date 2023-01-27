locals {
  bastion_tags = {
    name   = "${var.environment}-cassandra-bastion-host"
  }
}


module  "bastion_host" {
  count = var.enable_bastion_host ? 1 : 0
  source  = "../ec2"
  ec2_instance_ami               = var.main_bastion_instance_ami
  ec2_instance_instance_type     = var.main_bastion_instance_instance_type
  ec2_instance_key_name          = var.main_bastion_instance_key_name
  ec2_instance_availability_zone = module.main_vpc.vpc_subnets[0]
  ec2_instance_network_interface_id = module.bastion_host_eni[0].eni_id
  ec2_instance_user_data          = null
  ec2_instance_tag                = local.bastion_tags
}