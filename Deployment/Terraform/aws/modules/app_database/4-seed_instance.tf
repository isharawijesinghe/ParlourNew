locals {
  seed_tags = {
    name   = "${var.environment}-cassandra-seed-node"
  }
}

// Availability zone 1 seed instance
module  "az1_cassandra_seed" {
  count = var.enable_az1_cassandra_seed ? 1 : 0
  source  = "../ec2"
  ec2_instance_ami               = var.main_ec2_instance_ami //AMI id for pre build cassandra instance
  ec2_instance_instance_type     = var.main_ec2_instance_instance_type //Ec2 cassandra type ex: t2.micro
  ec2_instance_key_name          = var.main_ec2_instance_key_name //Created key name in AWS
  ec2_instance_availability_zone = module.main_vpc.vpc_subnets[0] //First private subnet
  ec2_instance_network_interface_id = module.az1_cassandra_seed_eni[0].eni_id //First eni id
  ec2_instance_user_data          = "${file("${path.module}/files/seed_cluster_config.sh")}" //User data script location
  ec2_instance_tag                = local.seed_tags
}

// Availability zone 2 seed instance
module  "az2_cassandra_seed" {
  count = var.enable_az2_cassandra_seed ? 1 : 0
  source  = "../ec2"
  ec2_instance_ami               = var.main_ec2_instance_ami //AMI id for pre build cassandra instance
  ec2_instance_instance_type     = var.main_ec2_instance_instance_type //Ec2 cassandra type ex: t2.micro
  ec2_instance_key_name          = var.main_ec2_instance_key_name //Created key name in AWS
  ec2_instance_availability_zone = module.main_vpc.vpc_subnets[1] //Second private subnet
  ec2_instance_network_interface_id = module.az2_cassandra_seed_eni[1].eni_id //Second eni id
  ec2_instance_user_data          = "${file("${path.module}/files/seed_cluster_config.sh")}" //User data script location
  ec2_instance_tag                = local.seed_tags
}