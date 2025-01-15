Feature: SimCard Activation
  As a system user
  I want to activate a SIM card and verify its status
  So that I can ensure the functionality of the microservice

  Scenario: Successful SIM card activation
    Given the SIM card ICCID "1255789453849037777"
    When I activate the SIM card
    Then the activation should be successful
    And the SIM card status should be:
      | iccid                  | customerEmail        | active |
      | 1255789453849037777    | test@example.com     | true  |

  Scenario: Failed SIM card activation
    Given the SIM card ICCID "8944500102198304826"
    When I activate the SIM card
    Then the activation should fail
    And the SIM card status should be:
      | iccid                  | customerEmail        | active |
      | 8944500102198304826    | test@example.com     | false |