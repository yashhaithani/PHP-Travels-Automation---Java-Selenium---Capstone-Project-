Feature: User Registration Functionality
 As a user I want to
 Register on the Application 
 So that I can Book Flight Tickets
 
 @positive
 @order1
 Scenario: Register new user with valid details
 Given user is on the "Sign Up" Page
 When user enters First Name "Karan" under Sign up
 And user enters Last Name "Rawat" under Sign up
 And user selects Select Country "United States +1" under Sign up
 And user enters Phone "7777777777" under Sign up
 And user enters EmailAddress "karan@gmail.com" under Sign up
 And user enters Password "karan123" under Sign up
 And user solves captcha manually
 And user clicks Sign up button under Sign up
 Then the user should be registered Successfully under Sign up

 @negative
 @order2
  Scenario: Registration fails with invalid email format
    Given user is on the "Sign Up" Page
    When user enters First Name "Ravi" under Sign up
    And user enters Last Name " " under Sign up
    And user selects Select Country "United States +1" under Sign up
    And user enters Phone " " under Sign up
    And user enters EmailAddress "ravi@gmail.com" under Sign up
    And user enters Password "ravi123" under Sign up
    And user solves captcha manually
    And user clicks Sign up button under Sign up
   Then user should not be registered
 




