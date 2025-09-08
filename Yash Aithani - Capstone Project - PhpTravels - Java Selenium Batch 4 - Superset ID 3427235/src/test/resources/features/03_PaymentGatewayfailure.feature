Feature: Payment failure simulation
@negative @order4
Scenario: Negative - Payment Gateway failure (PayPal)
  Given user is on the Invoice page
  When user proceeds to PayPal payment
  And user chooses "Pay with Debit/Credit Card"
  And user enters invalid card details
  Then payment should fail and booking must not be confirmed