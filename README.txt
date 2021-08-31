Monese Banking Challenge
    This project is an attempt to resolve the banking challenge proposed by Monese.

Resolution
    This project is divided in three layers:
        - Web : Receive and response http request
        - Service: Contains domain rules.
        - Repository: Access to database
    This project exposes two main flows:
        - GET account (with account id as query parameter and an optional amount of days for transactions)
        - POST transaction (with origin, destination and amount as query parameters)
    Technology used:
        - Spring boot including Spring Test, Spring Data, Spring Core and Spring MVC.
        - H2 for in-memory database
        - JUnit and Mockito for testing (included in Spring Test)

Considerations
    - Because is a sandbox application, all persistence happens on a in-memory database.
        Changes would be required to move this to a production environment, including the
        integration with a non volatile database.
    - Because we need to lock two different resources, deadlock is an arising problem.
        In order to work around it, we lock the accounts in ascendant id order.
    - Unit for three separated layers are performed in the /test package.
    - Integration test can be found on com.monese.banking.IntegrationTest
    - In order to limit the amount of transactions responded, we limit the amount of days in the past we search with
    the configurable parameter: default.transactions.by.account.max.amount.days.search (default 30)
    - In order to ease the testing of the app, an API POST /account?balance=<value> is provided.
        This would be disabled in a production env.

Next steps
    - Given more time, I'd perform elaborated test for deadlocks.
    - The POST new transaction API could return some information about the transaction, not only the status.
    - An integration with a non volatile database would be in order, changing the integration depending on the env.
    - I would make the  POST new transaction asynchronous, storing tasks on a queue.
        This would help us retry on failed transactions due to network issues or timeouts on locks.
    - A fixed amount of transaction could be returned in the GET accounts, with a proper pagination.
    - This project is running on port 8080. That could be changed by property.

Build, test and deploy

    In order to build the project, the following command can be run on the project root:
        mvn test
    To create the jar containing the application run on the project root:
        mvn package
    This will create a jar named `banking-0.0.1-SNAPSHOT.jar` on the folder /target.
    In order to run it, execute
        java -jar target/banking-0.0.1-SNAPSHOT.jar
    This project has been developed created with Java 11.

    An already package jar is provided with the project, in case issues building it arise.

Thanks for reading =)