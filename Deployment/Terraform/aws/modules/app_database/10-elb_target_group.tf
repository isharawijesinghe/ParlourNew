locals {
  elb_target_tags = {
    terraform   = "true"
    name = "${var.environment}-cassandra-target-group"
  }
}

// First seed instance
module "cassandra_elb_target_seed1" {
  count = var.enable_az1_cassandra_seed ? 1 : 0
  source  = "../elb_instance_target_group"
  environment = var.environment
  elb_vpc_id = module.main_vpc.vpc_id
  elb_target_id = module.az1_cassandra_seed[0].ec2_instance_id
  tags = local.elb_target_tags
  elastic_lb_arn = module.cassandra_elb.elb_arn
  port_config     = var.main_seed_port_config
  elb_target_type = var.main_nlb_instance_target_type
}

// Second seed instance
module "cassandra_elb_target_seed2" {
  count = var.enable_az2_cassandra_seed ? 1 : 0
  source  = "../elb_instance_target_group"
  environment = var.environment
  elb_vpc_id = module.main_vpc.vpc_id
  elb_target_id = module.az2_cassandra_seed[1].ec2_instance_id
  tags = local.elb_target_tags
  elastic_lb_arn = module.cassandra_elb.elb_arn
  port_config     = var.main_seed_port_config
  elb_target_type = var.main_nlb_instance_target_type
}

// Cluster node auto scaling group
module "cassandra_elb_target_asg" {
  count = var.enable_cassandra_node_asg ? 1 : 0
  source  = "../elb_asg_target_group"
  environment = var.environment
  elb_vpc_id = module.main_vpc.vpc_id
  tags = local.elb_target_tags
  elastic_lb_arn = module.cassandra_elb.elb_arn
  port_config     = var.main_seed_port_config
  asg_id = module.cassandra_cluster_node_asg[0].asg_id
  elb_target_type = var.main_nlb_instance_target_type
}