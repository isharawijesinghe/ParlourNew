# Set variables
variable "eks_cluster_name" {}
variable "node_group_name" {}
variable "desired_size" {default = 1}
variable "max_size" {default = 1}
variable "min_size" {default = 1}
variable "max_unavailable" {default = 1}
variable "capacity_type" {default = "ON_DEMAND"}

variable "node_group_subnet_ids" {
  type        = list(string)
  description = <<-EOF
    List of subnet IDs.
    Those should be Private Subnets if `internal == true`, and Public Subnets otherwise.
  EOF
}

variable "instance_types" {
  type        = list(string)
  description = "List of instance types"
  default = ["t2.medium"]
}