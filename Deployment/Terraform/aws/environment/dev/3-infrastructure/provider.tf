provider "aws" {
  profile = "default"
  region = var.aws_region_main
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

provider "astra" {
  token = var.astra_client_token
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
    astra = {
      source = "datastax/astra"
      version = "2.1.5"
    }
  }
}

data "aws_eks_cluster" "cluster" {
  name = module.core_infrastructure.main_eks_cluster.eks_cluster_id
}

data "aws_eks_cluster_auth" "cluster" {
  name = module.core_infrastructure.main_eks_cluster.eks_cluster_id
}

terraform {
  backend "s3" {
    profile = "default"
    encrypt        = true
    bucket         = "dev-parlour1-terraform-state-bucket"
    key            = "dev/core-infrastructure.tfstate"
    region         = "us-east-1"
    dynamodb_table = "dev-parlour-terraform-lock-state-db"
  }
}
