Feature: Sim Activation
  As a Sim User
  I want to activate thru micro-services
  So that i want know success or failure

  Scenario: Successful sim activation
    Given the SIM card with ICCID "1255789453849037777"
    When I submit a activation
    Then Activation is success
    And Get activation is "true"

  Scenario: Unsuccessful sim activation
    Given the SIM card with ICCID "8944500102198304826"
    When I submit a activation
    Then Activation is unsuccess
    And Get activation is "false"