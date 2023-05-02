resource "astra_private_link" "astra_private_link" {
  allowed_principals = [data.aws_caller_identity.main.arn]
  database_id        = data.astra_databases.databaselist.results[0].id
  datacenter_id      = data.astra_databases.databaselist.results[0].datacenters["AWS.us-east-1"]
}

