#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ]
then
    # Copy demos to the server
    scp -P 5177 */target/*.war dev@virtuallypreinstalled.com:tomcat/webapps/
fi