module "core_infrastructure" {
  source = "../../../modules/app_bootstrap"

  environment = local.environment
  main_s3_force_destroy = var.core_main_s3_force_destroy
  main_dynamodb_hash_key = var.core_main_dynamodb_hash_key
  main_dynamodb_attribute_name = var.core_main_dynamodb_attribute_name
  main_dynamodb_type = var.core_main_dynamodb_type
}
