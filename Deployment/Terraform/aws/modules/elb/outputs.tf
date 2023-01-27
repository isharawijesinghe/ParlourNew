output "elb_name" {
  value = aws_lb.elastic_lb.dns_name
}

output "elb_arn" {
  value = aws_lb.elastic_lb.arn
}
output "elb_id" {
  value = aws_lb.elastic_lb.id
}