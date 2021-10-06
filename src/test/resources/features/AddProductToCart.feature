@CartAll @AddSummerDress
Feature:
  As a user I want to add summer dress
  Scenario Outline:Verify the ability to add a summer dress and it is possible to proceed to the sign in section
    Given user navigates automation practice url
    #Given user goes to "<categoryname>" menu item
    #Then system shows "<categoryname>" category name
    And user enters search term "<categoryname>"
    And user clicks search button
    Then results are shown on a list
    When user clicks one product details
    Then user can see product details successfully
    When user clicks add cart button
    Then system shows confirmation card
    When on confirmation card user clicks proceed to checkout button
    Then user goes to the shopping cart summary page
    When on cart summary page user clicks proceed to checkout
    And user enters "<emailaddress>" email address and "<password>" password
    When user clicks log in button
    Then check login on home page

    Examples:
      | categoryname      | emailaddress             |password      |
      | Summer Dress      |sevcen.sokmen@gmail.com   |Qwert12345.   |