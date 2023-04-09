//Astra db variables
variable "main_database_name" {
  type = string
  default = "parlour_db"
}

variable "main_keyspace" {
  type = string
  default = "parlour"
}

variable "main_cloud_provider" {
  type = string
  default = "aws"
}

variable "main_region" {
  type = list(string)
  default = ["us-east-1"]
}

variable "enable_astra_db" {default = true}