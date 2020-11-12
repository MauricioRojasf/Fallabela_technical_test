#!/bin/bash

#### Default Configuration

trap "kill 0" SIGINT

CONCURRENCY=1
REQUESTS=10
ADDRESS="http://35.224.147.78/pi/?random_limit=10000" # by changin the random_limit number you can set the dificulty of the test for the api
HTTP_METHOD=""
BODY=""
HEADERS=""
CURL_PARAMETERS=""

show_help() {
cat << EOF
Naive Stress Test with cURL.
Usage: ./stress-test.sh [-a ADDRESS] [-c CONCURRENCY] [-r REQUESTS] [-X HTTP_METHOD] [-d BODY] [-H HEADERS]
Params:
  -a  address to be tested.
      Defaults to localhost:8080
  -c  concurrency: how many process to spawn
      Defaults to 1
  -r  number of requests per process
      Defaults to 10
  -X  http method
      Defaults to GET
  -d  body
      Defaults to NULL
  -H  headers
      Defaults to NULL
  -h  show this help text
Example:
  $ ./stressTest.sh -c 4 -r 100 (400 requests to localhost:8080)
EOF
}


#### CLI

while getopts ":a:c:r:h:X:d:H:" opt; do
  case $opt in
    a)
      ADDRESS=$OPTARG
      ;;
    c)
      CONCURRENCY=$OPTARG
      ;;
    r)
      REQUESTS=$OPTARG
      ;;
    X)
      HTTP_METHOD=$OPTARG
      CURL_PARAMETERS+=" $ADDRESS"
      ;;
    d)
      BODY=$OPTARG
      CURL_PARAMETERS+=" -d '$BODY'"
      ;;
    H)
      HEADERS=$OPTARG
      CURL_PARAMETERS+="--header '$HEADERS' "
      ;;
    h)
      show_help
      exit 0
      ;;
    \?)
      show_help >&2
      echo "Invalid argument: $OPTARG" &2
      exit 1
      ;;
  esac
done

shift $((OPTIND-1))

#### Main

if [ -n "$CURL_PARAMETERS" ]; then
  if [ -z "$HTTP_METHOD" ]; then
    CURL_PARAMETERS=" '$ADDRESS' $CURL_PARAMETERS"
  fi
else
  CURL_PARAMETERS="$ADDRESS"
fi

CURL_COMMAND="for n in {1..$REQUESTS}; do curl -s $CURL_PARAMETERS; done"
echo "curl command: $CURL_COMMAND"

for i in `seq 1 $CONCURRENCY`; do
  if [[ "$CURL_COMMAND" == *"#INCREMENT_TIMESTAMP"* ]]; then
    timestamp="$(date +%s%N)"
    CURL_COMMAND="${CURL_COMMAND//#INCREMENT_TIMESTAMP/$timestamp}"
  fi
  eval "$CURL_COMMAND" & pidlist="$pidlist $!"
done

# Execute and wait
FAIL=0
for job in $pidlist; do
  echo $job
  wait $job || let "FAIL += 1"
done

# Verify if any failed
if [ "$FAIL" -eq 0 ]; then
  echo "SUCCESS!"
else
  echo "Failed Requests: ($FAIL)"
fi