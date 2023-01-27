# Main VPC
output "main_vpc_id" { value = module.main_vpc.vpc_id }
output "main_vpc_name" { value = module.main_vpc.vpc_name }
output "main_vpc_cidr_block" { value = module.main_vpc.vpc_cidr_block }
output "main_vpc_private_subnets_cidr_blocks" { value = module.main_vpc.vpc_private_subnets_cidr_blocks }
output "main_vpc_public_subnets" { value = module.main_vpc.vpc_public_subnets }
output "main_vpc_public_subnets_azs" { value = local.azs.main.public }
output "main_vpc_database_subnets" { value = module.main_vpc.vpc_database_subnets }
output "main_vpc_database_subnets_azs" { value = local.azs.main.database }
output "main_vpc_private_subnets" { value = module.main_vpc.vpc_private_subnets }
output "main_vpc_private_subnets_azs" { value = local.azs.main.private }
output "main_vpc_endpoint_sg_id" { value = module.main_vpc.vpc_endpoint_sg_id }
output "main_vpc_database_subnet_group" { value = module.main_vpc.vpc_database_subnet_group }
output "main_vpc_database_subnet_group_name" { value = module.main_vpc.vpc_name }
output "main_vpc_database_subnet_group_azs" { value = local.azs.main.database }




