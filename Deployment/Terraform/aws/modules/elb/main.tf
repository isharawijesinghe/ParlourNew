#resource "aws_elb" "elb" {
#  name            = var.elb_name
#  subnets         = var.elb_subnets
#  internal        = var.elb_internal
#  security_groups = var.elb_sg
#  instances       = var.elb_instances
# // load_balancer_type = "network"
#
#  listener {
#    instance_port     = var.elb_instances_port
#    instance_protocol = "TCP"
#    lb_port           = var.elb_port
#    lb_protocol       = "TCP"
#  }
#
#  health_check {
#    healthy_threshold   = var.elb_healthy_threshold
#    unhealthy_threshold = var.elb_unhealthy_threshold
#    timeout             = var.elb_timeout
#    target              = var.elb_target
#    interval            = 30
#  }
#
#  cross_zone_load_balancing   = var.elb_cross_zone_load_balancing
#  idle_timeout                = var.elb_idle_timeout
#  connection_draining         = var.elb_connection_draining
#  connection_draining_timeout = var.elb_connection_draining_timeout
#
#  tags = var.tags
#}

resource "aws_lb" "elastic_lb" {
  name               = var.elb_name
  internal           = var.elb_internal
  load_balancer_type = var.elb_type
  subnets            = var.elb_subnets

  //enable_deletion_protection = var.elb_enable_deletion_protection

  tags = var.tags
}

#resource "aws_lb_target_group" "tg" {
#  for_each                = var.port_config
#  name                    = var.environment
#  port                    = each.key
#  protocol                = each.value
#  vpc_id                  = var.elb_vpc_id
#  target_type             = var.elb_target_type
#  deregistration_delay    = 90
#  health_check {
#    interval            = 30
#    port                = each.key
#    protocol            = "TCP"
#    healthy_threshold   = var.elb_healthy_threshold
#    unhealthy_threshold = 3
#  }
#  tags = {
#    Environment = "test"
#  }
#}
#
#resource "aws_lb_listener" "listener" {
#  load_balancer_arn   = aws_lb.elastic_lb.arn
#  for_each            = var.port_config
#  port                = each.key
#  protocol            = each.value
#  default_action {
#    target_group_arn = "${aws_lb_target_group.tg[each.key].arn}"
#    type             = "forward"
#  }
#}
#
#resource "aws_lb_target_group_attachment" "tga1" {
#  for_each = var.port_config
#  target_group_arn    = "${aws_lb_target_group.tg[each.key].arn}"
#  port                = each.key
#  target_id           = var.elb_target_id
#}