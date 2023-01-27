locals {
  main_elb_name = "${var.environment}-cassandra-clsuter-nlb"

  elb_tags = {
    terraform   = "true"
  }
}

module "cassandra_elb" {
  source  = "../elb"
  elb_name        = local.main_elb_name
  elb_internal    = var.main_cassandra_nlb_internal
  elb_type        = var.main_cassandra_nlb_type
  elb_subnets     = [module.main_vpc.vpc_public_subnets[0], module.main_vpc.vpc_public_subnets[1]]
  tags = local.elb_tags
}