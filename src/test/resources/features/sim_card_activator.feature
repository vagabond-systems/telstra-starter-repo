Feature: SIM Card Activation

  Scenario: Successful SIM card activation
    Given I submit a SIM activation request with ICCID "1255789453849037777" and email "success@example.com"
    When I query the activation status for SIM card ID 1
    Then the activation should be marked as "true"

  Scenario: Failed SIM card activation
    Given I submit a SIM activation request with ICCID "8944500102198304826" and email "fail@example.com"
    When I query the activation status for SIM card ID 2
    Then the activation should be marked as "false"
