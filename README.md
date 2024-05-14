# Multi-Module Project with Maven

This directory contains a multi-module project managed by [Apache Maven](https://maven.apache.org). It consists of three modules: `dal`, `model`, and `app`.

## Modules

### dal

The `dal` (Data Access Layer) module must contains the implementation of data access logic, using [Jackarta Persistence](https://jakarta.ee/specifications/persistence/3.1/).

### model

The `model` module defines the data model and domain objects used across the project. It should include entity classes, DTOs (Data Transfer Objects), and other model-related components. Both `app` and `dal` modules will use those definitions.

### app

The `app` module is the main application module that brings together the functionality provided by the `dal` and `model` modules. It must contains only the business logic, service layer, and user interface (UI) components. Do note the **main components of the UI are already implemented and must not be changed!**

## Usage

To build the project, run the following command in the root directory:

```bash
mvn clean package
```

This command will compile, test, and package all the modules.
To additionally run the application, execute:

```bash
mvn clean verify
```

This command will also start the application.

For more information about how to use maven see the [documentation](https://maven.apache.org/guides/index.html).

## License

This project is licensed under the [MIT License](https://opensource.org/license/mit).