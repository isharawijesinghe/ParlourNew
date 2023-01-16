output "environment_metadata_tags" {
  value       = local.environment_metadata_tags
  description = <<-EOF
    This contains a map of tags that should be merged with project-level default tags, so that all (taggable)
    resources point to a deployment of Core Infrastructure and its metadata. This should aid the discovery of
    when a resource was deployed, and for whom. Mostly useful for DEV/Review Environments.
  EOF
}

output "main_vpc_id" { value = module.core_infrastructure.main_vpc_id }
output "main_vpc_name" { value = module.core_infrastructure.main_vpc_name }
output "main_vpc_cidr_block" { value = module.core_infrastructure.main_vpc_cidr_block }
output "main_vpc_cidr_block1" { value = module.core_infrastructure.main_vpc_cidr_block }
output "main_vpc_private_subnets_cidr_blocks" { value = module.core_infrastructure.main_vpc_private_subnets_cidr_blocks }
output "main_vpc_private_subnets" { value = module.core_infrastructure.main_vpc_private_subnets }
output "main_vpc_private_subnets_azs" { value = module.core_infrastructure.main_vpc_private_subnets_azs }
output "main_vpc_database_subnets" { value = module.core_infrastructure.main_vpc_database_subnets }
output "main_vpc_database_subnets_azs" { value = module.core_infrastructure.main_vpc_database_subnets_azs }
output "main_vpc_public_subnets" { value = module.core_infrastructure.main_vpc_public_subnets }
output "main_vpc_public_subnets_azs" { value = module.core_infrastructure.main_vpc_public_subnets_azs }
output "main_vpc_database_subnet_group" { value = module.core_infrastructure.main_vpc_database_subnet_group }
output "main_vpc_database_subnet_group_name" { value = module.core_infrastructure.main_vpc_database_subnet_group_name }
output "main_vpc_database_subnet_group_azs" { value = module.core_infrastructure.main_vpc_database_subnet_group_azs }
output "main_vpc_endpoint_apigw_id" { value = module.core_infrastructure.main_vpc_endpoint_apigw_id }
output "main_vpc_endpoint_sg_id" { value = module.core_infrastructure.main_vpc_endpoint_sg_id }
output "main_vpc_endpoint_sqs_id" { value = module.core_infrastructure.main_vpc_endpoint_sqs_id }


