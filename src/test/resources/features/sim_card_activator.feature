Feature: Sim Card Activator
  Describes the behavior of the Sim card activation microservice

  Scenario: Functional sim cards activate successfully
    Given a functional sim card
    When a request to activate the sim card is submitted
    Then the sim card is activated and its state is recorded to the database

  Scenario: Broken sim cards fail to activate
    Given a broken sim card
    When a request to activate the sim card is submitted
    Then the sim card fails to activate and its state is recorded to the database