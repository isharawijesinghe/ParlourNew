output "vpc_id" {
  value = module.vpc.vpc_id
}

output "vpc_name" {
  value = module.vpc.name

  depends_on = [
    # The `vpc_name` gets sometimes used as the name of specific subnet groups, because the VPC Module
    # does not explicitly publish the names of the subnets. Thus, we need to make sure this output is only returned,
    # when the subnet groups are fully deployed.
    module.vpc.database_subnet_group,
  ]
}

output "vpc_nat_public_ips" {
  value = module.vpc.nat_public_ips
}

output "vpc_private_subnets" {
  value = module.vpc.private_subnets
}

output "vpc_database_subnets" {
  value = module.vpc.database_subnets
}

output "vpc_public_subnets" {
  value = module.vpc.public_subnets
}

output "vpc_subnets" {
  value = module.vpc.azs
}

output "vpc_default_route_table_id" {
  value = module.vpc.default_route_table_id
}

output "vpc_database_subnet_group" {
  value = module.vpc.database_subnet_group
}

output "vpc_database_subnet_group_name" {
  value       = module.vpc.name
  description = <<EOF
    The vpc subnet group name is not exposed by VPC module, but it's the same as the VPC Name.
    We expose it for easiness of use in projects.
  EOF

  depends_on = [
    module.vpc.database_subnet_group,
  ]
}

output "vpc_cidr_block" {
  value = module.vpc.vpc_cidr_block
}

output "vpc_private_subnets_cidr_blocks" {
  value = module.vpc.private_subnets_cidr_blocks
}

output "vpc_private_route_table_ids" {
  value = module.vpc.private_route_table_ids
}

output "vpc_endpoint_sg_id" {
  value = element(concat(aws_security_group.vpc_endpoint_sg.*.id, [""]), 0)
}

output "vpc_endpoint_apigw_id" {
  value = try(module.vpc_endpoints.endpoints["apigw"].id, null)
}

output "vpc_endpoint_sqs_id" {
  value = try(module.vpc_endpoints.endpoints["sqs"].id, null)
}

