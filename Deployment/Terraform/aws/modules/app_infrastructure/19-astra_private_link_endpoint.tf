resource "astra_private_link_endpoint" "example" {
  database_id   = data.astra_databases.databaselist.results[0].id
  datacenter_id = data.astra_databases.databaselist.results[0].datacenters["AWS.us-east-1"]
  endpoint_id   = aws_vpc_endpoint.astra_vpc_endpoint.id
}