variable "enabled" { default = true }
variable "environment" {}

variable "tags" { type = map(string) }

variable "vpc_name" {}
variable "vpc_cidr" {}

variable "vpc_enable_nat_gateway" {}
variable "vpc_one_nat_gateway_per_az" {}
variable "vpc_single_nat_gateway" {}
variable "create_database_subnet_route_table" {default = false}

variable "azs_private" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}
variable "azs_database" {
  type        = list(string)
  description = "List of Availability Zones for DATABASE Subnets. No more than 3 items."
}
variable "azs_public" {
  type        = list(string)
  description = "List of Availability Zones for PUBLIC Subnets. No more than 3 items."
}


variable "enable_shield_protection" {
  default     = false
  description = <<-EOF
    Tell the module whether the Shield Advanced Protection of EIPs should be deployed.
    Only enable is Shield Advanced Subscription is active for target AWS Account, otherwise the deployment will fail.
  EOF
}

variable "private_subnets" {
  default = null
}
variable "public_subnets" {
  default = null
}
variable "database_subnets" {
  default = null
}
