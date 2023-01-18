aws_region_main   = "us-east-1"
environment = "dev"

# Main VPC
core_main_vpc_cidr                   = "172.30.0.0/21"
core_main_vpc_az_count_private       = 2 # Need at least 2 for ALBs
core_main_vpc_az_count_database      = 2 # Need at least 2 for ALBs
core_main_vpc_az_count_public        = 2 # Need at least 2 for RDSs
core_main_vpc_public_subnets         = ["172.30.0.0/23", "172.30.2.0/23"]
core_main_vpc_private_subnets        = ["172.30.4.0/23", "172.30.6.0/23"]
core_main_vpc_database_subnets       = []
core_main_vpc_enable_nat_gateway     = true
core_main_vpc_one_nat_gateway_per_az = false
core_main_vpc_single_nat_gateway     = true
core_main_eks_cluster_enable         = true
core_main_ecr_enable                 = true
core_ecr_name                        = ["api-gateway"]
core_image_mutability                = "IMMUTABLE"
core_main_force_delete               = true
