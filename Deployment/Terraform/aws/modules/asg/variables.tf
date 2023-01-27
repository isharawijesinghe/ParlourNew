variable "asg_vpc_zone_identifier" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

#variable "asg_load_balancers" {
#  type        = list(string)
#  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
#}

variable "asg_launch_configuration" {}
variable "asg_min_size" {}
variable "asg_max_size" {}
variable "asg_policy_type" {}
variable "asg_metric_aggregation_type" {}
variable "asg_scaling_adjustment" {}
variable "asg_metric_interval_lower_bound" {}
variable "asg_metric_interval_upper_bound" {}

