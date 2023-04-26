# Demo of Liquibase and Flyway

This repository contains a demo of Liquibase and Flyway, two popular database migration tools. The demo includes sample projects and configurations to show how these tools can be used to manage database schema changes.

## Table of Contents

- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

To get started, you'll need to have the following software on your local machine:

- Firebird embedded
- Maven

### Firebird embedded

To get all the files needed to run Firebird embedded, you follow [this guide](https://ib-aid.com/download/docs/fb4migrationguide.html#_installing_embedded). Download and install [Firebird 4](https://firebirdsql.org/en/firebird-4-0/), then copy the files and structure as described in the guide to a folder named `fb`.

### Maven
To install Maven, follow [this guide](https://maven.apache.org/install.html).


Once you have these dependencies installed, you can clone this repository and navigate to the project directory:

```
git clone https://github.com/matteosusca/dbMigrDemo
cd dbMigrDemo
```

After that, copy the folder `fb` to the root of the project.

The last step is to set the environment variable `FIREBIRD` to the path of the `fb` folder. For example, if you cloned the repository in the `C:\Users\John\Documents` folder, you would set the environment variable to `C:\Users\John\Documents\dbMigrDemo\fb`.

#### Windows

To set the environment variable on Windows, open the `Environment Variables` window by searching for it in the Start menu. Then click on `Environment Variables...` and press `New` in the `System variables` section. Set the variable name to `FIREBIRD` and the variable value to the path of the `fb` folder.

#### Linux

To set the environment variable on Linux, open the `~/.bashrc` file and add the following line:

```bash
export FIREBIRD=/path/to/fb
```

Then, run the following command to reload the `~/.bashrc` file:

```bash
source ~/.bashrc
```

Now you're ready to install the maven dependencies.
To do that, run the following command on the root of the project:

```bash
mvn clean install
```

All done! You can now run the demo.

## Usage

First of all you will need to create the fdb file. To do that you will need to run the `CreateDatabase.java` class. You can do that by running the following command:

```bash
mvn exec:java -Dexec.mainClass="com.example.CreateDatabase"
```