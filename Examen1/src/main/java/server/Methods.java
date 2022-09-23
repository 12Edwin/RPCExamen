package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Methods {
    public Methods() {
    }
    public boolean Registro (String nombre, String apellido1, String apellido2, String curp, String rfc, String fechaNac){
        BeanRegistro registro= new BeanRegistro();
        DaoRegistro daoRegistro = new DaoRegistro();
        registro.setNombre(nombre);
        registro.setApellido1(apellido1);
        registro.setApellido2(apellido2);
        registro.setCurp(curp);
        registro.setRfc(rfc);
        registro.setFechaNac(fechaNac);
        boolean result = daoRegistro.SaveRegistro(registro);
        return result;
    }
    public boolean isRepeat (String curp){
        DaoRegistro daoRegistro =new DaoRegistro();
        ArrayList<String> curpSQL = daoRegistro.getRegistro();
        boolean result= true;
        if (curpSQL.size()==0){
            result= false;
        }
        for (String element : curpSQL){
            String elementLower = element.toLowerCase();
            String curpLower = curp.toLowerCase();
            if (curpLower.equals(elementLower)){
                result = true;
                break;
            }else {
                result = false;
            }
        }
        return result;
    }
    public String getPerson (String curp){
        String dato;
        DaoRegistro daoRegistro = new DaoRegistro();
        BeanRegistro registro = daoRegistro.getPerson(curp);
        dato = registro.getNombre()+" "+registro.getApellido1()+" "+registro.getFechaNac()+ " "+registro.getCurp()+" "+ registro.getRfc();
        return dato;
    }
    public boolean updatePerson (String nombre,String apellido1,String apellido2,String curp,String rfc,String fechaNac){
        DaoRegistro daoRegistro = new DaoRegistro();
        boolean result = daoRegistro.updatePerson(nombre,apellido1,apellido2,curp,rfc,fechaNac);
        return result;
    }
    public String listPersons (int i){
        DaoRegistro daoRegistro= new DaoRegistro();
        List<BeanRegistro> people = daoRegistro.listPersons();
        if (!(people.size()==i)){
            BeanRegistro registro = people.get(i);
            return registro.getNombre()+" "+registro.getApellido1()+" "+registro.getApellido2()+" "+registro.getFechaNac()+
                    " "+registro.getCurp()+" "+registro.getRfc();
        }else {
            return "";
        }
    }
    public boolean deletePerson (String curp){
        DaoRegistro daoRegistro = new DaoRegistro();
        boolean result = daoRegistro.deletePerson(curp);
        return result;
    }
}
