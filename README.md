[![CircleCI](https://dl.circleci.com/status-badge/img/gh/matteosusca/dbMigrDemo/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/matteosusca/dbMigrDemo/tree/master)

# Demo of Liquibase and Flyway

This repository contains a demo of Liquibase and Flyway, two popular database migration tools. The demo includes sample projects and configurations to show how these tools can be used to manage database schema changes.

## Table of Contents

- [Getting Started](#getting-started)
  - [Maven](#maven)
  - [Firebird embedded](#firebird-embedded)
    - [Windows](#windows)
    - [Linux](#linux)
- [Usage](#usage)
    - [Database Creation](#database-creation)
    - [Liquibase](#liquibase)
        - [Drop All](#drop-all)
        - [Update](#update)
        - [Update - Changes to Apply](#update---changes-to-apply)
        - [Rollback](#rollback)
        - [UpdateSQL](#updatesql)
    - [Flyway](#flyway)
        - [Clean](#clean)
        - [Migrate](#migrate)
        - [Migrate - Target](#migrate---target)
        - [Undo](#undo)
        - [Validate](#validate)
        - [Info](#info)
        - [Repair](#repair)

## Getting Started

To get started, you'll need to have the following software on your local machine:

- Maven
- Firebird embedded

### Maven
To install Maven, follow [this guide](https://maven.apache.org/install.html).


Once you have these dependencies installed, you can clone this repository and navigate to the project directory:

```
git clone https://github.com/matteosusca/dbMigrDemo
cd dbMigrDemo
```

### Firebird embedded

To get all the files needed to run Firebird embedded, you follow [this guide](https://ib-aid.com/download/docs/fb4migrationguide.html#_installing_embedded). Download and install [Firebird 4](https://firebirdsql.org/en/firebird-4-0/), then copy the files and structure as described in the guide to a folder named `fb`.

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

### Drop All

If you want to drop all the tables and the data you can run the following command:

```bash
mvn liquibase:dropAll
```

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

### Clean

If you want to drop all the tables and the data you can run the following command:

```bash
mvn flyway:clean
```

### Migrate

In Flyway to execute the changesets, which from now on will be called migrations, you will need to run the following command:

```bash
mvn flyway:migrate
```

This command is used to run the migrations that are not already executed in the database. In this case it will create the tables and the data.
The migrations are sql files that are stored in the `src/main/resources/db/migration` folder and are named with the following pattern: `V<version>__<description>.sql`.

Flyway will execute the migrations in the order of the version. For example, the `V1__createTable.sql` migration will be executed before the `V2__addForeignKey.sql` migration and `V1.8__insertData.sql` migration will be executed before the `V1.9__removeData.sql` migration.

In our case the migrations will be executed in this order:

```
V1.0__Create_tables.sql
|
V
V1.1__Employee_foreign_key.sql
|
V
V1.2__Fill_tables.sql
|
V
V2.0__Create_absence.sql
|
V
V2.1__Create_absence_type.sql
|
V
V2.2__Fill_absence.sql <--- We are here
```

### Migrate - Target

If you want to migrate to a specific version you can run the following command:

```bash
mvn flyway:migrate -Dflyway.target=<version>
```

This will execute all the migrations that are not already executed in the database and that have a version lower or equal to the target version.

For example, after executing the `mvn flyway:clean` command, if you run the `mvn flyway:migrate -Dflyway.target=1.2` command, the following migrations will be executed:

```
V1.0__Create_tables.sql
|
V
V1.1__Employee_foreign_key.sql
|
V
V1.2__Fill_tables.sql <--- We are here
```

### Undo

The undo functionality is not supported by Flyway Community Edition. To use this functionality you will need to use the Flyway Team or Enterprise Edition.

The documentation is available here: https://documentation.red-gate.com/fd/undo-184127463.html

### Validate

Validate is a command that is used to check if the migrations applied to the database are coherent with the migrations stored in the project.

To run this command you can use the following command:

```bash
mvn flyway:validate
```

### Info

Info is a command that is used to check the status of the migrations applied to the database.

To run this command you can use the following command:

```bash
mvn flyway:info
```

### Repair

Repair is a command that is used to repair the schema history table.

It can be used to fix the following issues:

- Remove failed migrations
- Realign the checksums, descriptions and types of the applied migrations with the ones of the available migrations
- Mark missing migrations as deleted

To run this command you can use the following command:

```bash
mvn flyway:repair
```
