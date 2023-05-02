variable "environment" {}

variable "tags" {
  description = "The key-value maps for tagging"
  type        = map(string)
  default     = {}
}

// VPC Parameters
variable "main_vpc_enable" {default = true}
variable "main_vpc_cidr" {default = "172.30.0.0/21"}
variable "main_vpc_public_subnets" {default = ["172.30.0.0/23", "172.30.2.0/23"]}
variable "main_vpc_private_subnets" {default = ["172.30.4.0/23", "172.30.6.0/23"]}
variable "main_vpc_database_subnets" {default = []}
variable "main_vpc_az_count_private" {default = 2}
variable "main_vpc_az_count_database" {default = 2}
variable "main_vpc_az_count_public" {default = 2}
variable "main_vpc_enable_nat_gateway" {default = true}
variable "main_vpc_one_nat_gateway_per_az" {default = false}
variable "main_vpc_single_nat_gateway" {default = true}

//Seed ENI Parameters
variable "enable_az1_cassandra_seed" {default = false} //By default only enable single seed node
variable "enable_az2_cassandra_seed" {default = false}
variable "enable_cassandra_node_asg" {default = false}
variable "main_az1_eni_private_ips" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

variable "main_az2_eni_private_ips" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

variable "main_bastion_host_private_ips" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

//Seed Nodes Instance Parameters
variable "main_ec2_instance_ami" {}
variable "main_ec2_instance_instance_type" {}
variable "main_ec2_instance_key_name" {}

//ASG + LC Parameters
variable "enable_lc_cluster_node" {default = false} //By default only enable single seed node and no cluster node as well
variable "main_asg_min_size" {}
variable "main_asg_max_size" {}
variable "main_asg_policy_type" {}
variable "main_asg_metric_aggregation_type" {}
variable "main_asg_scaling_adjustment" {}
variable "main_asg_metric_interval_lower_bound" {}
variable "main_asg_metric_interval_upper_bound" {}

//ELB Parameters
variable "main_cassandra_nlb_internal" {default = false}
variable "main_cassandra_nlb_type" {default = "network"}
variable "main_nlb_instance_target_type" {default = "instance"}
variable "main_seed_port_config" {
  default = {
    9042        =   "TCP"
  }
}


//Bastion Instance Parameters
variable "enable_bastion_host" {default = true}
variable "main_bastion_instance_ami" {}
variable "main_bastion_instance_instance_type" {}
variable "main_bastion_instance_key_name" {}
variable "main_bastion_instance_network_interface_id" {default = null}
