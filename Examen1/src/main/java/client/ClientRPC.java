package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class ClientRPC {
    static Scanner teclado = new Scanner(System.in);
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        String nombre,apellido1,apellido2,curp,rfc;
        String fechaNac="";
        String fecha="";
        String dato;
        int opc;
        String aux="";
        do {
            System.out.println();
            System.out.println();
            System.out.println("Hola bienvenido al registro de usuarios");
            System.out.println("1. Registrar una persona");
            System.out.println("2. Mostrar registros por curp");
            System.out.println("3. Modificar registro por curp");
            System.out.println("4. Consultar todas los registros");
            System.out.println("5. Eliminar registro por curp");
            System.out.println("6. Salir");
            System.out.println("Ingrese su opción");
            opc = teclado.nextInt();
            switch (opc){
                case 1:
                    System.out.println("Ingrese su nombre");
                    nombre = teclado.next();
                    System.out.println("Ingrese su primer apellido");
                    apellido1 = teclado.next();
                    System.out.println("Ingrese su segundo apellido");
                    apellido2 =  teclado.next();
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su anio de nacimiento (últimas 2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac = aux;
                            fecha = aux;
                        }
                    }while (!(aux.length()==2));
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su mes de nacimiento (2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac += aux;
                            fecha += "/"+aux;
                        }
                    }while (!(aux.length()==2));
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su dia de nacimiento (últimas 2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac += aux;
                            fecha += "/"+aux;
                        }
                    }while (!(aux.length()==2));
                    boolean isRepeat= true;
                    do {
                        System.out.println("Ingrese su curp");
                        curp = teclado.next();
                        curp = curp.toUpperCase();
                        Object cur[]={curp};
                        isRepeat = (Boolean) client.execute("Methods.isRepeat",cur);
                    }while (isRepeat);

                    rfc = rfc(nombre, apellido1, apellido2, fechaNac);
                    Object param[]= {nombre,apellido1,apellido2,curp,rfc,fecha};
                    boolean result= (Boolean) client.execute("Methods.Registro",param);
                    if (result){
                        System.out.println("Registro guardado correctamente");
                        System.out.println("Su RFC es: "+ rfc);
                    }else{
                        System.out.println("Ooops, ocurrió un error");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese la curp");
                    curp = teclado.next();
                    Object curps [] ={curp};
                    dato = (String) client.execute("Methods.getPerson",curps);
                    System.out.println(dato);

                    break;
                case 3:
                    do {
                        System.out.println("Ingrese su curp");
                        curp = teclado.next();
                        curp = curp.toUpperCase();
                        Object cur[]={curp};
                        isRepeat = (Boolean) client.execute("Methods.isRepeat",cur);
                        if (!isRepeat){
                            System.out.println("Usuario no registrado");
                        }
                    }while (!isRepeat);
                    System.out.println("Ingrese su nombre");
                    nombre = teclado.next();
                    System.out.println("Ingrese su primer apellido");
                    apellido1 = teclado.next();
                    System.out.println("Ingrese su segundo apellido");
                    apellido2 =  teclado.next();
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su anio de nacimiento (últimas 2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac = aux;
                            fecha += aux;
                        }
                    }while (!(aux.length()==2));
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su mes de nacimiento (2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac += aux;
                            fecha += "/"+aux;
                        }
                    }while (!(aux.length()==2));
                    do {
                        if (aux.length()!=2){
                            aux="";
                        }
                        System.out.println("Ingrese su dia de nacimiento (últimas 2 cifras)");
                        aux = teclado.next();
                        if (aux.length()==2){
                            fechaNac += aux;
                            fecha += "/"+aux;
                        }
                    }while (!(aux.length()==2));

                    rfc = rfc(nombre, apellido1, apellido2, fechaNac);
                    Object param1[]= {nombre,apellido1,apellido2,curp,rfc,fecha};
                    boolean result1= (Boolean) client.execute("Methods.updatePerson",param1);
                    if (result1){
                        System.out.println("Registro guardado correctamente");
                        System.out.println("Su RFC es: "+ rfc);
                    }else{
                        System.out.println("Ooops, ocurrió un error");
                    }
                    break;
                case 4:
                    int i=0;
                    Object val[];
                    String people;
                    System.out.println("Estas son la personas registradas:");
                    System.out.println();
                    do {
                        val= new Object[1];
                        val[0]=i;
                        people = (String) client.execute("Methods.listPersons",val);
                        if (!(people.equals(""))){
                            System.out.println(people);
                            i++;
                        }
                        if (people.equals("")& i==0){
                            System.out.println("No hay ningún registro...");
                        }
                    } while (!(people.equals("")));
                    break;
                case 5:
                    String delete;
                    do {
                        System.out.println("Ingrese su curp");
                        curp = teclado.next();
                        curp = curp.toUpperCase();
                        Object cur[]={curp};
                        isRepeat = (Boolean) client.execute("Methods.isRepeat",cur);
                        if (!isRepeat){
                            System.out.println("Usuario no registrado");
                        }
                    }while (!isRepeat);
                    System.out.println("Estás seguro que deceas eliminar este registro (Si / No)");
                    delete = teclado.next();
                    if (delete.toLowerCase().equals("si")){
                        Object cur [] = {curp};
                        boolean deleted = (Boolean) client.execute("Methods.deletePerson",cur);
                        if (deleted){
                            System.out.println("El registro fue borrado correctamente");
                        }else {
                            System.out.println("Ocurrió un error al eliminar el regitro");
                        }
                    }
                    break;
                case 6:

                    break;
                default:
                    System.out.println("Ingrese una opción valida");
                    break;
            }
        }while (!(opc==6));


    }
    public static String rfc (String nombre, String apellido1, String apellido2, String fechaNac){
        String rfc;
        rfc = apellido1.charAt(0)+""+apellido1.charAt(1)+""+apellido2.charAt(0)+""+nombre.charAt(0)+fechaNac;
        for (int i=10;i<13;i++){
            String SALTCHARS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 18){
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            rfc += salt.toString();

        }
        String result="";
        for (int i=0;i<13;i++){
            result += rfc.charAt(i);
        }
        return result.toUpperCase();
    }
}
