#!/bin/bash

#installs the pre-requisites.
set -e

echo "Downloading pre-requisites started."

#i18n bundle
echo "Downloading i18n bundle files"
wget --no-check-certificate --no-cache --no-cookies $artifactory_url_env/artifactory/libs-release-local/i18n/pmp-i18n-bundle.zip -O $i18n_path/pmp-i18n-bundle.zip

echo "unzip i18n bundle files.."
chmod 775 $i18n_path/*

cd $i18n_path
unzip -o pmp-i18n-bundle.zip
rm pmp-i18n-bundle.zip

echo "Pre-requisites download completed."

echo "Replacing public url placeholder with public url"

workingDir=$nginx_dir/html
rpCmd="s/_PUBLIC_URL_//g"
grep -rl '_PUBLIC_URL_' $workingDir | xargs sed -i $rpCmd

echo "Replacing completed."

echo "generating env-config file"

# Recreate config file
rm -rf ./env-config.js
touch ./env-config.js

# Add assignment 
echo "window._env_ = {" >> ./env-config.js

# Read each line in .env file
# Each line represents key=value pairs
while read -r line || [[ -n "$line" ]];
do
  # Split env variables by character `=`
  if printf '%s\n' "$line" | grep -q -e '='; then
    varname=$(printf '%s\n' "$line" | sed -e 's/=.*//')
    varvalue=$(printf '%s\n' "$line" | sed -e 's/^[^=]*=//')
  fi

  # Read value of current variable if exists as Environment variable
  value=$(printf '%s\n' "${!varname}")
  # Otherwise use value from .env file
  [[ -z $value ]] && value=${varvalue}
  
  # Append configuration property to JS file
  echo "  $varname: \"$value\"," >> ./env-config.js
done < .env

echo "}" >> ./env-config.js

echo "generation of env-config file completed!"

exec "$@"
