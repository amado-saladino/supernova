# Overview

Supernova framework could be a starter kit for test automation framework.

## Components

- RestUtils
- TestCase (UI)
- JSON server
- Scripts
- Data files

## JSON server

Json server could run in a container, container components reside in `json-server` folder.
This image can run in `Katacoda` playground.

## Scripts

Scripts can be injected in a web page, there is `jquery.js` to inject jQuery in a web page

## Data placeholders

Placeholders could be easily replaced by values
*e.g. user-placeholders.json* has some placeholders, these placeholders
could be replaced using `RestUtils.resolveVarsInString` method.

## Run

`gradle clean test`