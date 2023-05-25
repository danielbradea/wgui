#!/bin/bash

mvn clean package
cp ./target/*.jar ./docker/app.jar
