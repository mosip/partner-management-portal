#!/bin/bash
# Installs all PMS UI charts
## Usage: ./install.sh [kubeconfig]

if [ $# -ge 1 ] ; then
  export KUBECONFIG=$1
fi

NS=pms
CHART_VERSION=12.2.1
COPY_UTIL=../copy_cm_func.sh

echo Create $NS namespace
kubectl create ns $NS

function installing_pmp_ui() {
  echo Istio label
  kubectl label ns $NS istio-injection=enabled --overwrite
  helm repo update

  echo Copy configmaps
  $COPY_UTIL configmap global default $NS
  $COPY_UTIL configmap artifactory-share artifactory $NS
  $COPY_UTIL configmap config-server-share config-server $NS

  INTERNAL_API_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-api-internal-host})
  PMP_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-pmp-host})

  echo Installing pmp-ui
  helm -n $NS install pmp-ui mosip/pmp-ui  --set pmp.apiUrl=https://$INTERNAL_API_HOST/ --set istio.hosts=["$PMP_HOST"] --version $CHART_VERSION

  kubectl -n $NS  get deploy -o name |  xargs -n1 -t  kubectl -n $NS rollout status

  echo Installed pmp ui services

  echo "Partner management portal URL: https://$PMP_HOST/pmp-ui/"
  return 0
}

# set commands for error handling.
set -e
set -o errexit   ## set -e : exit the script if any statement returns a non-true return value
set -o nounset   ## set -u : exit the script if you try to use an uninitialised variable
set -o errtrace  # trace ERR through 'time command' and other functions
set -o pipefail  # trace ERR through pipes
installing_pmp_ui   # calling function
