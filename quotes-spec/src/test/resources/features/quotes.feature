Feature: Application quotes

Scenario: Register a new quote
Given I have an quote payload
When I POST it to the /quotes endpoint
Then I receive a 201 status code
