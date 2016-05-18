package testmysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class MySQL { // Metodo de conexion a la BD
    String nombre_tabla;
    private static Connection Conexion;
    public void MySQLConnection(String user, String pass, String db_name) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);//aca el orden como se pasan los datos a MySQL debe ser importante
            //JOptionPane.showMessageDialog(null, "Se ha iniciado la conexión con el servidor de forma exitosa");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void createDB(/*String name*/) throws Exception{ // Metodo para crear la BD pedimos el nombre de la BD
        try{
            String name = JOptionPane.showInputDialog("Ingrese nombre de la BD");
            String Query = "CREATE DATABASE " + name;
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            //closeConnection();
            MySQLConnection("root", "21579097", name);
            JOptionPane.showMessageDialog(null, "Se ha creado la BD! " + name + " de manera correcta");
        }catch(SQLException ex){
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(){ // Metodo de cierre de conexion a la BD
        try{
            Conexion.close();
            JOptionPane.showMessageDialog(null, "La conexion se cerro!");
        }catch (SQLException ex){
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createTable(/*String name*/){// Metodo para crear la Tabla pedimos el nombre de la Tabla
        try{
            String name = JOptionPane.showInputDialog("Ingrese el nombre de la tabla");
            String Query = "CREATE TABLE " + name + ""
                    + "(ID VARCHAR(25),Nombre VARCHAR(50), Apellido VARCHAR(50),"
                    + " Edad VARCHAR(3), Sexo VARCHAR(5))";
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Se ha creado la tabla " + name + " con exito");
            
        } catch (SQLException ex){
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertData(String table_name, String ID, String name, String lastname,
                            String age, String gender){// Metodo para insertar datos, por ahora son datos estaticos
        try{
            String Query = "INSERT INTO " + table_name + " VALUES("
                    + "\"" + ID + "\", "
                    + "\"" + name + "\", "
                    + "\"" + lastname + "\", "
                    + "\"" + age + "\", "
                    + "\"" + gender + "\")";
            Statement st =Conexion.createStatement();
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Los datos fueron ingresados con exito");
        }catch (SQLException ex){
            /*JOptionPane.showMessageDialog(null,"Error en el ingreso de datos");*/
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteRecord(String table_name, String ID){
        try {
            String query = "DELETE FROM " + table_name + "WHERE ID = \"" + ID + "\"";
            Statement st = Conexion.createStatement();
            st.executeUpdate(query);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            //JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo borrar el registro");
        }
    }
    public void getResultados(String table_name){
        try{
            String query = "SELECT * FROM " + table_name;
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getString("ID") + " "
                        + "Nombre: " + resultSet.getString("Nombre") + " " + resultSet.getString("Apellido") + " "
                        + "Edad: " + resultSet.getString("Edad") + " "
                        + "Sexo: " + resultSet.getString("Sexo"));
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void getResultTabla(String bd, String table_name ){
        try {
            String query = "SELECT * FROM " + table_name;
            Statement st = Conexion.createStatement();
            ResultSet rst = st.executeQuery(query);
            ResultSetMetaData rsMT = rst.getMetaData();
            int numColumnas = rsMT.getColumnCount();
            DefaultTableModel modelo = new DefaultTableModel();
            
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
   void getUsuario(String usuario, String password) throws Exception{
        MySQL db = new MySQL();
       try {
           db.MySQLConnection("root", "21579097", "bd1");
          // System.out.println("El usuario es: " + usuario + " y la clave es: " + password);
           String query = "SELECT * FROM usuarios WHERE NombreUsuario='"+usuario+"' AND clave= ('"+password+"')";
           Statement st = Conexion.createStatement();
           ResultSet rst =st.executeQuery(query);
           if (rst.next()){
               //JOptionPane.showMessageDialog(null, "OK");
               Window wj = new Window();
               wj.setVisible(true);
           }else{
               JOptionPane.showMessageDialog(null, "Nombre de Usuario o Contraseña Incorrecta");
           }
        } catch (SQLException ex) {
           Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}



