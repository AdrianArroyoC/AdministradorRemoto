Proyecto: Requerimientos (Remote Administrator)
El programa utilizara la arquitectura cliente-servidor mediante el uso de sockets, ser� un programa que funcione tal y como el VNC, Radmin etc. Que controlara el acceso a otra computadora mediante permisos otorgados a los clientes que se conecten al servidor.

En este caso, el sistema (servidor):

Manejara una barra (fondo transparente) donde mostrara a cada cliente que se conecte (alumno)
La barra (fondo transparente), se podr� colocar en (Arriba, Abajo, Izquierda, Derecha) adem�s tendr� un bot�n de ocultar la barra, y con una combinaci�n de teclas se des-ocultar� de nuevo la barra.
Cada alumno ser� un JToggleButton, que solo mostrar� el primer nombre del alumno, y cuando se pase el mouse por encima mostrar� un tooltiptext, que mostrar� el nombre completo del alumno.
Cuando el servidor desee que un cliente tome control de su escritorio, bastara con darle clic al JToggleButton, para que se quede sumido, y con ello dar� permiso al cliente (alumno) de que tome posesi�n del control de la pantalla, utilizando su mouse y su teclado
Cuando en el servidor se le d� clic y le quite lo �sumido� al bot�n, el cliente ya no podr� utilizar el control del servidor.
El servidor podr� cerrar la ventana del cliente cuando este lo desee (inventarse alg�n mecanismo).
En el caso del cliente:

Al ejecutar el cliente, pedir�:
Nombre completo del alumno
C�digo
Ip del servidor
Despu�s de agregar los datos del paso1, la ventana se minimizar� en la barra de tareas y aparecer� su nombre en un JToggleButton en el servidor.
No se podr� cerrar normalmente con darle clic en Cerrar ventana (X), para que el alumno no se salga cuando quiera, solo el servidor podr� cerrar la ventana del cliente.