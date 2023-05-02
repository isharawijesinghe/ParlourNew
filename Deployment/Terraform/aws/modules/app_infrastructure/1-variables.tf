variable "environment" {}
variable "aws_region" {}

/***----------------------- Application Infrastructure------------------------------***/

# Main VPC
variable "main_vpc_enable" {}
variable "main_vpc_cidr" {}
variable "main_vpc_public_subnets" {}
variable "main_vpc_private_subnets" {}
variable "main_vpc_database_subnets" {}
variable "main_vpc_az_count_private" {}
variable "main_vpc_az_count_database" {}
variable "main_vpc_az_count_public" {}
variable "main_vpc_enable_nat_gateway" {}
variable "main_vpc_one_nat_gateway_per_az" {}
variable "main_vpc_single_nat_gateway" {}
variable "main_eks_cluster_enable" {}
variable "main_ecr_enable" {}

variable "ecr_name" {
  description = "The list of ecr names to create"
  type        = list(string)
  default     = null
}

variable "tags" {
  description = "The key-value maps for tagging"
  type        = map(string)
  default     = {}
}
variable "image_mutability" {
  description = "Provide image mutability"
  type        = string
  default     = "MUTABLE"
}

variable "encrypt_type" {
  description = "Provide type of encryption here"
  type        = string
  default     = "KMS"
}

variable "force_delete" {
  description = "Delete repository forcefully "
  type        = bool
  default     = false
}

/***----------------------- DB Infrastructure------------------------------***/

variable "enable_db_infrastructure" { default = false }
variable "db_main_vpc_enable" { default = true }
variable "db_main_vpc_cidr" { default = "" }
variable "db_main_vpc_az_count_private" { default = 2 }
variable "db_main_vpc_az_count_database" { default = 2 }
variable "db_main_vpc_az_count_public" { default = 2 }
variable "db_main_vpc_enable_nat_gateway" { default = true }
variable "db_main_vpc_enable_nat_instance" { default = false }
variable "db_main_vpc_one_nat_gateway_per_az" { default = false }
variable "db_main_vpc_single_nat_gateway" { default = true }
variable "db_main_vpc_public_subnets" {}
variable "db_main_vpc_private_subnets" {}
variable "db_main_vpc_database_subnets" {}

variable "db_enable_az1_cassandra_seed" {}
variable "db_enable_az2_cassandra_seed" {}
variable "db_enable_cassandra_node_asg" {}
variable "db_main_az1_eni_private_ips" {}
variable "db_main_az2_eni_private_ips" {}

//Seed Nodes Instance Parameters
variable "db_main_ec2_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "db_main_ec2_instance_instance_type" {default = "t2.medium"}
variable "db_main_ec2_instance_key_name" {default = "Ec2_Key"}

//ASG + LC Parameters
variable "db_enable_lc_cluster_node" {default = false} //By default only enable single seed node and no cluster node as well
variable "db_main_asg_min_size" {}
variable "db_main_asg_max_size" {}
variable "db_main_asg_policy_type" {}
variable "db_main_asg_metric_aggregation_type" {}
variable "db_main_asg_scaling_adjustment" {}
variable "db_main_asg_metric_interval_lower_bound" {}
variable "db_main_asg_metric_interval_upper_bound" {}

//ELB Parameters
variable "db_main_seed_port_config" {
  default = { 9042        =   "TCP" }
}

//Bastion Instance Parameters
variable "db_enable_bastion_host" {default = true}
variable "enable_app_bastion_host" {default = true}
variable "db_main_bastion_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "db_main_bastion_instance_instance_type" {default = "t1.micro"}
variable "db_main_bastion_instance_key_name" {default = "Ec2_Key"}
variable "db_main_bastion_host_private_ips" {}

//Kubernetes Parameters
variable "k8_name_space" {default = "default"}
variable "user_service_account" {}
variable "gateway_service_account" {}
variable "article_service_account" {}
variable "stream_service_account" {}
variable "notification_service_account" {}


//S3 config parameters
variable "main_common_s3_force_destroy" { default = false }

variable "main_common_s3_status" {
  type = string
  default = "Enabled"
}

variable "main_common_s3_sse_algorithm" {
  type = string
  default = "AES256"
}
variable "main_common_s3_block_public_acls" { default = true }
variable "main_common_s3_block_public_policy" { default = true }
variable "main_common_s3_ignore_public_acls" { default = true }
variable "main_common_s3_restrict_public_buckets" { default = true }
variable "user_image_s3_enable" { default = true }
variable "pod_logs_s3_enable" { default = true }

variable "enable_fluent_bit" { default = true }
variable "fluent_bit_namespace" {}
variable "fluent_bit_service_account" {}

//Astra db variables
variable "main_database_name" {
  type = string
  default = "parlour_db"
}

variable "main_keyspace" {
  type = string
  default = "parlour"
}

variable "main_cloud_provider" {
  type = string
  default = "aws"
}

variable "main_region" {
  type = list(string)
  default = ["us-east-1"]
}

variable "enable_astra_db" {default = true}