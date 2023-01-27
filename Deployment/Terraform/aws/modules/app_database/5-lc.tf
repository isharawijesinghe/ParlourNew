locals {
  main_lc_name = "${var.environment}-cassandra-cluster-node-lc"
}

module "cassandra_cluster_node_lc" {
  count = var.enable_cassandra_node_asg ? 1 : 0
  source  = "../lc"
  lc_name        = local.main_lc_name
  lc_image_id    = var.main_ec2_instance_ami //AMI id for pre build cassandra instance
  lc_instance_type = var.main_ec2_instance_instance_type //Ec2 cassandra type ex: t2.micro
  lc_key_name     = var.main_ec2_instance_key_name //Created key name in AWS
  lc_user_data     = "${file("${path.module}/files/non_seed_cluster_config.sh")}" //User data script location
  lc_security_groups = [aws_security_group.cassandra_private_sg.id] //List of security group
}