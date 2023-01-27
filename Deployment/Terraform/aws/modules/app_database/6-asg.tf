module "cassandra_cluster_node_asg" {
  count = var.enable_cassandra_node_asg ? 1 : 0
  source  = "../asg"
  asg_vpc_zone_identifier = module.main_vpc.vpc_private_subnets
  //asg_load_balancers = [module.cassandra_elb.elb_id]
  asg_launch_configuration = module.cassandra_cluster_node_lc[0].lc_name
  asg_min_size = var.main_asg_min_size
  asg_max_size = var.main_asg_max_size
  asg_policy_type = var.main_asg_policy_type
  asg_metric_aggregation_type = var.main_asg_metric_aggregation_type
  asg_scaling_adjustment = var.main_asg_scaling_adjustment
  asg_metric_interval_lower_bound = var.main_asg_metric_interval_lower_bound
  asg_metric_interval_upper_bound = var.main_asg_metric_interval_upper_bound
}