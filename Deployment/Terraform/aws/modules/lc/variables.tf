variable "lc_security_groups" {
  type        = list(string)
  description = "List of Availability Zones for PRIVATE Subnets. No more than 3 items."
}

variable "lc_name" {}
variable "lc_image_id" {}
variable "lc_instance_type" {}
variable "lc_key_name" {}
variable "lc_user_data" {}


