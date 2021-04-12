# payment

payment service for the t2 store.

takes credit card info and the total amount of money and pretends to perform payment. 
if the card's checksum is set to "bad" payment always fails and if it is set to "good" the payment always succeeds.
if it is neither is fails at random. 