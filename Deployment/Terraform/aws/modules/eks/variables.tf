variable "eks_name" {}

variable "eks_subnet_ids" {
  type        = list(string)
  description = <<-EOF
    List of subnet IDs.
    Those should be Private Subnets if `internal == true`, and Public Subnets otherwise.
  EOF
}