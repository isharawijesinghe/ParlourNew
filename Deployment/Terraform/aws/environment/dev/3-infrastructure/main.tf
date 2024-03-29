locals {
  tags = {
    terraform   = "true"
    environment = local.environment
    workspace   = terraform.workspace
    project     = var.core_project_name
  }
}

locals {
  environment_metadata_tags = {}
}

module "core_infrastructure" {
  source = "../../../modules/app_infrastructure"

  environment = local.environment
  aws_region =var.aws_region_main

  /***----------------------- ************************** -----------------------------***/
  /***----------------------- Application Infrastructure------------------------------***/

  main_vpc_public_subnets   = var.core_main_vpc_public_subnets
  main_vpc_private_subnets  = var.core_main_vpc_private_subnets
  main_vpc_database_subnets = var.core_main_vpc_database_subnets
  main_vpc_enable            = var.core_enable_all || var.core_main_vpc_enable
  main_vpc_cidr              = var.core_main_vpc_cidr
  main_vpc_az_count_private  = var.core_main_vpc_az_count_private
  main_vpc_az_count_database = var.core_main_vpc_az_count_database
  main_vpc_az_count_public   = var.core_main_vpc_az_count_public
  main_vpc_one_nat_gateway_per_az = var.core_main_vpc_one_nat_gateway_per_az
  main_vpc_enable_nat_gateway     = var.core_main_vpc_enable_nat_gateway
  main_vpc_single_nat_gateway     = var.core_main_vpc_single_nat_gateway
  main_eks_cluster_enable = var.core_main_eks_cluster_enable
  main_ecr_enable = var.core_main_ecr_enable
  force_delete = var.core_main_force_delete
  ecr_name = var.core_ecr_name
  image_mutability = var.core_image_mutability
  enable_app_bastion_host = var.core_enable_app_bastion_host

  //Kubernetes Parameters
  k8_name_space = var.core_k8_name_space
  user_service_account = var.core_user_service_account
  gateway_service_account = var.core_gateway_service_account
  article_service_account = var.core_article_service_account
  stream_service_account = var.core_stream_service_account
  notification_service_account = var.core_notification_service_account

  tags = merge(local.tags, local.environment_metadata_tags)

  /***----------------------- ***************** -----------------------------***/
  /***----------------------- DB Infrastructure------------------------------***/

  // VPC Parameters
  db_main_vpc_public_subnets   = var.core_db_main_vpc_public_subnets
  db_main_vpc_private_subnets  = var.core_db_main_vpc_private_subnets
  db_main_vpc_database_subnets = var.core_db_main_vpc_database_subnets
  db_main_vpc_enable            = var.core_db_main_vpc_enable
  db_main_vpc_cidr              = var.core_db_main_vpc_cidr
  db_main_vpc_az_count_private  = var.core_db_main_vpc_az_count_private
  db_main_vpc_az_count_database = var.core_db_main_vpc_az_count_database
  db_main_vpc_az_count_public   = var.core_db_main_vpc_az_count_public
  db_main_vpc_one_nat_gateway_per_az = var.core_db_main_vpc_one_nat_gateway_per_az
  db_main_vpc_enable_nat_gateway     = var.core_db_main_vpc_enable_nat_gateway
  db_main_vpc_single_nat_gateway     = var.core_db_main_vpc_single_nat_gateway

  //Seed ENI Parameters
  db_enable_az1_cassandra_seed = var.core_db_enable_az1_cassandra_seed
  db_enable_az2_cassandra_seed = var.core_db_enable_az2_cassandra_seed
  db_enable_cassandra_node_asg = var.core_db_enable_cassandra_node_asg
  db_main_az1_eni_private_ips = var.core_db_main_az1_eni_private_ips
  db_main_az2_eni_private_ips = var.core_db_main_az2_eni_private_ips

  //Seed Nodes Instance Parameters
  db_main_ec2_instance_ami = var.core_db_main_ec2_instance_ami
  db_main_ec2_instance_instance_type = var.core_db_main_ec2_instance_instance_type
  db_main_ec2_instance_key_name =  var.core_db_main_ec2_instance_key_name

  //ASG + LC Parameters
  db_enable_lc_cluster_node = var.core_db_enable_lc_cluster_node
  db_main_asg_min_size = var.core_db_main_asg_min_size
  db_main_asg_max_size = var.core_db_main_asg_max_size
  db_main_asg_policy_type = var.core_db_main_asg_policy_type
  db_main_asg_metric_aggregation_type = var.core_db_main_asg_metric_aggregation_type
  db_main_asg_scaling_adjustment = var.core_db_main_asg_scaling_adjustment
  db_main_asg_metric_interval_lower_bound = var.core_db_main_asg_metric_interval_lower_bound
  db_main_asg_metric_interval_upper_bound = var.core_db_main_asg_metric_interval_upper_bound

  //ELB Parameters
  db_main_seed_port_config = var.core_db_main_seed_port_config

  //Bastion Instance Parameters
  db_enable_bastion_host = var.core_db_enable_bastion_host
  db_main_bastion_instance_ami = var.core_db_main_bastion_instance_ami
  db_main_bastion_instance_instance_type = var.core_db_main_bastion_instance_instance_type
  db_main_bastion_instance_key_name = var.core_db_main_bastion_instance_key_name
  db_main_bastion_host_private_ips = var.core_db_main_bastion_host_private_ips

  /***----------------------- ***************** ----------------------------***/
  /***----------------------- S3 Configurations-----------------------------***/

  main_common_s3_force_destroy = var.core_main_common_s3_force_destroy
  main_common_s3_status = var.core_main_common_s3_status
  main_common_s3_sse_algorithm = var.core_main_common_s3_sse_algorithm
  main_common_s3_block_public_acls = var.core_main_common_s3_block_public_acls
  main_common_s3_block_public_policy = var.core_main_common_s3_block_public_policy
  main_common_s3_ignore_public_acls = var.core_main_common_s3_ignore_public_acls
  main_common_s3_restrict_public_buckets = var.core_main_common_s3_restrict_public_buckets

  user_image_s3_enable = var.core_user_image_s3_enable
  pod_logs_s3_enable = var.core_pod_logs_s3_enable


  enable_fluent_bit = var.core_enable_fluent_bit
  fluent_bit_namespace = var.core_fluent_bit_namespace
  fluent_bit_service_account = var.core_fluent_bit_service_account

}
