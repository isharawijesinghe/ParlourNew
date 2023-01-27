resource "aws_eks_cluster" "eks_cluster" {
  name     = var.eks_name
  role_arn = aws_iam_role.eks-cluster.arn

  vpc_config {

    endpoint_private_access = false
    endpoint_public_access = true

    subnet_ids = var.eks_subnet_ids
  }

  depends_on = [
    aws_iam_role_policy_attachment.amazon-eks-cluster-policy
  ]
}


