#variable "elb_subnets" {
#  type        = list(string)
#  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
#}
#
#variable "elb_internal" {
#  description = "Delete repository forcefully "
#  type        = bool
#  default     = false
#}
#
variable "environment" {}
#variable "elb_name" {}
#//variable "elb_instances_port" {}
#variable "elb_port" {}
variable "elb_healthy_threshold" {default = 2}
#variable "elb_unhealthy_threshold" {default = 2}
#variable "elb_timeout" {default = 3}
#//variable "elb_target" {}
#variable "elb_cross_zone_load_balancing" {default = true}
#variable "elb_idle_timeout" {default = 400}
#variable "elb_connection_draining" {default = true}
#variable "elb_connection_draining_timeout" {default = 400}



variable "tags" { type = map(string) }

variable "elb_vpc_id" {}
variable "elb_type" {default = "application"}
//variable "elb_enable_deletion_protection" {default = true}
#variable "lb_config" {  }
#variable "tg_config" {  }
variable "port_config" {}
variable "elb_target_type" {}
variable "elb_target_id" {}
variable "elastic_lb_arn" {}
