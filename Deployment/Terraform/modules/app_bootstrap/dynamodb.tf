locals {
  main_dynamodb_name = "${var.environment}-parlour-terraform-lock-state-db"
}

module "remote_lock_dynamodb" {
  source  = "../../modules/dynamodb"
  dynamodb_name = main_dynamodb_name
  dynamodb_billing_mode = var.main_dynamodb_billing_mode
  dynamodb_hash_key = var.main_dynamodb_hash_key
  dynamodb_attribute_name = var.main_dynamodb_attribute_name
  dynamodb_type = var.main_dynamodb_type
}
