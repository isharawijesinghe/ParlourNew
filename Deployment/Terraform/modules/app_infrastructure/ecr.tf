module "ecr-repo" {
  count  = var.main_ecr_enable ? 1 : 0 //Do not create ecr if disable
  source           = "../../modules/ecr"
  force_delete     = var.force_delete
  ecr_name         = var.ecr_name
  tags             = var.tags
  image_mutability = var.image_mutability
}