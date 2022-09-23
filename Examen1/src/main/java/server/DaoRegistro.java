package server;

import utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoRegistro {

    public boolean SaveRegistro (BeanRegistro registro){
        boolean result = false;


        try(
                Connection con = MySQLConnection.getConnection();
                PreparedStatement pstm = con.prepareStatement("insert into Registros (nombre,apellido1,apellido2,curp,rfc,fechaNac) values (?,?,?,?,?,?);");
        )
        {
            pstm.setString(1,registro.getNombre());
            pstm.setString(2,registro.getApellido1());
            pstm.setString(3,registro.getApellido2());
            pstm.setString(4,registro.getCurp());
            pstm.setString(5,registro.getRfc());
            pstm.setString(6, registro.getFechaNac());
            result = pstm.executeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public  ArrayList<String> getRegistro (){
        ArrayList<String> curps = new ArrayList<>();
        try {
            Connection connection = MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Registros");
            while (rs.next()){
                curps.add(rs.getString("curp"));
            };

            rs.close();
            connection.close();
            statement.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return curps;
    }
    public BeanRegistro getPerson (String curp){
        BeanRegistro person = new BeanRegistro();

        try (
                Connection con = MySQLConnection.getConnection();
                PreparedStatement pstm = con.prepareStatement("SELECT * FROM Registros where curp = ?;");
        ){

            pstm.setString(1, curp);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                person.setNombre(rs.getString("nombre"));
                person.setApellido1(rs.getString("apellido1"));
                person.setApellido2(rs.getString("apellido2"));
                person.setCurp(rs.getString("curp"));
                person.setFechaNac(rs.getString("fechaNac"));
                person.setRfc(rs.getString("rfc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }
    public boolean updatePerson (String nombre,String apellido1,String apellido2,String curp,String rfc,String fechaNac){
        boolean result= false;
        try
                (Connection con  = MySQLConnection.getConnection();
                 PreparedStatement pstm = con.prepareStatement(
                         "UPDATE Registros set nombre=?, apellido1=?, apellido2=?, rfc=?, fechaNac=? where curp = ?"
                 );

                )


        {
            pstm.setString(1, nombre);
            pstm.setString(2, apellido1);
            pstm.setString(3, apellido2);
            pstm.setString(4, rfc);
            pstm.setString(5, fechaNac);
            pstm.setString(6, curp);
            result = pstm.executeUpdate() == 1;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<BeanRegistro> listPersons(){
        List<BeanRegistro> listPersons = new ArrayList<>();

        try {
            Connection connection = MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Registros");
            while (rs.next()){
                BeanRegistro person = new BeanRegistro();
                person.setNombre(rs.getString("nombre"));
                person.setApellido1(rs.getString("apellido1"));
                person.setApellido2(rs.getString("apellido2"));
                person.setCurp(rs.getString("curp"));
                person.setFechaNac(rs.getString("fechaNac"));
                person.setRfc(rs.getString("rfc"));

                listPersons.add(person);
            };

            rs.close();
            connection.close();
            statement.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return listPersons;
    }
    public boolean deletePerson(String curp){
        boolean result = false;

        try
                (Connection con  = MySQLConnection.getConnection();
                 PreparedStatement pstm = con.prepareStatement("DELETE FROM Registros WHERE curp = ?;");

                )
        {
            pstm.setString(1, curp);
            result = pstm.executeUpdate() == 1;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
