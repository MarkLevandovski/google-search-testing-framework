@GoogleSearch
Feature: Google Search

  Scenario Outline: Check if searched item is on first result page
    Given I open Google search page
    When I search for <searchTerm>
    Then <expectedUrl> should appear on the first page

    Examples:
      | searchTerm | expectedUrl                        |
      | equinity   | equinitytechnology.com             |
      | equinity   | glassdoor.com                      |
      | wykop      | wikipedia.org                      |
      | equinity   | this_is_url_that_wont_be_there.com |

