Feature: SIM Card Activation Service
  As a customer service representative
  I want to activate SIM cards for customers
  So that customers can use their mobile services

  Background:
    Given the SIM card activation service is running
    And the actuator service is available

  Scenario: Successfully activate a SIM card with valid ICCID
    Given I have a valid SIM card with ICCID "1255789453849037777"
    And the customer email is "customer@example.com"
    When I submit the activation request
    Then the activation should be successful
    And the activation record should be saved with ID 1
    And I should receive a success response
    When I query the database for SIM card with ID 1
    Then I should receive the SIM card details
    And the response should contain ICCID "1255789453849037777"
    And the response should contain customer email "customer@example.com"
    And the response should show active status as "true"

  Scenario: Fail to activate a SIM card with invalid ICCID
    Given I have a valid SIM card with ICCID "8944500102198304826"
    And the customer email is "customer@example.com"
    When I submit the activation request
    Then the activation should fail due to actuator service
    And the activation record should be saved with ID 2
    And I should receive a failure response
    When I query the database for SIM card with ID 2
    Then I should receive the SIM card details
    And the response should contain ICCID "8944500102198304826"
    And the response should contain customer email "customer@example.com"
    And the response should show active status as "false"

  Scenario: Fail to activate a SIM card with empty ICCID
    Given I have an invalid SIM card with ICCID ""
    And the customer email is "customer@example.com"
    When I submit the activation request
    Then the activation should fail with validation error
    And I should receive a bad request response

  Scenario: Fail to activate a SIM card with missing customer email
    Given I have a valid SIM card with ICCID "12345678901234567890"
    And the customer email is ""
    When I submit the activation request
    Then the activation should fail with validation error
    And I should receive a bad request response

  Scenario: Retrieve all activation records
    Given there are existing activation records
    When I request all activation records
    Then I should receive a list of activation records

  Scenario: Retrieve activation record by ICCID
    Given there is an activation record for ICCID "12345678901234567890"
    When I request the activation record for ICCID "12345678901234567890"
    Then I should receive the activation record

  Scenario: Retrieve activation records by customer email
    Given there are activation records for customer "customer@example.com"
    When I request activation records for customer "customer@example.com"
    Then I should receive the customer's activation records

  Scenario: Retrieve successful activations only
    Given there are both successful and failed activation records
    When I request successful activations
    Then I should receive only successful activation records

  Scenario: Retrieve failed activations only
    Given there are both successful and failed activation records
    When I request failed activations
    Then I should receive only failed activation records

  Scenario: Prevent duplicate activation of the same ICCID
    Given there is already an activation record for ICCID "12345678901234567890"
    When I try to activate the same ICCID "12345678901234567890" again
    Then the system should return the existing activation result
    And no new activation record should be created
