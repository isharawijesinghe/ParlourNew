resource "aws_lb_target_group" "tg" {
  for_each = var.port_config
  name                    = var.environment
  port                    = each.key
  protocol                = each.value
  vpc_id                  = var.elb_vpc_id
  target_type             = var.elb_target_type
  deregistration_delay    = 90
  health_check {
    interval            = 30
    port                = each.key
    protocol            = "TCP"
    healthy_threshold   = var.elb_healthy_threshold
    unhealthy_threshold = 3
  }
  tags = {
    Environment = "test"
  }
}

resource "aws_lb_listener" "listener" {
  load_balancer_arn   = var.elastic_lb_arn
  for_each            = var.port_config
  port                = each.key
  protocol            = each.value
  default_action {
    target_group_arn = "${aws_lb_target_group.tg[each.key].arn}"
    type             = "forward"
  }
}

resource "aws_lb_target_group_attachment" "instance_target_group" {
  for_each = var.port_config
  target_group_arn    = "${aws_lb_target_group.tg[each.key].arn}"
  port                = each.key
  target_id           = var.elb_target_id
}

