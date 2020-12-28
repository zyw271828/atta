#!/bin/bash

# This script will download and configure SPICE.

LINK=https://panderson.me/images/SPICE-1.0.zip

echo "Downloading..."
wget -c $LINK

echo "Unzipping..."
unzip SPICE-1.0.zip -d lib/

echo "Configuring..."
./lib/SPICE-1.0/get_stanford_models.sh

echo "Cleaning up"
rm ./SPICE-1.0.zip
