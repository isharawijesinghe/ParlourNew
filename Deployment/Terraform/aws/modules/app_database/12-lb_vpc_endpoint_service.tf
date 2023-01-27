resource "aws_vpc_endpoint_service" "application_vpc_endpoint_service" {
  acceptance_required          = false
  network_load_balancer_arns   = [module.cassandra_elb.elb_arn]
}