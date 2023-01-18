provider "aws" {
  profile = "terraform"
  region  = "us-east-1"
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }

  backend "s3" {
    # Replace this with your bucket name!
    bucket         = "parlour-terraform-dev-state"
    key            = "global/s3/terraform.tfstate"
    region         = "us-east-1"
    profile = "terraform"

    # Replace this with your DynamoDB table name!
    dynamodb_table = "parlour-terraform-dev-state-locks"
    encrypt        = true
  }
}
