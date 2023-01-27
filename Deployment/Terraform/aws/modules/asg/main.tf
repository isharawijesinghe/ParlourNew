resource "aws_autoscaling_group" "asg" {
  name                 = "cassndra_as"
  vpc_zone_identifier  =  var.asg_vpc_zone_identifier
  launch_configuration =  var.asg_launch_configuration
  min_size             =  var.asg_min_size
  max_size             =  var.asg_max_size

  //load_balancers = var.asg_load_balancers

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_policy" "cassandra_as_policy" {
  name            = "cassandra_as_policy"
  adjustment_type = "ChangeInCapacity"

  autoscaling_group_name  =  aws_autoscaling_group.asg.name
  policy_type             =  "StepScaling"
  metric_aggregation_type =  "Average"

  step_adjustment {
    scaling_adjustment          = -1
    metric_interval_lower_bound = 0
    metric_interval_upper_bound = 50
  }

  step_adjustment {
    scaling_adjustment          = 1
    metric_interval_lower_bound = 50
  }
}