resource "aws_iam_role" "eks-service-account-role" {
  name = "workload_sa"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = ["sts:AssumeRoleWithWebIdentity"]
        Effect = "Allow"
        Sid    = ""
        Principal = {
          Federated = var.eks_open_id_provider_arn
        }
      },
    ]
  })

  inline_policy {
    name = "eks_service_account_policy"

    policy = jsonencode({
      Version = "2012-10-17"
      Statement = [
        {
          Action   = ["s3:GetBucket", "s3:GetObject", "s3:PutObject"]
          Effect   = "Allow"
          Resource = "*"
        },
      ]
    })
  }
}
