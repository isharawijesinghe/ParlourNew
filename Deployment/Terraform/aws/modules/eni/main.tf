resource "aws_network_interface" "eni" {
  description     = "az1_cassandra_seed_seed_1_eni"
  subnet_id       = var.eni_subnet_id
  private_ips     = var.eni_private_ips
  security_groups = var.eni_security_groups

  tags = var.eni_tags
}