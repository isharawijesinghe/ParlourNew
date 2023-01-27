variable "environment" {}

variable "core_main_vpc_enable" { default = true }
variable "core_main_vpc_cidr" { default = "" }
variable "core_main_vpc_az_count_private" { default = 2 }
variable "core_main_vpc_az_count_database" { default = 2 }
variable "core_main_vpc_az_count_public" { default = 2 }
variable "core_main_vpc_enable_nat_gateway" { default = true }
variable "core_main_vpc_enable_nat_instance" { default = false }
variable "core_main_vpc_one_nat_gateway_per_az" { default = false }
variable "core_main_vpc_single_nat_gateway" { default = true }
variable "core_main_vpc_public_subnets" {}
variable "core_main_vpc_private_subnets" {}
variable "core_main_vpc_database_subnets" {}

variable "core_enable_az1_cassandra_seed" {}
variable "core_enable_az2_cassandra_seed" {}
variable "core_enable_cassandra_node_asg" {}
variable "core_main_az1_eni_private_ips" {}
variable "core_main_az2_eni_private_ips" {}

//Seed Nodes Instance Parameters
variable "core_main_ec2_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "core_main_ec2_instance_instance_type" {default = "t2.medium"}
variable "core_main_ec2_instance_key_name" {default = "Ec2_Key"}

//ASG + LC Parameters
variable "core_enable_lc_cluster_node" {default = false} //By default only enable single seed node and no cluster node as well
variable "core_main_asg_min_size" {}
variable "core_main_asg_max_size" {}
variable "core_main_asg_policy_type" {}
variable "core_main_asg_metric_aggregation_type" {}
variable "core_main_asg_scaling_adjustment" {}
variable "core_main_asg_metric_interval_lower_bound" {}
variable "core_main_asg_metric_interval_upper_bound" {}

//ELB Parameters
variable "core_main_seed_port_config" {
  default = { 9042        =   "TCP" }
}

//Bastion Instance Parameters
variable "core_enable_bastion_host" {default = true}
variable "core_main_bastion_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "core_main_bastion_instance_instance_type" {default = "t2.nano"}
variable "core_main_bastion_instance_key_name" {default = "Ec2_Key"}
variable "core_main_bastion_host_private_ips" {default = "Ec2_Key"}