# 2024-FutStats

## Descripción de la Aplicación 

FustStats es una aplicación que contiene toda la información sobre ligas, equipos, juagdores.. para que sea accesible para todo tipo de personas.

## Documentación de la API 
Toda la documentación se halla dentro de la carpeta apidoc dentro del backend. Si se quiere acceder a swagger uan vez iniciada la aplicación acceder a este enlace: <a href ="http://localhost:8443/swagger-ui/index.html" > swagger </a>

## Diagrama de la Base de Datos
![image](https://github.com/user-attachments/assets/18836056-04f0-482e-81ae-24fbe5379400)

## Diagrama de la relación entre las distintas entidades
![image](https://github.com/user-attachments/assets/565050fc-ce17-45a5-8c46-21b6586ab3ef)

## Entidades

-Usuario: Contiene toda la información necesaria para los usuarios como su nombre, contraseña, sus equipos favoritos, etc. Hay tres tipos de usuario:
  - Usuario no registrado: Este usuario tiene acceso a toda la aplicación, pero solo puede ver las distintas ligas, equiposm,etc.
  - Usuario registrado: el usuario registrado puede hacer lo mismo que el usuario no registreado pero además tiene un apartado propio donde puede ir agregando sus ligas, equipos y jugadores favoritos, evitando tener que buscarlos siempre que accede.
  - Usuario Administador: es aquel que se encarga de mantener atualizada la información ya que es el único que puede crear, actualizar o borrar las ligas, los equipos, los juagdores, etc.
    
-Liga: la entidad liga es la más importante ya que es aquella en la que se van a encontrar el resto de entidades. Esta contiene toda la información relevante sobre la liga, y además indica que equipos, partidos y jugadores pertenecen a esta.

-Equipo: la entidad esquipo es la encargadad de almacenar los datos agrupados de todos los jugadores y de todos los partidos, ademas de contener los partidos en los que han participado y los jugadores que le pertenecen.

-Partido: contiene y muestra los datos de los dos equipos que han participado, y las actuaciones de cada uno de los jugadores de los esquipos en el partido.

-Jugador: contiene los datos totales de los jugadores. Estos datos son el conjunto de cada una de las actuaciones que han tenido en los partidos.

-JugadorPartido: contiene los datos de una actuación de un jugador en un partido conccreto. Esta entidad aunque sea la última es la segunda mas importante, ya que los datos de las demás entidades no se actualñizan directamente sino que cogen los datos de esta entidad y calculan los suyos.

## Logica de funcionamiento de los datos en FutStat

1. Se crean las distintas entidades con sus respectivos datos. Gracias a los distintos DTO solo se van a crear aquellos datos no futbolísticos gracias al databaseInitializer.
   
![image](https://github.com/user-attachments/assets/e3cfe23d-9709-4a78-9ee7-7978d92d6331)

2. Se introducen los datos de una actuación de un jugador en un partido concreto y se llama a la actualización de los datos tanto del propio partido como del propio jugador.
   
![image](https://github.com/user-attachments/assets/6609ed3f-5a84-42ff-b5ae-a411b171b1ed)
![image](https://github.com/user-attachments/assets/531cfe2b-9e46-428d-9d19-62cb02e4ec4f)

3. Se acctulizan los datos del partido y del jugador. Una vez se actualizan los datos del partido se llama al método de actualización de los datos del equipo.
   
![image](https://github.com/user-attachments/assets/b4208666-ed93-4b98-94a1-8bc0d387e226)

De esta forma nos aseguramos que los datos futbolísticos son correctos y no se pueden modificar de ninguna otra forma evitando así la posibilidad de errores humanos.

## Datos iniciados desde "DatabaseInitializer"

Se crean varios usuarios para que se puedan realizar las pruebas 

![image](https://github.com/user-attachments/assets/84620595-ea5a-4854-98a0-cb6749e8ee0a)

Se crean varias ligas de prueba

![image](https://github.com/user-attachments/assets/f4ac9736-5f54-4c89-b9e3-b076df20b8a4)
![image](https://github.com/user-attachments/assets/d9af2e8f-1400-462b-8780-f2a6ea67f155)
![image](https://github.com/user-attachments/assets/023a6bdb-8469-4ffd-bd97-7426b24c9815)

Se crean varios equipos de prueba

![image](https://github.com/user-attachments/assets/bfc4038f-2172-40c9-a418-03b9281d6d9c)
![image](https://github.com/user-attachments/assets/768c80c6-72b0-4ce0-a554-d149d37e0b3c)
![image](https://github.com/user-attachments/assets/391670bb-f52d-4fe2-9720-6af14752adaa)

Por útlimo se crean varios jugadores de prueba

![image](https://github.com/user-attachments/assets/21a5d359-9177-44d4-91bc-2863b386678a)
![image](https://github.com/user-attachments/assets/0aae529d-04fb-4131-97eb-3bb3b051143d)















   
