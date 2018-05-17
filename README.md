Proyecto: Requerimientos (Remote Administrator)
El programa utilizara la arquitectura cliente-servidor mediante el uso de sockets, será un programa que funcione tal y como el VNC, Radmin etc. Que controlara el acceso a otra computadora mediante permisos otorgados a los clientes que se conecten al servidor.

En este caso, el sistema (servidor):

Manejara una barra (fondo transparente) donde mostrara a cada cliente que se conecte (alumno)
La barra (fondo transparente), se podrá colocar en (Arriba, Abajo, Izquierda, Derecha) además tendrá un botón de ocultar la barra, y con una combinación de teclas se des-ocultará de nuevo la barra.
Cada alumno será un JToggleButton, que solo mostrará el primer nombre del alumno, y cuando se pase el mouse por encima mostrará un tooltiptext, que mostrará el nombre completo del alumno.
Cuando el servidor desee que un cliente tome control de su escritorio, bastara con darle clic al JToggleButton, para que se quede sumido, y con ello dará permiso al cliente (alumno) de que tome posesión del control de la pantalla, utilizando su mouse y su teclado
Cuando en el servidor se le dé clic y le quite lo “sumido” al botón, el cliente ya no podrá utilizar el control del servidor.
El servidor podrá cerrar la ventana del cliente cuando este lo desee (inventarse algún mecanismo).
En el caso del cliente:

Al ejecutar el cliente, pedirá:
Nombre completo del alumno
Código
Ip del servidor
Después de agregar los datos del paso1, la ventana se minimizará en la barra de tareas y aparecerá su nombre en un JToggleButton en el servidor.
No se podrá cerrar normalmente con darle clic en Cerrar ventana (X), para que el alumno no se salga cuando quiera, solo el servidor podrá cerrar la ventana del cliente.