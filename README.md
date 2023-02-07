# Sistema de informacion BookStop BD2

Sistema de informacion de la libreria BookStop en el ramo de Base de datos II de la Universidad de La Serena en el año 2022

## Descripcion

Este sistema de informacion fue hecho para la libreria BookStop en el lenguaje de programacion de Java, ademas, se utilizo MongoDB para la creacion de la base de datos la cual fue traspasada de PostgreSQL a traves de comandos SQL al formato JSON, para luego la incorporacion de estos documentos a MongoDB

## Requerimientos

- Java
- MongoDB
- Librerias(Swingx, MongoDB java driver )

## Usos

- Busqueda de libro por sus atributos(Titulo, Editorial, Nombre del autor, Tipo de genero, etc.)
- Visualizacion de los libros a traves de una tabla intuitiva
- Ingreso de los datos del usuario(Rut)
- Creacion de la venta del libro, y almacenamiento de la boleta tanto en la coleccion de Libros como en la de Clientes

## Instrucciones

Instalar dependencias de Maven
```sh 
mvn install
```
