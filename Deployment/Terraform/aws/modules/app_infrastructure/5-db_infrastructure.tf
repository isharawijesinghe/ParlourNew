module "database_infrastructure" {
  source  = "../app_database"

  //General Variables
  environment = var.environment

  // VPC Parameters
  main_vpc_public_subnets   = var.db_main_vpc_public_subnets
  main_vpc_private_subnets  = var.db_main_vpc_private_subnets
  main_vpc_database_subnets = var.db_main_vpc_database_subnets
  main_vpc_enable            = var.db_main_vpc_enable
  main_vpc_cidr              = var.db_main_vpc_cidr
  main_vpc_az_count_private  = var.db_main_vpc_az_count_private
  main_vpc_az_count_database = var.db_main_vpc_az_count_database
  main_vpc_az_count_public   = var.db_main_vpc_az_count_public
  main_vpc_one_nat_gateway_per_az = var.db_main_vpc_one_nat_gateway_per_az
  main_vpc_enable_nat_gateway     = var.db_main_vpc_enable_nat_gateway
  main_vpc_single_nat_gateway     = var.db_main_vpc_single_nat_gateway

  //Seed ENI Parameters
  enable_az1_cassandra_seed = var.db_enable_az1_cassandra_seed
  enable_az2_cassandra_seed = var.db_enable_az2_cassandra_seed
  enable_cassandra_node_asg = var.db_enable_cassandra_node_asg
  main_az1_eni_private_ips = var.db_main_az1_eni_private_ips
  main_az2_eni_private_ips = var.db_main_az2_eni_private_ips

  //Seed Nodes Instance Parameters
  main_ec2_instance_ami = var.db_main_ec2_instance_ami
  main_ec2_instance_instance_type = var.db_main_ec2_instance_instance_type
  main_ec2_instance_key_name =  var.db_main_ec2_instance_key_name

  //ASG + LC Parameters
  enable_lc_cluster_node = var.db_enable_lc_cluster_node
  main_asg_min_size = var.db_main_asg_min_size
  main_asg_max_size = var.db_main_asg_max_size
  main_asg_policy_type = var.db_main_asg_policy_type
  main_asg_metric_aggregation_type = var.db_main_asg_metric_aggregation_type
  main_asg_scaling_adjustment = var.db_main_asg_scaling_adjustment
  main_asg_metric_interval_lower_bound = var.db_main_asg_metric_interval_lower_bound
  main_asg_metric_interval_upper_bound = var.db_main_asg_metric_interval_upper_bound

  //ELB Parameters
  main_seed_port_config = var.db_main_seed_port_config

  //Bastion Instance Parameters
  enable_bastion_host = var.db_enable_bastion_host
  main_bastion_instance_ami = var.db_main_bastion_instance_ami
  main_bastion_instance_instance_type = var.db_main_bastion_instance_instance_type
  main_bastion_instance_key_name = var.db_main_bastion_instance_key_name
  main_bastion_host_private_ips = var.db_main_bastion_host_private_ips
}