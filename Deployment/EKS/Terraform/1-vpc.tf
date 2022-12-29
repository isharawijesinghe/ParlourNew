resource "aws_vpc" "main" {
  cidr_block = "172.30.0.0/21"

  instance_tenancy = "default"

  # Must be enabled for EFS
  enable_dns_support               = true
  enable_dns_hostnames             = true
  assign_generated_ipv6_cidr_block = false

  tags = {
    Name = "main"
  }
}

output "vpc_id" {
  value       = aws_vpc.main.id
  description = "VPC id."

  sensitive = false
}
