resource "aws_instance" "ec2_instance" {
  ami               = var.ec2_instance_ami
  instance_type     = var.ec2_instance_instance_type
  key_name          = var.ec2_instance_key_name
  availability_zone = var.ec2_instance_availability_zone

  network_interface {
    network_interface_id = var.ec2_instance_network_interface_id
    device_index         = 0
  }

  user_data = var.ec2_instance_user_data

  tags = var.ec2_instance_tag
}