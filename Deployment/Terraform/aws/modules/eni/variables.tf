variable "eni_subnet_id" {}

variable "eni_private_ips" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

variable "eni_security_groups" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

variable "eni_tags" { type = map(string) }

