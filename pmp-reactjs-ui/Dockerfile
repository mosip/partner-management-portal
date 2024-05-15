FROM node:12.18.4-alpine as build_pmp_reactjs_ui

COPY package*.json ./
RUN npm install
#Copy the working directory
COPY . ./
RUN npm run build

FROM nginx

ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME
LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}

ENV base_path=/usr/share/nginx/html

ENV i18n_path=${base_path}/i18n

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_group=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_uid=1001

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_gid=1001

# install packages and create user
RUN apt-get -y update \
&& apt-get install -y unzip wget \
&& groupadd -g ${container_user_gid} ${container_user_group} \
&& useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/sh -m ${container_user} \
&& mkdir -p /var/run/nginx /var/tmp/nginx ${i18n_path} \
&& chown -R ${container_user}:${container_user} /usr/share/nginx /var/run/nginx /var/tmp/nginx ${i18n_path} /var/cache/nginx /var/log/nginx

# set working directory for the user
WORKDIR /home/${container_user}

ADD ./nginx/nginx.conf /etc/nginx/nginx.conf

ADD default.conf /etc/nginx/conf.d/

COPY --from=build_pmp_reactjs_ui /build ${base_path}

# can be passed during Docker build as build time environment for artifactory URL
ARG artifactory_url
# environment variable to pass artifactory url, at docker runtime
ENV artifactory_url_env=${artifactory_url}

# install the needed packages including wget and a unzip library 
#RUN apt-get update -y && apt-get install -y wget && apt-get install -y unzip

# change permissions of file inside working dir
RUN chown -R ${container_user}:${container_user} /home/${container_user}

# change permissions of file inside working dir
RUN chown -R ${container_user}:${container_user} ${base_path}

# select container user for all tasks
USER ${container_user_uid}:${container_user_gid}

EXPOSE 3000

#get the pmp i18n bundle zip from artifactory
CMD wget -q --show-progress "${artifactory_url_env}"/artifactory/libs-release-local/i18n/pmp-i18n-bundle.zip -O "${i18n_path}"/pmp-i18n-bundle.zip ; \
    cd ${i18n_path} ; \
    unzip -o pmp-i18n-bundle.zip ; \
    nginx -g 'daemon off;'
