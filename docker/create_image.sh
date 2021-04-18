#!/bin/bash
#
# BUILDING DOCKER IMAGE FROM DOCKERFILE AND GIT REPOSITORY
# JUDING - DAW GROUP 2
# Authors: Diego Guerrero, Ismael González, José Luis Toledano, Alberto Pérez
# April 2021.
#

echo
echo "JUDING - FEDERATION OF JUDO OF MADRID."
echo "Install and run application via Docker."
echo "Prerrequisites: Docker CLI must be installed."
echo "Warning: this script assumes that current directory is webapp2. If not, close and change directory before continuing."
echo "Continue? (Y/N)"
read VAR
if [[ $VAR != Y && $VAR != y ]]
then
    echo "Exiting..."
    exit 1
fi

# Step 1: compiling Maven application
cd ../backend
echo
echo "1. Compile Maven application from source code."
sudo docker run --rm -v "$PWD":/data -w /data maven mvn package

# Step 2: building Docker image (using Dockerfile)
cp target/*.jar ../docker
cd ../docker
echo
echo "2. Build Docker image using Dockerfile."
sudo docker build -t juding .
sudo rm -rf *.jar

echo
echo "It's ready! Image has been called \"juding\" and can be used"
echo "Prerrequisite: MySQL database called 'juding' with username 'judingUser' and password 'judingPassword_DAWG2' (or use docker-compose method)."

exit 0
