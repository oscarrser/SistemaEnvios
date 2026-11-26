SISTEMA ENVIOS - CODIGO PARA NETBEANS

IMPORTANTE:
1. Este paquete contiene los archivos fuente organizados por paquete.
2. Tu clase conexion.Conexion debe tener el método público estático:
       public static Connection conectar() throws SQLException
   Si ya tienes conexión exitosa con otro nombre de método, conserva tus credenciales
   y adapta únicamente la llamada Conexion.conectar() en EnvioDAOImpl.
3. En Conexion.java configura el nombre real de tu base de datos, usuario y contraseña.
4. Ejecuta paquelibre.sql en MySQL Workbench si aún no tienes estas dos tablas.
5. Copia cada carpeta dentro de Source Packages, respetando los nombres de paquete.
6. No crees otro paquete si ya existe.
7. La clase principal es sistemaenvios.SistemaEnvios.
8. Ejecuta SistemaEnvios.java con Run File.

PAQUETES:
conexion
dao
dao.impl
fabrica
modelo
modelo.estado
modelo.excepciones
servicio
vista
sistemaenvios

TABLAS:
envios
historial_estados

REGLAS:
- Guía PL- y seis dígitos.
- Peso real > 0; máximo 50 kg, internacional máximo 30 kg.
- Dimensiones mayores que cero.
- Peso volumétrico = largo * ancho * alto / 5000.
- Peso facturable = techo del máximo entre peso real y volumétrico.
- Seguro express e internacional = máximo entre $5.000 y 1% del valor declarado.
- Arancel internacional = 10% del valor declarado.
- Estados: REGISTRADO -> EN RUTA -> ENTREGADO o DEVUELTO.
