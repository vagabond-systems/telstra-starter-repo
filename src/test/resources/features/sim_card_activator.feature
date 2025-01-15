Feature: SIM Card Activation

  Scenario: Successful SIM card activation
    Given the SIM card actuator is running
    When I submit an activation request with ICCID "1255789453849037777"
    Then the activation should be successful
    And I can verify the activation status from the database

  Scenario: Failed SIM card activation
    Given the SIM card actuator is running
    When I submit an activation request with ICCID "8944500102198304826"
    Then the activation should fail
