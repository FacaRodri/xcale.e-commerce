XCALE E-COMMERCE CHALLENGE!

--------------------------------------------------------------------

Arquitectura:
MVC

Patrones de diseño:
Inyección de Dependencias (DI)

Tecnologias:
Maven 3.6.3 +
Java 17 +
Spring boot 3.2.0

Dependencias:
Spring Web +
Spring Data JPA	+
Lombok

Base de datos:	
H2 DataBase

Tests:
JUnit 5.8.1 +
Mockito 3.12.4

--------------------------------------------------------------------

End points:

Create cart: POST http://localhost:8080/api/carts

Descripcion: Endpoint para crear un carrito

Respuesta Exitosa: 200, con el mensaje "Cart created with ID: 1"

===================================

Add products to a cart: POST http://localhost:8080/api/carts/1/addProducts

Descripcion: Endpoint para agregar uno o mas productos a un carrito.

Detalles: El sistema permite añadir los mismos productos a distintos carritos, si un producto se quiere agregar a un carrito y este ya existe, se le sumara la cantidad que se desea añadir al ya existente.

Body example: 

[
    {
        "id": 1,
        "description": "Product 1",
        "amount": 1
    },
    {
        "id": 1,
        "description": "Product 1",
        "amount": 5
    }
]

Respuesta Exitosa: 200, con el body: 

{
    "id": 1,
    "products": [
        {
            "id": 1,
            "description": "Product 1",
            "amount": 5
        },
	    {
        "id": 2,
        "description": "Product 2",
        "amount": 5
    }
    ],
    "lastActivity": "2023-12-07T17:30:58.1598481"
}

===================================

Get cart: GET http://localhost:8080/api/carts/1

Descripcion: Endpoint para obtener la informacion de un carrito.

Detalles: Si se consulta por un ID de un carrito que no existe el sistema responde con 404 y el mensaje "Cart not found".

Respuesta Exitosa: 200, con el body:

{
    "id": 1,
    "products": [
        {
            "id": 1,
            "description": "Product 1",
            "amount": 5
        }
    ],
    "lastActivity": "2023-12-07T17:30:58.159848"
}


===================================

Delete cart: DELETE http://localhost:8080/api/carts/1

Descripcion: Endpoint para eliminar un carrito.

Detalles: Si se quiere eliminar un carrito que no existe el sistema responde con 404 y el mensaje "Cart not found". Al eliminar un carrito se eliminan los productos asociados, sin embargo si estos productos tambien se encuentran en otro carrito no seran eliminados de este ultimo.

--------------------------------------------------------------------

Eliminacion de un carrito por inactividad:

Para este punto agregue un atributo a la clase Cart llamado "lastActivity" de tipo LocalDateTime. 

Tambien cree un cron que se ejecuta cada 1 minuto y verifica en la base de datos si un carrito tuvo mas de 10 minutos de inactividad y de ser asi este se elimina, en el metodo donde se agregan productos se actualiza el campo "lastActivity" del Cart cuando se agrega uno o varios productos.








