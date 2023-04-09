locals { main_tags = merge(var.tags) }

data "aws_region" "main" { provider = aws }
#data "aws_region" "backup" { provider = aws.backup }

data "aws_caller_identity" "main" {}
#data "aws_caller_identity" "backup" { provider = aws.backup }

data "aws_availability_zones" "main" { state = "available" }

data "astra_databases" "databaselist" {
  status = "ACTIVE"
}

output "existing_dbs" {
  value = [for db in data.astra_databases.databaselist.results : db.id]
}

output "astra_database_id" {
  value = data.astra_databases.databaselist.results[0].id
}

output "astra_database_datacenter" {
  value = data.astra_databases.databaselist.results[0].datacenters[0]
}


