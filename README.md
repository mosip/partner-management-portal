[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mosip_partner-management-portal&branch=release-1.3.x&metric=alert_status)](https://sonarcloud.io/dashboard?id=mosip_partner-management-portal&branch=release-1.3.x)
# Partner-management-portal

## Deployment in K8 cluster with other MOSIP services:
### Pre-requisites
* Set KUBECONFIG variable to point to existing K8 cluster kubeconfig file:
  * ```
    export KUBECONFIG=~/.kube/<my-cluster.config>
    ```
### Install
  ```
    $ cd deploy/pmp-ui-v2
    $ ./install.sh
   ```
### Delete
  ```
    $ cd deploy/pmp-ui-v2
    $ ./delete.sh
   ```
### Restart
  ```
    $ cd deploy/pmp-ui-v2
    $ ./restart.sh
   ```