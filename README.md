# Fallabela technical test
This Repostory was made for the development of the Fallabella Technical Test in the application of the Technical Lead job.

The Project was made in SringBoot (Java) using Swagger for the Api Documentation and desing tools.
Make sure you have the corresponding version of each component in case you want to edit the code.

# Manual Build

- Es necesario tener instalado java , maven (builded with ver : 3.6.0)
- Para levantar el servidor localmente
  - `mvn spring:boot run`
- Para hacer build manualmente (también corre los test unitarios)
  - `mvn clean install`
  
- La api funciona funcionará bajo los siguientes casos
  - El query params, estén correctamente escritos Ej: /pi/?random_limit=1
  - El parámetro random_limit sea un número entero
    - Si es un decimal o un texto la api responderá con un Bad Request(Comportamiento esperado)
  - Se implementó seguridad de una forma sencilla para validar el concepto
    - En el header del request debe ir la correcta llave para 'Authorization" (Revisar el codigo para ver esto)
    - Por defecto la api acepta consultas de cualquier IP, esto se puede cambiar en la variable ALLOWED-ORIGINS
      en el archivo de configuración.properties.
    - Para este caso ese valor debe ser cambiado en el valor del despliegue de GKE.

- Los test unitarios se encuentran implementados para verificar el build, si estos fallan el build no se realizará

# Deployment & CD/CI

El proyecto fue desplegado en un cluster de Kubernetes en GCP, se puede revisar de forma sencilla utilizando la ip del despliegue

- http://35.224.147.78
- Para acceder se deben usar las siguientes credenciales 
  - user : falabella
  - password : password.2020
  - Al acceder a dicha dirección e ingresar las credenciales se le redireccionará al swagger del despliegue para que pueda realizar pruebas desde el navegador.

Respecto del CD/CI , este fue realizado utilizando este repository de GitHub y conectandolo al despliegue en GKE utilizando Cloud Build de Google. Este último
se activa al detectar un nuevo push a la rama develop. Construirá el servidor de Springboot utilizando un contenedor de maven para el build y generará la imagen
para el despliegue conteniendo en una imagen java/distroless.

Si se desea revisar las configuraciones realizadas respecto este punto, pedir acceso a mauricio.rojasf@usach.cl para los accesos correspondiente de los recursos
de la nube.

# Horizontal Pod Scaler
En la carpeta Kubernetes del repositorio se encuentra el archivo [deploy.yaml](https://github.com/MauricioRojasf/Fallabela_technical_test/blob/develop/kubernetes/deploy.yaml), que contiene las configuraciones del despliegue para le integración continua y el autoescalado de la api.

- Esencialmente las políticas de autoescalado configuradas son las siguientes:
  - Un default de 3 réplicas
  - Mínimo de 1 réplica y un máximo de 5
  - La política de autoescalado está fijada en un uso de CPU del 80%, esto levantará automáticamente la nueva réplica y balanceará la carga correspondiente.

# Benchmark

En el directorio raíz del repositorio se encuentra un archivo llamado [stressTest.sh](https://github.com/MauricioRojasf/Fallabela_technical_test/blob/develop/stressTest.sh) este bash servira el que nos ayude a hacer las pruebas masivas.
Para ejecutarlo se debe utilizar la siguiente sintaxis:

`~$ ./stressTest.sh -c 4 -r 100 -H "Authorization: Basic ZmFsYWJlbGxhOnBhc3N3b3JkLjIwMjA"`

  - El parámetro -c indica la concurrencia
  - El parámetro -r indica la cantidad de requests
  - El parámetro -H es para el header de autenticación de la api
  - Tener en cuenta que por defecto el test estará corriendo llamadas con random_limit=10000
    - Si se desea modificar este valor se puede hacer desde la línea 9 en la variable ADDRESS

# Observations

Debido a la naturaleza matemática de la API y su cálculo intensivo no se recomienda usar valores mayores de random_limit 100000 , si bien se ha ejecutado con un valor de 1.000.000 (un millón) y el servidor no se cae, sino que más bien responde pero esa consulta puede tardar un rato en ser completada (varios minutos).

Para valores inferiores a 100000 la consulta tardará hasta 1 segundo, lo cual es ideal para realizar los test de stress.

Cualquier duda o consulta respecto al despliegue o desarrollo de este test, comunicarse con Mauricio Rojas Fuentes , mauricio.rojasf@usach.cl

Gracias Totales.
