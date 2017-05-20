
# DiscoWeb BACKEND y FRONTEND


**Pasos para configurar el backend**

**1)** Descargar el archivo DiscotecaFinal.mwb que se encuentra dentro de la carpeta Modelo.

**2)** Abrir el modelo con MySQLWorkbench y generar la base de datos a partir del modelo.

**3)** Cambiar el archivo presistence.xml que se encuetra en el directorio **Discotecas/src/main/resources/META-INF/** por las configuraciones de tu user, password y nombre de la base de datos creada.
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_0.xsd">
  <persistence-unit name="unitEcho">
    <provider>org.datanucleus.api.jpa.PersistenceProviderImpl</provider>
    <class>com.example.beans.Entrada</class>
    <class>com.example.beans.Reserva</class>
    <class>com.example.beans.Discoteca</class>
    <class>com.example.beans.Asistente</class>
    <class>com.example.beans.Evento</class>
    <class>com.example.beans.AsistenteEvento</class>
    <class>com.example.beans.Adminstrador</class>
    <exclude-unlisted-classes>true</exclude-unlisted-classes>
    <properties>
      <property name="datanucleus.identifier.case" value="MixedCase"/>
      <!-- LOCAL     --> 
      
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/discotecasWebDB"/>
      <property name="javax.persistence.jdbc.user" value="root"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="password"/>
      
      
    
    </properties>
  </persistence-unit>
</persistence>
```


**4)** Navegar hasta la ruta de la carpeta Discotecas 
```
cd ~/.../Discotecas 
```

**5)** Cuando se esté dentro de esta carpeta se corre el comando **mvn appdengine:devserver**

**6)** Esperar a que suba el proyecto, para probar los endpoints con el Frontend.


**Pasos para configurar el frontend**

