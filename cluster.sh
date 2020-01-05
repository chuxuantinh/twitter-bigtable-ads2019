#!/bin/bash


PHASE=""
DEFAULT_ZONE="asia-east2-b"
DEFAULT_CLUSTER='chutinh-mapreduce-bigtable'
# cluster ID


function print_usage() {
  echo 'Usage: $0 [create <bucket> [<clusterName> [zone]] | delete [<clusterName>] | start  <clusterName> <jar> [many options] | proxy [<clusterName>] | chrome [<clusterName>] ]'
  echo ""
  echo "create - builds a 16 core cluster on 4 machines with a 2 core controller"
  echo "will also download 5 files to /tmp and copy them up to your bucket."
  echo ""
  echo "delete - will get stop and delete your cluster"
  echo ""
  echo "start - will submit a new job against an existing cluster"
  echo ""
  echo 'ssh - ssh & proxy to master'
  echo ''
  echo 'chrome - Launch chrome on a Mac'
  echo ''
  echo "Default Cluster name: ${DEFAULT_CLUSTER}"
  echo "Default Zone: ${DEFAULT_ZONE}"
  echo ''
  echo "Note - this script provides some help in using Google Cloud Dataproc - as you learn the gcloud"
  echo "commands, you can skip this script."
}

if [ $# = 0 ]; then
  print_usage
  exit
fi

COMMAND=$1
case $COMMAND in
  # usage flags
  --help|-help|-h)
    print_usage
    exit
    ;;

create)   # create <bucket> [<clusterName> [zone]]

  if (( $# < 2 )); then
    print_usage
    exit
  fi
  ZONE=${4:-$DEFAULT_ZONE}
  CLUSTER="${3:-$DEFAULT_CLUSTER}"

  gcloud dataproc clusters create "chutinh-mapreduce-bigtable" \
    --bucket "chutinh-bucket" \
    --num-workers 2 \
    --zone asia-east1-a \
    --master-machine-type n1-standard-4 \
    --worker-machine-type n1-standard-4
  ;;

delete)  # delete [<clusterName>]

  CLUSTER="${2:-$DEFAULT_CLUSTER}"
  gcloud -q dataproc clusters delete "$CLUSTER"
  ;;

start)  # start [<clusterName>]

  CLUSTER="${2:-$DEFAULT_CLUSTER}"

  TARGET="WordCount-$(date +%s)"
  gcloud dataproc jobs submit hadoop --cluster "$CLUSTER" \
    --jar target/wordcount-mapreduce-0-SNAPSHOT-jar-with-dependencies.jar \
    -- wordcount-hbase \
    gs://chutinh-buckket/tweets \
    "${TARGET}"
    echo "Output table is: ${TARGET}"
  ;;

ssh)  # ssh [<clusterName>]

  CLUSTER="${2:-$DEFAULT_CLUSTER}"
  MASTER="${CLUSTER}-m"

# --ssh-flag='-N' --ssh-flag='-n'

  gcloud compute ssh --ssh-flag='-D 1080'  "${MASTER}"
  ;;

chrome) # chrome [<clusterName>]

  CLUSTER="${2:-$DEFAULT_CLUSTER}"

  MASTER="${CLUSTER}-m"

  '/Applications/Google Chrome.app/Contents/MacOS/Google Chrome' \
    --proxy-server="socks5://localhost:1080" \
    --host-resolver-rules="MAP * 0.0.0.0 , EXCLUDE localhost" \
    --user-data-dir="/tmp/" \
    --incognito \
    --disable-plugins-discovery \
    --disable-plugins \
    "http://${MASTER}:8088"
  ;;

esac
