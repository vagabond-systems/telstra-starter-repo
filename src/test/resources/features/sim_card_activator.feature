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


#  Scenario: Activation
#    Given Iccid is 1255789453849037777
#    When I ask the iccid number
#    Then I should be told "activated"
#
#  Scenario: Not Activation
#    Given Iccid is 8944500102198304826
#    When I ask the iccid number
#    Then I should be told "not activated"

#  Scenario Outline: Iccid activated or not
#    Given Iccid is "<iccid>"
#    When I check the activation status of "<iccid>"
#    Then I should be told "<answer>"
#
#    Examples:
#      | iccid                     |   answer          |
#      | 1255789453849037777       |   activated       |
#      | 8944500102198304826       |   not activated   |
#      | 0000000000000000000       |   not activated   |