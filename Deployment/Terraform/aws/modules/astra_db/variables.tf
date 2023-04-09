variable "database_name" {
  type = string
  default = "parlour_db"
}

variable "keyspace" {
  type = string
  default = "parlour"
}

variable "cloud_provider" {
  type = string
  default = "aws"
}

variable "region" {
  type = list(string)
  default = ["us-east-1"]
}

variable "enable_astra_db" {default = true}