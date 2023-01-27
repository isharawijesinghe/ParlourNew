locals {
  environment = "${var.environment}-${terraform.workspace}"
}