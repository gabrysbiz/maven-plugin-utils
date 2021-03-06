# Maven Plugin Utils Changelog

## 3.0.0
Features:
* Set compatibility with Java 11+

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/LATEST/)

## 2.0.0
Features:
* Set compatibility with Java 6+
* Set compatibility with Maven 3+
* Renamed `Time` class to [TimeSpan](https://gabrysbiz.github.io/maven-plugin-utils/2.0.0/apidocs/index.html?biz/gabrys/maven/plugin/util/timer/TimeSpan.html)

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/2.0.0/)

## 1.4.0
Features:
* Added [ContextClassLoaderExtender](https://gabrysbiz.github.io/maven-plugin-utils/1.4.0/apidocs/index.html?biz/gabrys/maven/plugin/util/classpath/ContextClassLoaderExtender.html) responsible for adding Maven project dependencies to classpath
* Added [ParameterUtils](https://gabrysbiz.github.io/maven-plugin-utils/1.4.0/apidocs/index.html?biz/gabrys/maven/plugin/util/parameter/ParameterUtils.html) which provides tools to work with method parameters
* Separation of the seconds and milliseconds in the [Time#toString()](https://gabrysbiz.github.io/maven-plugin-utils/1.4.0/apidocs/index.html?biz/gabrys/maven/plugin/util/timer/Time.html) method

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/1.4.0/)

## 1.3.0
Features:
* Added [ParametersLogBuilder](https://gabrysbiz.github.io/maven-plugin-utils/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/parameter/ParametersLogBuilder.html) which allows to log Mojos parameters values
* Added protected method [AntFileScanner#createDirectoryScanner()](https://gabrysbiz.github.io/maven-plugin-utils/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/io/AntFileScanner.html)
* Added protected method [RegexFileScanner#createFileFilter(File, String[], String[])](https://gabrysbiz.github.io/maven-plugin-utils/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/io/RegexFileScanner.html)

Bugs:
* Fixed incorrect implementation of method [Time#toString()](https://gabrysbiz.github.io/maven-plugin-utils/1.3.0/apidocs/index.html?biz/gabrys/maven/plugin/util/timer/Time.html)

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/1.3.0/)

## 1.2
Features:
* Allow access to all classes from [biz.gabrys.maven.plugin.util.io](https://gabrysbiz.github.io/maven-plugin-utils/1.2/apidocs/index.html?biz/gabrys/maven/plugin/util/io/package-summary.html) package

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/1.2/)

## 1.1
Bugs:
* Corrected [double-checked locking](https://en.wikipedia.org/wiki/Double-checked_locking)
* Optimization of call logger instructions
* Added missing [JavaDocs](https://gabrysbiz.github.io/maven-plugin-utils/1.1/apidocs/)

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/1.1/)

## 1.0
Initial release.

[See documentation](https://gabrysbiz.github.io/maven-plugin-utils/1.0/)