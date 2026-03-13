---
description: Establishing a connection to the DB2 database is crucial for the functionality of the GoldenEye Exquisite Rings application, as it allows the application to interact with the database to store and retrieve data.
---
# DB2 Connections

!!! note "Prerequisites"

    To establish a connection to the DB2 database, you will need to install the Test Automation Framework (TAF) extension for VS Code. This extension will allow you to create a connection to the DB2 database and run SQL queries directly from VS Code.

    You will also need the following Visual Studio Code extensions for Java to use the DB2 database in the project:

    - Debugger for Java
    - Java platform extension (or the Java Extension Pack)
    - Language Support for Java(TM) by Red Hat

Establishing a connection to the DB2 database is crucial for the functionality of the GoldenEye Exquisite Rings application, as it allows the application to interact with the database to store and retrieve data. Once the connection is established, you can run SQL queries to interact with the database and perform operations such as inserting, updating, and retrieving data.

## Setting up DB2 Connection

As mentioned in the prerequisites, TAF will need to be installed. Once TAF is installed, you can follow these steps to establish a connection to the DB2 database:

1. Open a terminal on your local device and run the following command:
    `where.exe /R c:\ db2jcc*`

    This command will search for the DB2 JDBC driver files on your local device. You should see two files in the output: `db2jcc4.jar` and `db2jcc_license_cisuz.jar`.

1. Install the DB2 JDBC drivers into your local maven repository by running the following commands in the terminal, replacing `<Path>` with the actual path to the directory where the DB2 JDBC driver files are located:

    `mvn install:install-file "-Dfile=<Path>\db2jcc4.jar" "-DgroupId=com.ibm.db2.jcc" "-DartifactId=db2jcc4" "-Dversion=11.5.8.0" "-Dpackaging=jar"`

    `mvn install:install-file "-Dfile=<Path>\db2jcc_license_cisuz.jar" "-DgroupId=com.ibm.db2.jcc" "-DartifactId=db2jcc-license-cisuz" "-Dversion=11.5.8.0" "-Dpackaging=jar"`

    !!! note "Note"

        Some installations run into issues with the `mvn` command. If you encounter an error, try running the command with `.\mvnw.cmd` instead.

1. Create a `launch.json` file within the `.vscode` directory of the project. This file is included within `.gitignore` and will not be tracked by git. In the `launch.json` file, add the following configuration:

    ```json
    {
	    "version": "0.2.0",
	    "configurations": [
	        {
	            "type": "java",
	            "name": "RingsApplication",
	            "request": "launch",
	            "mainClass": "com.goldeneye.rings.RingsApplication",
	            "projectName": "rings",
	            "env": {
	                "DB2_HOST": "192.168.54.250",
	                "DB2_PORT": "3600",
	                "DB2_DATABASE": "HL02HL2D",
	                "DB2_USERNAME": "<user>",
	                "DB2_PASSWORD": "<password>"
	            }
	        }
	    ]
    }

    ```

    In the `env` section of the configuration, replace `<user>` and `<password>` with your DB2 database username and password.

    !!! note "Note"

        Ensure the latest version of the project is pulled from the repository before making any changes.

1. Save the `launch.json` file and restart VSCode to ensure the changes take effect.

## Testing the DB2 Connection

1. Reopen the project in VSCode and open the `RingsApplication.java` file located in the `src/main/java/com/goldeneye/rings` directory.

1. Add the following code to test the DB2 connection, within `main`:

    ```java
        ... // other imports
        {
            public static void main(String[] args) {
                SpringApplication.run(RingsApplication.class, args);
            }
            @Bean
            CommandLineRunner testConnection(DataSource dataSource) {
                return args -> {
                    try (var connection = dataSource.getConnection()) {
                        System.out.println("DB2 Connection successful: " + connection.getMetaData().getDatabaseProductVersion());
                    }
                };
            }
    }
    ```

1. Save 'RingsApplication.java' and run the application using the VSCode debugger. Upon successful connection, you will see a message in the console output, along with the database product version. An error message will be displayed in the console output if the connection fails.
