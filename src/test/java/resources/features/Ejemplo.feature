Feature: Búsqueda en DuckDuckGo

  @DuckDuckGo
  Scenario: Realizar una búsqueda en DuckDuckGo
    Given ingreso el término de búsqueda "Selenium WebDriver"
    When presiono el botón de búsqueda
    Then se muestran los resultados relacionados con "Selenium WebDriver"

