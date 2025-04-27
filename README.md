# Guía de Uso del Nano Framework Selenium

Este proyecto utiliza el framework Cucumber Java junto con Selenium y TestNG para pruebas automatizadas. Está diseñado para facilitar la automatización de navegadores y la escritura de pruebas de manera estructurada y eficiente.

Se ha rediseñado para mantener el uso de estas herramientas lo más limpio y natural posible, facilitando la escritura, ejecución y mantenimiento de las pruebas.

### Requisitos Previos
1. **Java Development Kit (JDK)**: Asegúrate de tener instalado JDK 8 o superior.
   - Verifica la instalación con: `java -version`
2. **Apache Maven**: Necesario para gestionar las dependencias y construir el proyecto.
   - Verifica la instalación con: `mvn -version`

## Estructura del Proyecto

La estructura del proyecto es la siguiente:
```
nano-framework-selenium/
├── .gitignore
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       └── java/
│           ├── core/
│           │   ├── locators/
│           │   │   └── ExampleLocator.java  # Localizadores de elementos en las páginas
│           │   ├── page/
│           │   │   └── ExamplePage.java     # Representación de una página web
│           │   ├── steps/
│           │   │   └── ExampleStep.java     # Definiciones de pasos para Cucumber
│           │   └── util/
│           │       ├── Action.java      # Métodos comunes para interacciones con Selenium
│           │       ├── Core.java        # Configuración base del framework
│           │       ├── Excel.java       # Manejo de datos desde archivos CSV
│           │       └── Test.java        # Clase principal para ejecutar las pruebas
│           ├── resources/
│           │   ├── features/
│           │       └── Ejemplo.feature      # Archivo de pruebas en formato Gherkin
│           ├── fixtures/
│           │   ├── test.data.csv            # Datos de prueba para el entorno de test
│           │   └── prod.data.csv            # Datos de prueba para el entorno de producción
│           └── config.properties            # Archivo de configuración del entorno
├── target/
│   ├── cucumber-reports/
│   │   └── cucumber.html                    # Reporte HTML generado por las pruebas
```

### Descripción de los directorios principales:

1. **Core**: Contiene clases de utilidad y configuración del framework:
   - `Core.java`: Configuración base del framework.
   - `Action.java`: Métodos comunes para interactuar con Selenium.
   - `Test.java`: Clase principal para ejecutar las pruebas.
   - `Excel.java`: Clase para manejar datos desde archivos CSV.

2. **Resources**:
   - **Locators**: Clases que contienen los localizadores de elementos (`ExampleLocator.java`).
   - **Pages**: Clases que representan las páginas o vistas (`ExamplePage.java`).
   - **Steps**: Definiciones de pasos para Cucumber (`ExampleStep.java`).
   - **Config**: Archivo `config.properties` para definir configuraciones específicas del entorno.
   - **Fixtures**: Archivos CSV con datos de prueba para diferentes entornos (`test.data.csv`, `prod.data.csv`).


3. **Features**: Archivos `.feature` escritos en Gherkin que definen los escenarios de prueba.

4. **Target**: Carpeta generada automáticamente que contiene los resultados de las pruebas, incluyendo reportes en HTML (`cucumber.html`).

**Notas:**
1. El archivo `pom.xml` define las dependencias y configuración del proyecto.
2. Los reportes generados por las pruebas están en `target/cucumber-reports`.

## Configuración
1. Existe dos configuraciones por ambiente `test` o `prod` 
2. Edita el archivo de configuración `src/test/resources/config.properties` para definir los valores necesarios, como:
   - `test.baseurl`: URL base para las pruebas.
   - `test.platform`: Navegador a utilizar (`IE`, `Chrome`, `Edge`, `Firefox`, `Safari`).

## Ejecución del Proyecto
1. Para ejecutar las pruebas, utiliza el siguiente comando:
   ```bash
   #Ejecucion Total
   mvn test	

   #Ejecución por tags
   mvn test -Dcucumber.filter.tags="@google"

   #Ejecución de ambiente
   mvn test -Dcucumber.filter.tags="@google" -Dcucumber.env="test" or prod
   ```
   Esto ejecutará las pruebas definidas en los archivos `.feature` utilizando Cucumber y TestNG.

2. Para generar un reporte HTML de las pruebas, verifica los resultados en:
   ```
   target/cucumber-reports/cucumber.html
   ```

Con estos pasos, deberías poder instalar y ejecutar el framework sin problemas.

## Manejo de Datos

Para manejar datos en el framework, sigue estos pasos:

1. Crea un archivo CSV con las iniciales del entorno (`test` o `prod`) seguido del nombre del archivo, por ejemplo: `test.data.csv` o `prod.data.csv`.

2. Asegúrate de que el archivo CSV esté ubicado en la carpeta `src/test/resources/fixtures/`.

3. Para cargar los datos desde el archivo CSV, utiliza el siguiente código:

```java
// Carga los datos desde el archivo CSV
List<Map<String, String>> data = Excel.getData("data.csv");

// Accede a los valores de las columnas
System.out.println("Columna1: " + data.get(0).get("columna1"));
System.out.println("Columna2: " + data.get(0).get("columna2"));
```

**Notas**
- El método `Excel.getData(String fileName)` busca automáticamente el archivo en la carpeta `fixtures`.
- Asegúrate de que las columnas en el archivo CSV tengan nombres únicos y estén correctamente formateadas.
- Puedes cambiar el nombre del archivo según el entorno (`test` o `prod`) para cargar datos específicos.