module "datastax-astra_db" {
  source  = "../astra_db"
  count = var.enable_astra_db ? 1 : 0
  database_name  = var.main_database_name
  keyspace       = var.main_keyspace
  cloud_provider = var.main_cloud_provider
  region         = var.main_region
}

#resource "astra_private_link" "astra_private_link" {
#  allowed_principals = [data.aws_caller_identity.main.arn]
#  database_id        = module.astra_db.
#  datacenter_id      = "a6bc9c26-e7ce-424f-84c7-0a00afb12588-1"
#}