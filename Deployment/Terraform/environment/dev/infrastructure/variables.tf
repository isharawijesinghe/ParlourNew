variable "environment" {}
variable "aws_region_main" {}
variable "core_project_name" { default = "core-infrastructure" } # TF_VAR_core_project_name

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