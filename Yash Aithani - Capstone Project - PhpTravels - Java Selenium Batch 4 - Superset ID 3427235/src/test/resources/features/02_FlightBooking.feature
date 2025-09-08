Feature: Flight Search and Booking
  As a registered user
  I want to search and book flights
  So that I can travel to my destination

  Background:
    Given user is registered and redirected to "Dashboard"

  @search
  @order3
  Scenario: Search flights with valid details
    When user navigates to "Flights" section
    And user searches flight from "DEL" to "BLR" with departure "20-09-2025"
   And user selects the first flight available
    And user fill the passenger and traveler details
    Then user should see booking confirmation
