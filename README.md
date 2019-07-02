**Pact Test Example**

Pre-requisites

1.Run Pact Broker in a docker container in the local machine. It will be accessible in the url http://localhost:8090/

To generate the pact file on the consumer side run
`mvn clean install`

To publish pact files to the pact broker run
`mvn pact:publish`

To verify Pact contracts from provider side run using maven
`mvn pact:verify`

To verify Pact contracts from the provider side using junit5 run the unit test class `ProviderTest`