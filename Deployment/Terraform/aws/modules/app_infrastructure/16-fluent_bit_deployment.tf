//Assume role policy
data "aws_iam_policy_document" "fluent_bit_oidc_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRoleWithWebIdentity"]
    effect  = "Allow"

    condition {
      test     = "StringEquals"
      variable = "${replace(module.main_eks.eks_cluster_url, "https://", "")}:sub"
      values   = ["system:serviceaccount:${var.fluent_bit_namespace}:${var.fluent_bit_service_account}"]
    }

    principals {
      identifiers = [module.main_eks.eks_open_id_provider_arn]
      type        = "Federated"
    }
  }
}

resource "kubernetes_namespace" "fluent_bit_logs" {
  metadata {
    name = var.fluent_bit_namespace
  }
}

resource "kubernetes_service_account" "fluent_bit_sa" {
  metadata {
    name      = var.fluent_bit_service_account
    namespace = kubernetes_namespace.fluent_bit_logs.metadata.0.name
    labels = {
      "app.kubernetes.io/managed-by" = "Helm"
    }
    annotations = {
      "meta.helm.sh/release-namespace" = var.fluent_bit_namespace
      "meta.helm.sh/release-name" = "fluent-bit"
      "eks.amazonaws.com/role-arn" = "${aws_iam_role.fluent_bit_node_iam_role.arn}"
    }
  }
}

resource "aws_iam_policy" "fluentbit_access_policy" {
  name_prefix = "${var.environment}-fluentbit-policy"
  description = "Iam policy for fluentbit log push"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid = "fluentBitLogManagement"
        Action = [
          "logs:PutLogEvents",
          "logs:Describe*",
          "logs:CreateLogStream",
          "logs:CreateLogGroup",
          "logs:PutRetentionPolicy"
        ]
        Effect   = "Allow"
        Resource = "*"
      }
    ]
  })
}

//Define user role --> Add assume role as well
resource "aws_iam_role" "fluent_bit_node_iam_role" {
  name = "${var.fluent_bit_service_account}-${var.environment}-aws-node"
  assume_role_policy =  data.aws_iam_policy_document.fluent_bit_oidc_assume_role_policy.json
  tags = merge(var.tags,
    {
      "ServiceAccountName"      = var.fluent_bit_service_account
      "ServiceAccountNameSpace" = var.fluent_bit_namespace
    }
  )
  depends_on = [module.main_eks.eks_open_id_provider_cluster]
}

//Attach S3 Bucket Policy to Role
resource "aws_iam_role_policy_attachment" "aws_fluent_bit_access_policy" {
  role       = aws_iam_role.fluent_bit_node_iam_role.name
  policy_arn = aws_iam_policy.fluentbit_access_policy.arn
  depends_on = [aws_iam_role.fluent_bit_node_iam_role]
}

#resource "helm_release" "fluent_bit_helm_release" {
#  repository = "https://aws.github.io/eks-charts"
#  chart      = "aws-for-fluent-bit"
#  version    = "0.1.21" # (17 nov, 2022) # https://artifacthub.io/packages/helm/aws/aws-for-fluent-bit
#  name       = "aws-fluent-bit"
#  namespace  = kubernetes_namespace.fluent_bit_logs.metadata.0.name
#  cleanup_on_fail = true
#
#  values = [
#    templatefile("${path.module}/fluentbit/aws-fluentbit.tpl", {
#      logGroupName = "${var.environment}-fluentbit"
#      region       = var.aws_region
#    })
#  ]
#
#  depends_on = [kubernetes_namespace.fluent_bit_logs]
#}

#resource "helm_release" "fluent_bit_daemonset" {
#  repository = "https://fluent.github.io/helm-charts"
#  chart      = "fluent-bit"
#  version    = "0.15.15"
#
#  name       = "fluent-bit"
#  namespace  = kubernetes_namespace.fluent_bit_logs.metadata.0.name
#  cleanup_on_fail = true
#
#  values = [
#    templatefile("${path.module}/fluentbit/fluent-bit.yaml", {
#      service_account_name = var.fluent_bit_service_account
#      region               = var.aws_region,
##
##      log_group_name_application = "applogs"
##      log_group_name_system      = "systemlogs"
##      log_retention_days         = 7
##      region                     = var.aws_region
#    }),
#  ]
#}

#resource "helm_release" "fluent_bit_helm_release" {
#  repository = "https://aws.github.io/eks-charts"
#  chart      = "aws-for-fluent-bit"
#  version    = "0.1.21" # (17 nov, 2022) # https://artifacthub.io/packages/helm/aws/aws-for-fluent-bit
#  name       = "aws-fluent-bit"
#  namespace  = kubernetes_namespace.fluent_bit_logs.metadata.0.name
#  cleanup_on_fail = true
#
#  values = [
#    templatefile("${path.module}/fluentbit/aws-fluentbit.tpl", {
#      logGroupName = "${var.environment}-fluentbit"
#      region       = var.aws_region
#    })
#  ]
#
#  depends_on = [kubernetes_namespace.fluent_bit_logs]
#}