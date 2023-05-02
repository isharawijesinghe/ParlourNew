provider "aws" {
  profile = "default"
  region = var.aws_region_main
}

provider "astra" {
  token = var.astra_client_token
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
    null = {
      version = "~> 3.0"
      source  = "hashicorp/null"
    }
    astra = {
      source = "datastax/astra"
      version = "2.1.5"
    }
  }
}

terraform {
  backend "s3" {
    profile = "default"
    encrypt        = true
    bucket         = "dev-parlour1-terraform-state-bucket"
    key            = "dev/core-astra-db-infrastructure.tfstate"
    region         = "us-east-1"
    dynamodb_table = "dev-parlour-terraform-lock-state-db"
  }
}
