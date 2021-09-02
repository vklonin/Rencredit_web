# Autotests for DataArt
___
## Stack
| GitHub | IntelliJ IDEA | Java | Gradle | Junit5 | Selenide |
|:------:|:----:|:----:|:------:|:------:|:--------:|
| <img src="images/GitHub.svg" width="40" height="40"> | <img src="images/IDEA.svg" width="40" height="40"> | <img src="images/JAVA.svg" width="40" height="40"> | <img src="images/Gradle.svg" width="40" height="40"> | <img src="images/Junit5.svg" width="40" height="40"> | <img src="images/Selenide.svg" width="40" height="40"> |

| Jenkins | Selenoid | Allure Report | 
|:--------:|:-------------:|:---------:|
| <img src="images/Jenkins.svg" width="40" height="40"> | <img src="images/Selenoid.svg" width="40" height="40"> | <img src="images/Allure Report.svg" width="40" height="40"> | 
___

## Run tests in Jenkins Default parameters [Jenkins](https://jenkins.autotests.cloud/job/dataart_tests/build?delay=0sec) <a href="https://www.jetbrains.com/idea/"><img src="./images/Jenkins.svg" width="30" height="30"  alt="Jenkins"/></a>

* REMOTE_DRIVER_URL (url address from selenoid or grid. default selenoid.autotests.cloud)
* ALLURE_NOTIFICATIONS_VERSION (default 3.0.1)
* credentials passed through a Jenkins job (used Owner Java library to get them from .properties)

## Telegram channel to look at results [(Telegram channel)](https://t.me/joinchat/d7-KKDr_A4FjMjM6)
*telegram integration made through 3d-parties library, settings passed through Jenkins job
![alt "Telegram"](./images/telegramBtf.png "Jenkins")

## Results in Jenkins with Allure Reports
![alt "Jenkins"](./images/JenkinsBtf.png "Jenkins")
![alt "Allure reports"](./images/allureBtf.png "Allure Reports")

## Test results video attachment example from Selenoid

![alt "Video from Selenoid"](./images/videoBtf.gif "Video from Selenoid")