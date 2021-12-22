# Overview

Supernova framework could be a starter kit for test automation framework.

## Components

- RestUtils
- TestCase (UI)
- JSON server
- Scripts
- Data files
- Extent Report
- Allure Report

## JSON server

Json server could run in a container, container components reside in `json-server` folder.

```shell
docker run -d -p 81:80 -v $PWD:/data -e "DB=users.json" json-server:v1
```

### port

container port is defined in `json-server.json` config file

### DB json file

Env. var could be defined for the container to hold the relative path to json data source

## Scripts

Scripts can be injected in a web page, there is `jquery.js` to inject jQuery in a web page

## Data placeholders

Placeholders could be easily replaced by values
*e.g. user-placeholders.json* has some placeholders, these placeholders
could be replaced using `RestUtils.resolveVarsInString` method.

## Run

`gradle clean test`

## Allure Report

It will be automatically generated in this path `build/allure-results`
The HTML format needs to be compiled, to compile these results, run this command from `build` directory:

`allure generate --clean`

> `allure-results` must be there where we're running this command.

## References

[Allure Report with Gradle and Test NG](https://github.com/allure-examples/allure-testng-gradle/blob/master/build.gradle)
