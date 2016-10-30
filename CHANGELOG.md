# Maven Plugin Utils Changelog

## 1.4.0
Features:
* Added [ContextClassLoaderExtender](http://maven-plugin-utils.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/maven/plugin/util/classpath/ContextClassLoaderExtender.html) responsible for adding Maven project dependencies to classpath
* Added [ParameterUtils](http://maven-plugin-utils.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/maven/plugin/util/parameter/ParameterUtils.html) which provides tools to work with method parameters

## 1.3.0
Features:
* Added [ParametersLogBuilder](http://maven-plugin-utils.projects.gabrys.biz/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/parameter/ParametersLogBuilder.html) which allows to log Mojos parameters values
* Added protected method [AntFileScanner#createDirectoryScanner()](http://maven-plugin-utils.projects.gabrys.biz/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/io/AntFileScanner.html)
* Added protected method [RegexFileScanner#createFileFilter(File, String[], String[])](http://maven-plugin-utils.projects.gabrys.biz/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/io/RegexFileScanner.html)

Bugs:
* Fixed incorrect implementation of method [Time#toString()](http://maven-plugin-utils.projects.gabrys.biz/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/timer/Time.html)

[See documentation](http://maven-plugin-utils.projects.gabrys.biz/1.3.0/)

## 1.2
Features:
* Allow access to all classes from [biz.gabrys.maven.plugin.util.io](http://maven-plugin-utils.projects.gabrys.biz/1.2/apidocs/index.html?biz/gabrys/maven/plugin/util/io/package-summary.html) package

[See documentation](http://maven-plugin-utils.projects.gabrys.biz/1.2/)

## 1.1
Bugs:
* Corrected [double-checked locking](https://en.wikipedia.org/wiki/Double-checked_locking)
* Optimization of call logger instructions
* Added missing [JavaDocs](http://maven-plugin-utils.projects.gabrys.biz/1.1/apidocs/)

[See documentation](http://maven-plugin-utils.projects.gabrys.biz/1.1/)

## 1.0
Initial release.

[See documentation](http://maven-plugin-utils.projects.gabrys.biz/1.0/)