Feature: Is it activated or not?
  Checking whether it is activated or not

#  Scenario: Activation
#    Given Iccid is 1255789453849037777
#    When I ask the iccid number
#    Then I should be told "activated"
#
#  Scenario: Not Activation
#    Given Iccid is 8944500102198304826
#    When I ask the iccid number
#    Then I should be told "not activated"

  Scenario Outline: Iccid activated or not
    Given Iccid is "<iccid>"
    When I check the activation status of "<iccid>"
    Then I should be told "<answer>"

    Examples:
      | iccid                     |   answer          |
      | 1255789453849037777       |   activated       |
      | 8944500102198304826       |   not activated   |
      | 0000000000000000000       |   not activated   |