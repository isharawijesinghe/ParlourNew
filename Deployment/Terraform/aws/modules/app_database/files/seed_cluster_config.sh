Content-Type: multipart/mixed; boundary="//"
MIME-Version: 1.0

--//
Content-Type: text/cloud-config; charset="us-ascii"
MIME-Version: 1.0
Content-Transfer-Encoding: 7bit
Content-Disposition: attachment; filename="cloud-config.txt"

#cloud-config
cloud_final_modules:
- [scripts-user, always]

--//
Content-Type: text/x-shellscript; charset="us-ascii"
MIME-Version: 1.0
Content-Transfer-Encoding: 7bit
Content-Disposition: attachment; filename="userdata.txt"

#!/bin/bash
export NODE_IP=`hostname -I`
export SEED_LIST="172.30.5.10, 172.30.7.10"
export CASSANDRA_YML="/opt/bitnami/cassandra/conf/cassandra.yaml"
export CLUSTER_NAME="devoops_cluster"
export SNITCH_TYPE="Ec2Snitch"

#sed -i "/cluster_name:/c\cluster_name: \'${CLUSTER_NAME}\'"  ${CASSANDRA_YML}
sudo sed -i "/- seeds:/c\          - seeds: \"${SEED_LIST}\""     ${CASSANDRA_YML}
sudo sed -i "/listen_address:/c\listen_address: ${NODE_IP}"       ${CASSANDRA_YML}
#sed -i "/rpc_address:/c\rpc_address: ${NODE_IP}"             ${CASSANDRA_YML}
#sed -i "/endpoint_snitch:/c\endpoint_snitch: ${SNITCH_TYPE}" ${CASSANDRA_YML}
#sed -i "/authenticator: AllowAllAuthenticator/c\authenticator: PasswordAuthenticator" ${CASSANDRA_YML}
sudo sed -i "/authenticator:/c\authenticator: AllowAllAuthenticator" ${CASSANDRA_YML}
sudo sed -i "/authorizer:/c\authorizer: AllowAllAuthorizer" ${CASSANDRA_YML}

echo 'auto_bootstrap: false' >> ${CASSANDRA_YML}

#chkconfig cassandra on

sudo /opt/bitnami/ctlscript.sh restart


#sleep 10
##
##while ! cqlsh -e 'describe cluster' ; do
##    sleep 1
##done
#
#if ! nc -z localhost 9042; then
#    /bin/echo "Hello World 1" >> /tmp/testfile1.txt
#    echo "Waiting for Cassandra to start..."
#    while ! nc -z localhost 9042; do
#       sleep 1
#    done
#    /bin/echo "Cassandra is ready." >> /tmp/testfile100.txt
#fi
#
#/bin/echo "Cassandra is ready." >> /tmp/testfile101.txt
#
#sleep 10
#
#/bin/echo "Hello World2" >> /tmp/testfile2.txt
#
#counter=1
#/bin/echo "Hello World2" >> /tmp/testfile41.txt
#while [[ $counter -le 5 ]] ; do
#    /bin/echo "Hello World2" >> /tmp/testfile4.txt
#    cqlsh -e 'describe cluster' && break
#    ((counter++))
#done
#
#/bin/echo "Hello World2" >> /tmp/testfile3.txt
#
#counter1=1
#/bin/echo "Hello World2" >> /tmp/testfile51.txt
#while [[ $counter1 -le 5 ]] ; do
#    /bin/echo "Hello World2" >> /tmp/testfile52.txt
#    cqlsh -e 'ALTER USER cassandra with PASSWORD 'password' ' && break
#    ((counter1++))
#done
#
#sudo sed -i "/authenticator: AllowAllAuthenticator/c\authenticator: PasswordAuthenticator" ${CASSANDRA_YML}
#sudo sed -i "/authorizer: AllowAllAuthorizer/c\authorizer: CassandraAuthorizer" ${CASSANDRA_YML}
#
#sudo /opt/bitnami/ctlscript.sh restart


--//--