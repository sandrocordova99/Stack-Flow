# TaskFlow: Sistema de Gestión de Proyectos y Colaboración

Una plataforma integral para la creación, organización y gestión colaborativa de proyectos y tareas.

## Descripción del Proyecto

TaskFlow es un sistema diseñado para optimizar la gestión de proyectos y la colaboración en equipo. Permite a los usuarios crear sus propios proyectos, definir tareas dentro de ellos y, lo más importante, invitar a otros usuarios a colaborar. Esta funcionalidad facilita que equipos de trabajo se organicen, asignen responsabilidades y realicen un seguimiento eficiente del progreso de cada "tarea de mierda" hasta la finalización del proyecto.

## Características Principales

* **Gestión de Usuarios:** Registro, autenticación y gestión de perfiles de usuario.
* **Creación de Proyectos:** Los usuarios pueden crear y gestionar múltiples proyectos.
* **Gestión de Tareas:** Dentro de cada proyecto, los usuarios pueden crear, asignar y gestionar tareas individuales.
* **Colaboración en Equipo:** Funcionalidad para invitar y agregar a otros usuarios a proyectos específicos, permitiendo la colaboración en tareas.
* **Interfaz Intuitiva:** Frontend desarrollado con Angular para una experiencia de usuario fluida.
* **API RESTful:** Comunicación eficiente entre el frontend y el backend.
* **Persistencia de Datos:** Almacenamiento seguro de la información en una base de datos relacional.

## Tecnologías Utilizadas

* **Backend:** Spring Boot (Java)
* **Base de Datos:** MySQL
* **Frontend:** Angular
* **Estilos:** HTML, CSS

## Cómo Empezar

Sigue estos pasos para levantar y ejecutar el proyecto localmente:

### Prerequisitos

* Java Development Kit (JDK) 17 o superior.
* Node.js y npm (para Angular).
* MySQL instalada y en ejecución.
* Maven (para Spring Boot).

### Instalación y Ejecución

#### 1. Backend (Spring Boot)

1.  **Clonar el Repositorio del Backend:**
    ```bash
    git clone  https://github.com/sandrocordova99/Stack-Flow.git
    cd tu_repositorio_backend_taskflow
    ```
    *(Reemplaza con la URL y nombre de tu repositorio de Backend si es diferente al frontend)*

2.  **Configurar la Base de Datos MySQL:**
    * Crea una base de datos MySQL, por ejemplo, `taskflow_db`.
    * Abre el archivo `src/main/resources/application.properties` (o `application.yml`).
    * Actualiza las propiedades de conexión a tu base de datos con tus credenciales:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/taskflow_db
        spring.datasource.username=tu_usuario_db
        spring.datasource.password=tu_password_db
        # spring.jpa.hibernate.ddl-auto=update # Descomenta para que Hibernate cree/actualice las tablas
        ```

3.  **Construir y Ejecutar el Backend:**
    ```bash
    mvn clean install
    java -jar target/nombre-de-tu-jar-backend.jar
    ```
    *(Ajusta `nombre-de-tu-jar-backend.jar` al nombre real del JAR que se genera en tu carpeta `target`)*

## Documentación de la API

* Si utilizaste Postman para documentar el backend, puedes indicar aquí dónde encontrar esa colección (ej. un enlace a una colección pública o la ubicación de un archivo `.json` en el repositorio).
    * Ejemplo: "La documentación detallada de la API REST del backend se encuentra en la colección de Postman disponible en `StackFlow.json`."

## Contacto

* **Email:** sandrocordova99@hotmail.com
* **Telefono:** 923323888
