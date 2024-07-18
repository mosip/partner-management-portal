#!/bin/bash
# Installs all PMS UI charts
## Usage: ./install.sh [kubeconfig]

if [ $# -ge 1 ] ; then
  export KUBECONFIG=$1
fi

NS=pms
PMP_UI_CHART_VERSION=12.0.2-develop

API_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-api-internal-host})
PMP_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-pmp-host})

echo Create $NS namespace
kubectl create ns $NS

function installing_pmp_ui() {
  echo Istio label
  kubectl label ns $NS istio-injection=enabled --overwrite
  helm repo update

  echo Copy configmaps
  sed -i 's/\r$//' copy_cm.sh
  ./copy_cm.sh

  INTERNAL_API_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-api-internal-host})
  PMP_HOST=$(kubectl get cm global -o jsonpath={.data.mosip-pmp-host})

  echo Installing pmp-ui
  helm -n $NS install pmp-ui mosip/pmp-ui  --set pmp.apiUrl=https://$INTERNAL_API_HOST/ --set istio.hosts=["$PMP_HOST"] --version $PMP_UI_CHART_VERSION

  kubectl -n $NS  get deploy -o name |  xargs -n1 -t  kubectl -n $NS rollout status

  echo Installed pmp ui services

  echo "Admin portal URL: https://$PMP_HOST/pmp-ui/"
  return 0
}

# set commands for error handling.
set -e
set -o errexit   ## set -e : exit the script if any statement returns a non-true return value
set -o nounset   ## set -u : exit the script if you try to use an uninitialised variable
set -o errtrace  # trace ERR through 'time command' and other functions
set -o pipefail  # trace ERR through pipes
installing_pmp_ui   # calling function
