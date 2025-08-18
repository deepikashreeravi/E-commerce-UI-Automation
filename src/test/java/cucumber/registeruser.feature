@tag
Feature: User Registration

  Background:
    Given the user is on the home page

  @tag1
  Scenario Outline: Register a new user successfully
    Given the user is on the home page
    When the user clicks on the "Signup / Login" button
    And the user enters a new username "<username>" and email address "<email>"
    And the user fills in the registration details
    Then the user should see a message "ACCOUNT CREATED!"

    Examples:
      | username | email                        |
      | deepika  | deepikashree.r@example.com   |
      | tum      | deepikashree.tum@example.com |

  @tag2
  Scenario Outline: Register a new user with invalid data
    Given the user is on the home page
    When the user clicks on the "Signup / Login" button
    And the user enters a new username "<username>" and email address "<email>"
    Then the user should see an error message "Email Address already exist!" or "Invalid email address"

    Examples:
      | username | email                    |
      | deepika  | deepikashree.r@example.com |
      | testuser | invalid-email-format       |