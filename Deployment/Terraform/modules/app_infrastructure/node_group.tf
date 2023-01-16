locals {
  main_node_group_name  = "${var.environment}-eks-node-group"
}

module "eks_node_group" {
  count  = var.main_eks_cluster_enable ? 1 : 0  //Disable eks cluster if required
  source  = "../../modules/node_groups"
  eks_cluster_name = local.main_eks_name
  node_group_name = local.main_node_group_name
  node_group_subnet_ids = module.main_vpc.vpc_private_subnets
}