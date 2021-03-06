<!-- ABOUT THE PROJECT. SCREENSHOTS -->
## About The Project

#### SignIn page
![SignIn page][signIn-screenshot]
#### SignUp page
![SignUp page][signUp-screenshot]
#### Profile page
![Profile page][profile-screenshot]

The goal of the project is to create a web application using Java EE and database (PostgreSQL) without connecting a modern technology stack like spring boot and hibernate.

In the application, you can create an account and upload images to the server. For each user is given a total of 10 MB of memory.

### Built With

* Java 11
* Java EE (Servlets, JSP)
* JDBC (PostgreSQL)
* Apache Maven
* Apache Tomcat 8

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

Install what you don't have.
* Java 8 JDK
    * Install java 8 suitable for your OS using [this link](https://github.com/frekele/oracle-java/releases). Here is an example for linux.
 ```sh
 wget "https://github.com/frekele/oracle-java/releases/download/8u92-b14/jdk-8u92-linux-x64.tar.gz"
 sudo mkdir /opt/jdk
 sudo tar xf jdk-8u92-linux-x64.tar.gz -C /opt/jdk
 sudo update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_92/bin/java 100
 sudo update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_92/bin/javac 100
 java -version # check your installation
 ```
* Apache Maven
 ```sh
 sudo apt install maven
 mvn -version # check your installation
 ```
* PostgreSQL
 ```sh
 sudo apt -y install postgresql
 psql --version # check your installation
 ```
* Apache Tomcat 8
 ```sh
 sudo mkdir /opt/tomcat
 wget "https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.73/bin/apache-tomcat-8.5.73.tar.gz"
 sudo tar xf apache-tomcat-8.5.73.tar.gz -C /opt/tomcat/
 ```

### Installation

1. Clone the repo
 ```sh
 git clone https://github.com/MeowthPerry/javaee_app.git
 ```
2. Change password for DB user and create tables
 ```sh
 sudo -i -u postgres # change user
 psql
 ALTER USER postgres WITH PASSWORD 'new_password';
 \i {PATH_TO_REPOSITORY}/javaee_app/src/main/resources/sql/schema.sql;
 \i {PATH_TO_REPOSITORY}/javaee_app/src/main/resources/sql/data.sql;
 \q # to quit psql
 ```
3. Change project's properties file 
 ```sh
 nano javaee_app/src/main/resources/application.properties
 ```
4. Build project using Maven
 ```sh
 mvn -f {PATH_TO_REPOSITORY}/javaee_app/pom.xml clean install
 ```
5. Copy compiled files to Tomcat
 ```sh
 sudo cp -r {PATH_TO_REPOSITORY}/javaee_app/target/ProfileApp /opt/tomcat/apache-tomcat-8.5.73/webapps/
 ```

## Usage

1. Start Tomcat
 ```sh
 sudo bash /opt/tomcat/apache-tomcat-8.5.73/bin/startup.sh
 ```
2. Three pages are available to you.
 * http://localhost:8080/ProfileApp/signIn
 * http://localhost:8080/ProfileApp/signUp
 * http://localhost:8080/ProfileApp/profile
4. If you use VirtualBox you have to crate new port forwarding rule.
5. To turn off Tomcat
 ```sh
 sudo bash /opt/tomcat/apache-tomcat-8.5.73/bin/shutdown.sh
 ```
 
 ## To contact me
 
 * Email: xab.is@yandex.ru
 * Telegram: @ahahahahahahahahahahahabibullin

[signIn-screenshot]: screenshots/signIn.png
[signUp-screenshot]: screenshots/signUp.png
[profile-screenshot]: screenshots/profile.png
