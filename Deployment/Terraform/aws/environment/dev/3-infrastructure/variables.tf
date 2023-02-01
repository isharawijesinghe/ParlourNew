variable "environment" {}
variable "aws_region_main" {}
variable "core_project_name" { default = "core-infrastructure" } # TF_VAR_core_project_name

/***----------------------- Application Infrastructure------------------------------***/

variable "core_enable_all" {
  type        = bool
  default     = false
  description = "Internal use only. Do not set this to 'true' when calling this as downstream or the pipeline fails!"
}

# Main VPC
variable "core_main_vpc_enable" { default = true }
variable "core_main_vpc_cidr" { default = "" }
variable "core_main_vpc_az_count_private" { default = 3 }
variable "core_main_vpc_az_count_database" { default = 3 }
variable "core_main_vpc_az_count_public" { default = 3 }
variable "core_main_vpc_enable_nat_gateway" { default = false }
variable "core_main_vpc_enable_nat_instance" { default = false }
variable "core_main_vpc_one_nat_gateway_per_az" { default = false }
variable "core_main_vpc_single_nat_gateway" { default = true }
variable "core_main_vpc_reuse_nat_ips" { default = false }
variable "core_main_vpc_reserved_nat_ips" { default = [] }
variable "core_main_vpc_enable_dynamodb_endpoint" { default = false }                 # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_kms_endpoint" { default = false }                      # tflint-ignore: terraform_unused_declarations # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_s3_endpoint" { default = false }                       # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_secretsmanager_endpoint" { default = false }           # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_ses_endpoint" { default = false }                      # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_sns_endpoint" { default = false }                      # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_sqs_endpoint" { default = false }                      # tflint-ignore: terraform_unused_declarations # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_ssm_endpoint" { default = false }                      # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_enable_ssmmessages_endpoint" { default = false }              # tflint-ignore: terraform_unused_declarations # tflint-ignore: terraform_unused_declarations
variable "core_main_vpc_public_subnets" {}
variable "core_main_vpc_private_subnets" {}
variable "core_main_vpc_database_subnets" {}
variable "core_main_eks_cluster_enable" {default = false}
variable "core_main_ecr_enable" {default = false}
variable "core_ecr_name" {
  description = "The list of ecr names to create"
  type        = list(string)
  default     = null
}

variable "core_image_mutability" {
  description = "Provide image mutability"
  type        = string
  default     = "MUTABLE"
}

variable "core_main_force_delete" {
  description = "Delete repository forcefully "
  type        = bool
  default     = false
}

variable "core_enable_app_bastion_host" {}

variable "core_k8_name_space" {}
variable "core_auth_service_account" {}

/***----------------------- DB Infrastructure------------------------------***/

variable "core_db_main_vpc_enable" { default = true }
variable "core_db_main_vpc_cidr" { default = "" }
variable "core_db_main_vpc_az_count_private" { default = 2 }
variable "core_db_main_vpc_az_count_database" { default = 2 }
variable "core_db_main_vpc_az_count_public" { default = 2 }
variable "core_db_main_vpc_enable_nat_gateway" { default = true }
variable "core_db_main_vpc_enable_nat_instance" { default = false }
variable "core_db_main_vpc_one_nat_gateway_per_az" { default = false }
variable "core_db_main_vpc_single_nat_gateway" { default = true }
variable "core_db_main_vpc_public_subnets" {}
variable "core_db_main_vpc_private_subnets" {}
variable "core_db_main_vpc_database_subnets" {}

variable "core_db_enable_az1_cassandra_seed" {}
variable "core_db_enable_az2_cassandra_seed" {}
variable "core_db_enable_cassandra_node_asg" {}
variable "core_db_main_az1_eni_private_ips" {}
variable "core_db_main_az2_eni_private_ips" {}

//Seed Nodes Instance Parameters
variable "core_db_main_ec2_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "core_db_main_ec2_instance_instance_type" {default = "t2.medium"}
variable "core_db_main_ec2_instance_key_name" {default = "Ec2_Key"}

//ASG + LC Parameters
variable "core_db_enable_lc_cluster_node" {default = false} //By default only enable single seed node and no cluster node as well
variable "core_db_main_asg_min_size" {}
variable "core_db_main_asg_max_size" {}
variable "core_db_main_asg_policy_type" {}
variable "core_db_main_asg_metric_aggregation_type" {}
variable "core_db_main_asg_scaling_adjustment" {}
variable "core_db_main_asg_metric_interval_lower_bound" {}
variable "core_db_main_asg_metric_interval_upper_bound" {}

//ELB Parameters
variable "core_db_main_seed_port_config" {
  default = { 9042        =   "TCP" }
}

//Bastion Instance Parameters
variable "core_db_enable_bastion_host" {default = true}
variable "core_db_main_bastion_instance_ami" {default = "ami-089c41d63bb620fa4"}
variable "core_db_main_bastion_instance_instance_type" {default = "t2.nano"}
variable "core_db_main_bastion_instance_key_name" {default = "Ec2_Key"}
variable "core_db_main_bastion_host_private_ips" {default = null}