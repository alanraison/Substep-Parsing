Feature: A feature with a scenario outline

Tags: scenarioOutlineTag

Scenario Outline: A scenario that's an outline

Given I think for <SECONDS>
Then I am <TIREDNESS_LEVEL>
So <SECONDS> means I'll be <TIREDNESS_LEVEL>

Examples:

 | SECONDS | TIREDNESS_LEVEL |
 |   5     |       OK        |
 |  120    |    EXHAUSTED    |

