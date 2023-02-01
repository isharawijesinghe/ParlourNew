data "tls_certificate" "eks_cluster_url" {
  url = try(aws_eks_cluster.eks_cluster.identity[0].oidc[0].issuer, null)
}
