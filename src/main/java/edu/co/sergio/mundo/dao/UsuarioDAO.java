/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.vo.Departamento;
import edu.co.sergio.mundo.vo.Usuario;
import edu.co.sergio.mundo.vo.consulta3;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Labing
 */
public class UsuarioDAO implements IBaseDatos<Usuario> {
    
    public  List  Consulta1(){
         List con1 =new LinkedList(); 
         String query = "select nom_proy, count(*) as total from  proyecto left join recurso using (id_proyecto) group by nom_proy";
         Connection connection = null;
         try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    String nom_proy ="";
            int total =0;
	    while (rs.next()){
	      
	        
	         nom_proy = rs.getString("nom_proy");
	         total = rs.getInt("total");
	        
	        con1.add(nom_proy);
	        con1.add(total);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al realizar la consulta ");
			e.printStackTrace();
		}
	    
        return con1;
    }
   
    public  List Consulta2(){
         List  con2 =new LinkedList(); 
         String query = "select nom_depto,count(*) as Num from ((Depto natural join Proyecto) natural join DeptoProyecto )group by nom_depto";
         Connection connection = null;
         try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
	     String nom_depto =" ";
             int Num=0;
	    while (rs.next()){
	      
	        
	        nom_depto = rs.getString("nom_depto");
	         Num = rs.getInt("Num");
	        
	        con2.add(nom_depto);
	        con2.add(Num);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al realizar la consulta");
			e.printStackTrace();
		}
	    
                
        return con2;
    }
    
    public LinkedList<consulta3> consulta3(){
       
        LinkedList<consulta3> c3 = new LinkedList<consulta3>();
        
        String query = "select nom_depto, tipo_contrato, count(*) as total from Depto join Empleado using (id_depto) group by nom_depto, tipo_contrato having count(*)>1";
        Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    String nom_dep = null;
	    String tipodeco = null;
	    int total=0;
	    while (rs.next()){
	    	
	      
	        
	        nom_dep = rs.getString("nom_depto");
	        
	        
	        tipodeco = rs.getString("tipo_contrato");
	        
                total=rs.getInt("total");
                
	        consulta3 registro= new consulta3(nom_dep, tipodeco, total);
	      c3.add(registro);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al obtener la lista de Departamentos");
			e.printStackTrace();
		}
	    
	    return c3;
    
    
    }
    
    
	/**
	 * Funcion que permite obtener una lista de los departamentos existentes en la base de datos
	 * @return List<Departamento> Retorna la lista de Departamentos existentes en la base de datos
	 */
    
	public List<Usuario> findAll() {
		List<Usuario> usuarios= null;
	    String query = "SELECT * FROM Usuario";
	    Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    String user = null;
	    String nombre = null;
	
	    while (rs.next()){
	    	if(usuarios == null){
	    		usuarios= new ArrayList<Usuario>();
	    	}
	      
	        Usuario registro= new Usuario();
	        user = rs.getString("userito");
	        registro.setUserito(user);
	        
	        nombre = rs.getString("nombre");
	        registro.setNombre(nombre) ;
	        
	        usuarios.add(registro);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al obtener la lista de Usuarios");
			e.printStackTrace();
		}
	    
	    return usuarios;
	}

	
	/**
	 * Funcion que permite realizar la insercion de un nuevo registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de insercion es exitosa.
	 */
	public boolean insert(Usuario t) {
		boolean result=false;
		Connection connection=null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    String query = " insert into Usuario (userito,nombre)"  + " values (?,?)";
        PreparedStatement preparedStmt=null;
	    try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString (1, t.getUserito());
                        preparedStmt.setString (2, t.getNombre());
			result= preparedStmt.execute();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Funcion que permite realizar la actualizacion de un nuevo registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de actualizacion es exitosa.
	 */
	public boolean update(Usuario t) {
		boolean result=false;
		Connection connection= null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		String query = "update Usuario set nombre = ? where userito = ?";
		PreparedStatement preparedStmt=null;
		try {
		    preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString (1, t.getUserito());
                    preparedStmt.setString   (2, t.getNombre());
		    if (preparedStmt.executeUpdate() > 0){
		    	result=true;
		    }
			    
		} catch (SQLException e) {
				e.printStackTrace();
		}
			
		return result;
	}

	/**
	 * Funcion que permite realizar la eliminario de registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de borrado es exitosa.
	 */
	public boolean delete(Usuario t) {
	   boolean result=false;
	   Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	   String query = "delete from Usuario where userito = ?";
	   PreparedStatement preparedStmt=null;
	   try {
		     preparedStmt = connection.prepareStatement(query);
		     preparedStmt.setString(1, t.getUserito());
		    result= preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	   return result;
	}

    
    
}
