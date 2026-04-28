Feature: Reservation Management
  As a booking administrator
  I want to manage reservations
  So that I can assign guests to rooms

  Scenario: Create a new reservation
    Given the system does not have a guest with identification "1007282714"
    And the system does not have a room with code "03"
    And I create a guest with the following details:
      | identification | name            | email             |
      | 1007282714     | Natalia Serrano | natalia@gmail.com |
    And I create a room with the following details:
      | code | name             | city      | max_guests | nightly_price | available |
      | 03   | Habitación Doble | Manizales | 5          | 200000.58     | true      |
    When I create a reservation with the following details:
      | check_in         | check_out        | identification | guests_count | notes     | code |
      | 2026-05-04 15:00 | 2026-05-12 11:00 | 1007282714     | 5            | Hospedaje | 03   |
    Then the reservation should be created successfully