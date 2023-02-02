output "eks_name" {
  value = aws_eks_cluster.eks_cluster.name
}

output "eks_cluster_url" {
  value = try(aws_eks_cluster.eks_cluster.identity[0].oidc[0].issuer, null)
}

output "eks_open_id_provider_arn" {
  value = aws_iam_openid_connect_provider.eks-cluster.arn
}

output "eks_open_id_provider_cluster" {
  value = aws_iam_openid_connect_provider.eks-cluster
}

output "eks_open_id_provider_cluster_url" {
  value = aws_iam_openid_connect_provider.eks-cluster.url
}

output "eks_cluster_id" {
  value = aws_eks_cluster.eks_cluster.id
}
