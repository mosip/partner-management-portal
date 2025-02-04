# Start with a base image that includes Java 21
FROM openjdk:21-slim

# Install additional dependencies, such as Selenium, Chrome, and kubectl
USER root

RUN apt-get update && \
    apt-get install -y wget curl unzip xvfb libxi6 libgconf-2-4 chromium chromium-driver

# Install kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    chmod +x kubectl && \
    mv kubectl /usr/local/bin/kubectl

# Define build-time arguments and labels
ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME

# Set labels for metadata
LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}

# Define build-time arguments for user and group
ARG container_user=mosip
ARG container_user_group=mosip
ARG container_user_uid=1001
ARG container_user_gid=1001

# Set working directory for the user
ENV work_dir=/home/${container_user}/

# Create a new user with specified permissions
RUN groupadd -g ${container_user_gid} ${container_user_group} \
    && useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/bash -m ${container_user} -d ${work_dir} \
    && chown -R ${container_user}:${container_user} /home/${container_user}/  

# Switch to the specified user for the subsequent commands
USER ${container_user_uid}:${container_user_gid}

# Copy your application files into the container
COPY --chown=${container_user_uid}:${container_user} --chmod=771 ./entrypoint.sh ${work_dir}/entrypoint.sh
COPY --chown=${container_user}:${container_user} ./src/main/resources/ ${work_dir}/resources/
COPY --chown=${container_user_uid}:${container_user} ./target/*.jar ${work_dir}

# Set the working directory
WORKDIR /home/${container_user}/

# Entrypoint for the container
ENTRYPOINT ["/bin/bash", "-c", "./entrypoint.sh"]
