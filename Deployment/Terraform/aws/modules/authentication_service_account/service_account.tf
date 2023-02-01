resource "kubernetes_service_account" "eks-service-account" {
  metadata {
    name = var.auth_service_account
    namespace = var.auth_namespace

    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.eks-service-account-role.arn
    }
  }
}