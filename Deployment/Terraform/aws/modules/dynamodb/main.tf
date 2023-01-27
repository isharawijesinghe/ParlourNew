resource "aws_dynamodb_table" "dynamo_db" {
  name         = var.dynamodb_name
  billing_mode = var.dynamodb_billing_mode
  hash_key     = var.dynamodb_hash_key

  attribute {
    name = var.dynamodb_attribute_name
    type = var.dynamodb_type
  }
}