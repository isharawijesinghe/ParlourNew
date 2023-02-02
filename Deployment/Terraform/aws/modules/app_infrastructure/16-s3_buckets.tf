locals {
  user_image_s3_name = "${var.environment}-parlour-user-images"
}

//User images upload s3 bucket
module "user_image_s3" {
  source  = "../s3"
  s3_bucket_name = local.user_image_s3_name
  s3_force_destroy = var.main_common_s3_force_destroy
  s3_status = var.main_common_s3_status
  s3_sse_algorithm = var.main_common_s3_sse_algorithm
  s3_block_public_acls = var.main_common_s3_block_public_acls
  s3_block_public_policy = var.main_common_s3_block_public_policy
  s3_ignore_public_acls = var.main_common_s3_ignore_public_acls
  s3_restrict_public_buckets = var.main_common_s3_restrict_public_buckets
}
