variable "dynamodb_name" {
  type = string
}

variable "dynamodb_billing_mode" {
  type = string
  default = "PAY_PER_REQUEST"
}

variable "dynamodb_hash_key" {
  type = string
}

variable "dynamodb_attribute_name" {
  type = string
}

variable "dynamodb_type" {
  type = string
}