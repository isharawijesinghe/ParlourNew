locals {
  main_node_group_name  = "${var.environment}-eks-node-group"
}

module "eks_node_group" {
  //count  = var.main_eks_cluster_enable ? 1 : 0  //Disable eks cluster if required
  source  = "../node_groups"
  eks_cluster_name = module.main_eks.eks_name
  node_group_name = local.main_node_group_name
  node_group_subnet_ids = module.main_vpc.vpc_private_subnets
  depends_on = [module.main_eks]
}