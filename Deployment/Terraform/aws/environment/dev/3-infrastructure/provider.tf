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

data "aws_eks_cluster" "cluster" {
  name = module.core_infrastructure.main_eks_cluster.eks_cluster_id
}

data "aws_eks_cluster_auth" "cluster" {
  name = module.core_infrastructure.main_eks_cluster.eks_cluster_id
}

provider "kubernetes" {
  host                   = data.aws_eks_cluster.cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority.0.data)
  token                  = data.aws_eks_cluster_auth.cluster.token
}

provider "helm" {
  kubernetes {
    host = data.aws_eks_cluster.cluster.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority.0.data)
    token = data.aws_eks_cluster_auth.cluster.token
  }
}

terraform {
  backend "s3" {
    profile = "terraform"
    encrypt        = true
    bucket         = "dev-parlour-terraform-state-bucket"
    key            = "dev/core-infrastructure.tfstate"
    region         = "us-east-1"
    dynamodb_table = "dev-parlour-terraform-lock-state-db"
  }
}
