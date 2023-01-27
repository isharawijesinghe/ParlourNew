variable "environment" {}
variable "main_s3_force_destroy" { default = false }

variable "main_s3_status" {
  type = string
  default = "Enabled"
}

variable "main_s3_sse_algorithm" {
  type = string
  default = "AES256"
}

variable "main_s3_block_public_acls" { default = true }
variable "main_s3_block_public_policy" { default = true }
variable "main_s3_ignore_public_acls" { default = true }
variable "main_s3_restrict_public_buckets" { default = true }

variable "main_dynamodb_billing_mode" {
  type = string
  default = "PAY_PER_REQUEST"
}

variable "main_dynamodb_hash_key" {
  type = string
}

variable "main_dynamodb_attribute_name" {
  type = string
}

variable "main_dynamodb_type" {
  type = string
}