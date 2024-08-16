Feature: Sim Card Activator
	Describes the behavior of the Sim Card activation microservice
	
	Scenario: Functional sim cards activate successfully
	Given a Functional Sim card
	When a request to activate sim card is submitted
	Then  the sim card is activated and its state is stored in database
	
	Scenario: Broken sim card activation failure
	Given a broken sim card
	When a request to activate a sim card is submitted
	Then the sim card fails to activate and its state is stored in database
	