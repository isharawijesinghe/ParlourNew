//Assume role policy
data "aws_iam_policy_document" "stream_oidc_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRoleWithWebIdentity"]
    effect  = "Allow"

    condition {
      test     = "StringEquals"
      variable = "${replace(module.main_eks.eks_cluster_url, "https://", "")}:sub"
      values   = ["system:serviceaccount:${var.k8_name_space}:${var.stream_service_account}"]
    }

    principals {
      identifiers = [module.main_eks.eks_open_id_provider_arn]
      type        = "Federated"
    }
  }
}

// S3 Access for bucket
resource "aws_iam_policy" "stream_s3_access_policy" {
  name = "StreamAllS3BucketAccess"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action   = ["s3:ListBucket", "s3:GetBucket", "s3:GetObject", "s3:PutObject"]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}

//Define user role --> Add assume role as well
resource "aws_iam_role" "stream_node_iam_role" {
  name = "${var.stream_service_account}-${var.environment}-aws-node"
  assume_role_policy =  data.aws_iam_policy_document.stream_oidc_assume_role_policy.json
  tags = merge(var.tags,
    {
      "ServiceAccountName"      = var.stream_service_account
      "ServiceAccountNameSpace" = var.k8_name_space
    }
  )
  depends_on = [module.main_eks.eks_open_id_provider_cluster]
}

//Attach user role to policy
resource "aws_iam_role_policy_attachment" "aws_stream_cni_policy" {
  role       = aws_iam_role.stream_node_iam_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  depends_on = [aws_iam_role.stream_node_iam_role]
}

//Attach S3 Bucket Policy to Role
resource "aws_iam_role_policy_attachment" "aws_stream_s3_policy" {
  role       = aws_iam_role.stream_node_iam_role.name
  policy_arn = aws_iam_policy.stream_s3_access_policy.arn
  depends_on = [aws_iam_role.stream_node_iam_role]
}

//Kubernetes stream service account
resource "kubernetes_service_account" "eks_stream_service_account" {
  metadata {
    name = var.stream_service_account
    namespace = var.k8_name_space
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.stream_node_iam_role.arn
    }
  }
}
