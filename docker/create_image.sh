#!/bin/bash
#
# BUILDING DOCKER IMAGE FROM DOCKERFILE AND GIT REPOSITORY
# JUDING - DAW GROUP 2
# Authors: Diego Guerrero, Ismael González, José Luis Toledano, Alberto Pérez
# May 2021.
#

echo
echo "JUDING - FEDERATION OF JUDO OF MADRID."
echo "Install and run application via Docker."
echo "Prerrequisites: Docker CLI must be installed."
echo "Warning: this script assumes that current directory is webapp2/docker. If not, close and change directory before continuing."
echo "Admin privileges are necessary to continue."
echo "Continue? (Y/N)"
read VAR
if [[ $VAR != Y && $VAR != y ]]
then
    echo "Exiting..."
    exit 1
fi


# Step 1: compiling Angular frontend using Node.js image and moving them into new location
echo
echo "1. Compile Angular frontend and installing Node modules... This might take a while."
cd ../frontend
sudo docker run --rm -v "$PWD":/usr/src/app -w /usr/src/app node:16.0.0 /bin/bash -c "npm install && npm run build"
cd ../backend/src/main/resources/static
rm -rf new
mkdir new
cd new
cp -r ../../../../../../frontend/dist/juding/* .


# Step 2: compiling Maven application
cd ../../../../../../backend
echo
echo "2. Compile Maven application from source code."
sudo docker run --rm -v "$PWD":/data -w /data maven:3.8.1 mvn package

# Step 3: building Docker image (using Dockerfile)
cd ..
echo
echo "3. Build Docker image using Dockerfile."
sudo docker build -t daw2021webapp2/juding:v2.0 -f ./docker/Dockerfile ./backend/target

echo
echo "It's ready! Image has been called \"daw2021webapp2/juding:v2.0\" and can be used"
echo "Prerrequisite: MySQL database called 'juding' with username 'judingUser' and password 'judingPassword_DAWG2' (or use docker-compose method)."

exit 0
