locals {
  ecr_tags = {
    terraform   = "true"
    name = "${var.environment}-application-ecr"
  }
}


module "ecr-repo" {
  count  = var.main_ecr_enable ? 1 : 0 //Do not create ecr if disable
  source           = "../ecr"
  force_delete     = var.force_delete
  ecr_name         = var.ecr_name
  tags             = local.ecr_tags
  image_mutability = var.image_mutability
}