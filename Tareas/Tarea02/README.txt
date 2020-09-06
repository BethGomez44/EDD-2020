********
Del Moral Morales Francisco Emmanuel
Gomez De La Torre Heidi Lizbeth


*******Como utilizar la aplicacion:

Para poder usar este sistema de momento tienes que contar con una instalacion de java 8 o superior
la herramienta  que usamos para compilar el proyecto se llama Gradle y te recomendamos que tambien la uses por que es simple
y sencilla de usar.

Si no cuentas con gradle puedes ejecutar los siguientes comandos en la terminal : 

    1) $ sdk install gradle 6.0.1

Y en caso de no tener sdkman instaldo tambien realiza estos pasos : 
En una terminal :

    1) $ curl -s "https://get.sdkman.io" | bash
    2) $ source "$HOME/.sdkman/bin/sdkman-init.sh"
    3) $ sdk version

Este ultimo, para verificar que la instalacion haya sido correcta

Y si eres de aquellos que no confian en nadie no te preocupes siente libre de seguir los pasos por tu cuenta desde las 
fuentes oficiales :

    para gradle https://gradle.org/install/
    para sdkman https://sdkman.io/install

Una vez hecho esto tan solo ejecuta este ultimo comando abriendo una terminal en el directorio Tarea02: 

    1) $ gralde run 

¡Y listo! dentro de unos segundos el sistema se abrira en tu terminal

por si estas interesado la estructura de los archivos fuente es la siguiente:

la clase principal es la clase Interfaz.java
.
├── lib
│   └── core.jar
├── README.txt
└── src
    └── main
        ├── java
        │   ├── app
        │   │   └── Interfaz.java
        │   └── sub
        │       ├── botones
        │       │   └── Boton.java
        │       ├── estructuras
        │       │   ├── Cola.java
        │       │   ├── Coleccionable.java
        │       │   └── Pila.java
        │       └── laberinto
        │           ├── Casilla.java
        │           ├── Laberinto.java
        │           └── LaberintoPaso.java
        └── resources
  
*******Funcionalidades de los botones

Al ejecutar la aplicacion encontraras una ventana parecida a esta:

--------------------------------------------------------- 
|	       |					|	[1]	Generar laberinto
|   [1]   [2]  |	____________________            |	[2]	Generar laberinto paso a paso
|	       |	 * _ _|  _ _ _   _ _|           |	[3]	Mostrar solucion
|   [3]   [4]  |	| |   | |  _  |_ _  |           |	[4] 	Mostrar solucion paso a paso
|	       |	|  _|_ _| | |_ _  |*            |	[5]	Borrar solucion
|   [5]   [6]  |	|_  |   | |  _ _|_  |           |	[6]	Guardar laberinto
|	       |	| | | | | | |  _ _ _|           |	[Ancho]	
|	       |	|_ _ _|_| | |_  |_  |           |	[Alto]
|   Ancho      |	|  _  |  _|   |_  | |           |
|  [        ]  |	|  _| |_  | |_  |_  |           |
|	       |	| |_ _|  _| | | |  _|           |
|   Alto       |	|_ _ _ _|_ _ _|_ _ _|           |
|  [        ]  |					|
|	       |					|	
---------------------------------------------------------



-[1]	Generar laberinto
Este boton genera un laberinto de las dimensiones especificadas en el ancho y el alto, sin embargo ten en cuenta que para laberintos muy grandes
no podremos mostrartelo en la pagina, sin embargo estaremos felices de proporcionarte un archivo .jpg con  tu laberinto  

-[2]	Generar laberinto paso a paso
Siempre y cuando el laberinto puede imprimirse de acuerdo a tu resolucion  de pantalla, el boton generar paso a paso te mostrara como se crean
los pasadizos del laberinto.

-[3]	Mostrar solucion
Si ya generaste un laberinto y no sabes como resolverlo presiona mostrar solucion, el camino a seguir se marcara con bolitas rojas, si el laberinto 
es demasiado grande para la resolucion de tu pantalla el boton mostrar solucion te permite almacenar un .jpg con la solucion del laberinto que generaste

-[4}]	Mostrar solucion
Siempre y cuando el laberinto puede imprimirse de acuerdo a tu resolucion  de pantalla, el boton Mostrar solucion paso a paso te mostrara el camino a seguir 
guiandote lentament por el laberinto.

-[5]	Borrar solucion
Si tu laberinto esta resuelto, siempre puedes borraar la solucion con este boton

-[6]	Guardar laberinto
Si este laberinto te gusto demasiado puedes guardarlo como .jpg para imprimirlo, ten en cuenta que si la solucion esta visible, este se guaardara con la solucion














Esperamos tengas un gran dia y excelente salud :D
