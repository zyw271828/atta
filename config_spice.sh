#!/bin/bash

# This script will download and configure SPICE.

LINK=https://panderson.me/images/SPICE-1.0.zip

echo "Downloading SPICE..."
wget -c $LINK

echo "Unzipping SPICE..."
unzip SPICE-1.0.zip -d lib/

echo "Getting Stanford CoreNLP models..."
./lib/SPICE-1.0/get_stanford_models.sh

echo "Cleaning up..."
rm ./SPICE-1.0.zip

echo "Done."
