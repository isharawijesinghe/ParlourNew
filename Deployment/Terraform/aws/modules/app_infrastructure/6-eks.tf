locals {
  main_eks_name = "${var.environment}-eks-cluster"
}

module "main_eks" {
  //count  = var.main_eks_cluster_enable ? 1 : 0 //Disable eks cluster if required
  source  = "../eks"
  eks_name = local.main_eks_name
  eks_subnet_ids = concat(module.main_vpc.vpc_public_subnets, module.main_vpc.vpc_private_subnets)
}