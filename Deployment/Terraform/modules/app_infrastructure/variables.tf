variable "environment" {}

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
variable "main_vpc_enable_dynamodb_endpoint" { default = false }
variable "main_vpc_enable_kms_endpoint" { default = false }
variable "main_vpc_enable_s3_endpoint" { default = false }
variable "main_vpc_enable_secretsmanager_endpoint" { default = false }
variable "main_vpc_enable_ses_endpoint" { default = false }
variable "main_vpc_enable_sns_endpoint" { default = false }
variable "main_vpc_enable_sqs_endpoint" { default = false }
variable "main_vpc_enable_ssm_endpoint" { default = false }
variable "main_vpc_enable_ssmmessages_endpoint" { default = false }
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