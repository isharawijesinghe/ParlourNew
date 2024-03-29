environment = "dev"

// VPC Parameters
core_main_vpc_cidr                   = "172.30.0.0/21"
core_main_vpc_az_count_private       = 2 # Need at least 2 for ALBs
core_main_vpc_az_count_database      = 2 # Need at least 2 for ALBs
core_main_vpc_az_count_public        = 2 # Need at least 2 for RDSs
core_main_vpc_public_subnets         = ["172.30.0.0/23", "172.30.2.0/23"]
core_main_vpc_private_subnets        = ["172.30.4.0/23", "172.30.6.0/23"]
core_main_vpc_database_subnets       = []
core_main_vpc_enable_nat_gateway     = true
core_main_vpc_one_nat_gateway_per_az = false
core_main_vpc_single_nat_gateway     = true

//Seed ENI Parameters
core_enable_az1_cassandra_seed = true //Enable az1 seed node 1
core_enable_az2_cassandra_seed = false //Enable az2 seed node 2
core_enable_cassandra_node_asg = false //Enable cluster node asg
core_main_az1_eni_private_ips = ["172.30.5.10"]
core_main_az2_eni_private_ips = ["172.30.7.10"]

//Seed Nodes Instance Parameters
core_main_ec2_instance_ami = "ami-089c41d63bb620fa4" //Dabian pre build cassandra AMI
core_main_ec2_instance_instance_type = "t2.medium"
core_main_ec2_instance_key_name = "Ec2_Key"

//ASG + LC Parameters
core_enable_lc_cluster_node = true //By default only enable single seed node and no cluster node as well
core_main_asg_min_size = 1
core_main_asg_max_size = 1
core_main_asg_policy_type = "StepScaling"
core_main_asg_metric_aggregation_type = "Average"
core_main_asg_scaling_adjustment = 1
core_main_asg_metric_interval_lower_bound = 0
core_main_asg_metric_interval_upper_bound = 50

//ELB Parameters
//core_main_seed_port_config = { 9042 = "TCP" }

//Bastion Instance Parameters
core_enable_bastion_host = true
core_main_bastion_instance_ami = "ami-00874d747dde814fa" //Ubuntu server 22.04 LTS
core_main_bastion_instance_instance_type = "t1.micro"
core_main_bastion_instance_key_name= "Ec2_Key"
core_main_bastion_host_private_ips = ["172.30.1.10"]