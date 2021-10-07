## TestCode
This project works on the  http://automationpractice.com/index.php and using Java, automated test/s to verify
that summer dresses can be added to the cart and it’s possible to proceed to the Sign in section.

---
## Installation
To develop the testing code
- Selenium WebDriver
- Maven
- Cucumber
- TestNG
- Java 11 JDK 

To run the tests locally with 
- `Chrome`: install ChromeDriver from [here](http://chromedriver.chromium.org)
- `Firefox`: install GeckoDriver from [here](https://github.com/mozilla/geckodriver/releases)


## Files

- Cucumber feature file is under `/resources/features` folder
- Test results report is under`/reports` folder
- `config.properties` are defined under `src/test/resources/properties` folder.
- default browser is firefox

## Running tests ##

1. Run maven project

```console
$ mvn test
```

