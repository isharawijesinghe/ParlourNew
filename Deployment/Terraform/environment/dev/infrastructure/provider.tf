provider "aws" {
  profile = "terraform"
  region = var.aws_region_main
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
    cloudflare = {
      source  = "cloudflare/cloudflare"
      version = "~> 3.0"
    }
    null = {
      version = "~> 3.0"
      source  = "hashicorp/null"
    }
    random = {
      version = "~> 3.0"
      source  = "hashicorp/random"
    }
    tls = {
      version = "~> 4.0"
      source  = "hashicorp/tls"
    }
    time = {
      version = "0.9.1"
      source  = "hashicorp/time"
    }
  }
}



terraform {
  backend "s3" {
    encrypt        = true
    bucket         = "dev-parlour-terraform-state-bucket"
    key            = "dev/core-infrastructure.tfstate"
    region         = "us-east-1"
    dynamodb_table = "dev-parlour-terraform-lock-state-db"
  }
}
