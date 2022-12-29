resource "aws_subnet" "public1" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "172.30.0.0/23"
  availability_zone = "us-east-1a"

  map_public_ip_on_launch = true

  tags = {
    "Name"                      = "public-us-east-1a"
    "kubernetes.io/role/elb"    = 1
    "kubernetes.io/cluster/eks" = "shared"
  }
}

resource "aws_subnet" "public2" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "172.30.2.0/23"
  availability_zone = "us-east-1b"

  map_public_ip_on_launch = true

  tags = {
    "Name"                      = "public-us-east-1b"
    "kubernetes.io/role/elb"    = 1
    "kubernetes.io/cluster/eks" = "shared"
  }
}

resource "aws_subnet" "private1" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "172.30.4.0/23"
  availability_zone = "us-east-1a"

  tags = {
    "Name"                            = "private-us-east-1a"
    "kubernetes.io/role/internal-elb" = 1
    "kubernetes.io/cluster/eks"       = "shared"
  }
}

resource "aws_subnet" "private2" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "172.30.6.0/23"
  availability_zone = "us-east-1b"

  tags = {
    "Name"                            = "private-us-east-1b"
    "kubernetes.io/role/internal-elb" = 1
    "kubernetes.io/cluster/eks"       = "shared"
  }
}
