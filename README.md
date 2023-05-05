# Demo of Liquibase and Flyway

This repository contains a demo of Liquibase and Flyway, two popular database migration tools. The demo includes sample projects and configurations to show how these tools can be used to manage database schema changes.

## Table of Contents

- [Getting Started](#getting-started)
- [Usage](#usage)

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

## Database Creation

First of all you will need to create the fdb file. To do that you will need to run the `CreateDatabase.java` class. You can do that by running the following command:

```bash
mvn exec:java -Dexec.mainClass="com.example.CreateDatabase"
```

This will create the `test.fdb` file in the `fb` folder.

## Liquibase

### Update

Now you have an empty database. Liquibase can help us to create the tables and the data. To do that you will need to run the following command in the root of the project:

```bash
mvn liquibase:update
```

This command is used to run the changesets that are not already executed in the database. In this case it will create the tables and the data.
The query that will be executed are stored in the `src/main/resources/changelog.xml` file.

This file contains links to other files that contains the queries. For example, the `src/main/resources/createTable.sql` file contains the query to create the table `PERSON` and the table `EMPLOYEE`.

Now we have executed these changesets:

```
createTable.sql
|
V
addForeignKey.sql
|
V
insertEmployee.sql
|
V
createAbsence.sql
|
V
createAbsenceType.sql
|
V
fillAbsence.sql <--- We are here
```

### Drop All

If you want to drop all the tables and the data you can run the following command:

```bash
mvn liquibase:dropAll
```

### Update - Changes to Apply

If you want to update a specific number of changesets you can run the following command:

```bash
mvn liquibase:update -Dliquibase.changesToApply=2
```

This will execute the first two changesets that are not already executed in the database.

For example, after executing the `mvn liquibase:dropAll` command, if you run the `mvn liquibase:update -Dliquibase.changesToApply=3` command, the following changesets will be executed:

```
createTable.sql
|
V
addForeignKey.sql
|
V
insertEmployee.sql <--- We are here
```

### Rollback

If you want to rollback a specific number of changesets you can run the following command:

```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=<number>
```

These changesets have to contain a rollback query. For example, the `src/main/resources/inserEmployee.sql` file contains the queries to rollback the insert of the employee.

To test it you can run the following commands:

```bash
mvn liquibase:dropAll
mvn liquibase:update -Dliquibase.changesToApply=3
```

Now you have the `EMPLOYEE` and the `PERSON` tables with the data. 

```
createTable.sql
|
V
addForeignKey.sql
|
V
insertEmployee.sql <--- We are here
```

If you run the following command:

```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=1
```

You will be back to `createTable.sql` changeset.

```
createTable.sql
|
V
addForeignKey.sql <--- We are here
```

NOTE: You cannot rollback a SQL changeset if it doesn't contain a rollback query.

### UpdateSQL

"updateSQL is a helper goal that allows you to inspect the SQL Liquibase will run while using the Maven update goal." (https://docs.liquibase.com/tools-integrations/maven/commands/maven-updatesql.html)

To run this command you can use the following command:

```bash
mvn liquibase:updateSQL
```

This command will generate a file named `migrate.sql` in the `target/liquibase` folder. This file contains all the queries that will be executed by the `mvn liquibase:update` command.

## Flyway

### Migrate

Now we will see how to do the same things with Flyway. To do that you will need to run the following command in the root of the project:

```bash
mvn flyway:migrate
```

This command is used to run the migrations that are not already executed in the database. In this case it will create the tables and the data.