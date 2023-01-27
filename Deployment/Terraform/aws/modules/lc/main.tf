resource "aws_launch_configuration" "cassandra_lc" {
  name            = var.lc_name
  image_id        = var.lc_image_id
  instance_type   = var.lc_instance_type
  key_name        = var.lc_key_name
  security_groups = var.lc_security_groups
  user_data       = var.lc_user_data
}