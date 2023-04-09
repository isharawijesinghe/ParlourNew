resource "astra_database" "main" {
  count = var.enable_astra_db ? 1 : 0
  name           = var.database_name
  keyspace       = var.keyspace
  cloud_provider = var.cloud_provider
  regions         = var.region
}