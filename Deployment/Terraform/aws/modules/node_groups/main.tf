resource "aws_eks_node_group" "nodes_general" {
  cluster_name    = var.eks_cluster_name
  node_group_name = var.node_group_name
  node_role_arn   = aws_iam_role.nodes.arn

  subnet_ids = var.node_group_subnet_ids

  scaling_config {
    desired_size = var.desired_size
    max_size     = var.max_size
    min_size     = var.min_size
  }

  capacity_type  = var.capacity_type
  instance_types = var.instance_types
  force_update_version = false

  update_config {
    max_unavailable = var.max_unavailable
  }

  labels = {
    role = "nodes_general"
  }

  depends_on = [
    aws_iam_role_policy_attachment.amazon-eks-worker-node-policy,
    aws_iam_role_policy_attachment.amazon-eks-cni-policy,
    aws_iam_role_policy_attachment.amazon-ec2-container-registry-read-only,
  ]

}
