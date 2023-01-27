variable "environment" {}
variable "elb_healthy_threshold" {default = 2}
variable "tags" { type = map(string) }
variable "elb_vpc_id" {}
variable "elb_type" {default = "application"}
variable "port_config" {}
variable "elb_target_type" {}
variable "elastic_lb_arn" {}
variable "asg_id" {default = null}