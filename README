# SistemaEnvios

## Descripción del proyecto

**SistemaEnvios** es una aplicación desarrollada en Java que permite gestionar el proceso de envío de paquetes. Su propósito es facilitar el registro, consulta y seguimiento de envíos, organizando la información de cada paquete y controlando los cambios en su estado.

El proyecto está desarrollado aplicando los principios de la Programación Orientada a Objetos (POO), buenas prácticas de programación y patrones de diseño.

## Objetivo

Desarrollar un sistema que permita administrar envíos de manera organizada, facilitando el registro de paquetes, la gestión de sus características y el seguimiento de su estado durante el proceso de entrega.

## Funcionalidades principales

- Registro y gestión de envíos.
- Manejo de diferentes tipos de envío: estándar, express e internacional.
- Validación de los datos de cada paquete.
- Cálculo de costos de seguro según el tipo de envío.
- Seguimiento de los estados del envío.
- Gestión de estados como registrado, en ruta, entregado y devuelto.
- Almacenamiento de información mediante una base de datos MySQL.

## Tecnologías utilizadas

- **Java:** lenguaje principal de desarrollo.
- **MySQL:** sistema de gestión de base de datos.
- **JDBC:** conexión entre Java y MySQL.
- **NetBeans / Visual Studio Code:** entornos de desarrollo.
- **Git y GitHub:** control de versiones y almacenamiento del código fuente.

## Estructura del proyecto

El proyecto está organizado en paquetes para separar las responsabilidades:

- `conexion`: administra la conexión con la base de datos.
- `dao`: define las operaciones de acceso a los datos.
- `dao.impl`: contiene la implementación de dichas operaciones.
- `fabrica`: contiene clases encargadas de crear objetos.
- `modelo`: contiene las clases principales del sistema.
- `modelo.estado`: administra los estados de los envíos.
- `modelo.excepciones`: contiene excepciones personalizadas.
- `principal`: contiene clases de prueba y ejecución.
- `servicio`: administra la lógica de negocio.
- `vista`: contiene la interfaz gráfica.
- `sistemaenvios`: contiene la clase principal de la aplicación.

## Programación Orientada a Objetos

En el desarrollo se aplican conceptos fundamentales de POO:

- **Encapsulamiento:** protección y control del acceso a los atributos.
- **Herencia:** reutilización de características entre diferentes tipos de envío.
- **Polimorfismo:** comportamiento especializado según el tipo de envío.
- **Abstracción:** representación de los elementos principales del sistema mediante clases y métodos.

## Principios y patrones de diseño

El proyecto busca aplicar buenas prácticas de diseño:

- **SOLID:** principios para desarrollar código organizado, mantenible y flexible.
- **Factory:** creación de diferentes tipos de envíos.
- **State:** administración de los cambios de estado de un envío.
- **DAO:** separación entre la lógica del sistema y el acceso a la base de datos.

## Base de datos

El sistema utiliza MySQL para almacenar la información relacionada con los envíos.

El proyecto incluye un archivo SQL para preparar la base de datos. Antes de ejecutar la aplicación, se debe importar el script en MySQL y configurar los datos de conexión en la clase `Conexion.java`.

## Ejecución del proyecto

1. Descargar o clonar el repositorio.
2. Abrir el proyecto en el entorno de desarrollo.
3. Configurar la conexión a MySQL en `Conexion.java`.
4. Crear e importar la base de datos utilizando el archivo SQL incluido.
5. Verificar que el conector JDBC de MySQL esté agregado al proyecto.
6. Ejecutar la clase principal `SistemaEnvios.java`.

---

**SistemaEnvios — Gestión y seguimiento de envíos.**
