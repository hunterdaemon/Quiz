/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.vo.Departamento;
import edu.co.sergio.mundo.vo.Obra_de_arte;
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
public class Obras_de_arteDAO implements IBaseDatos<Obra_de_arte>{
    
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
    
	public List<Obra_de_arte> findAll() {
		List<Obra_de_arte> obras= null;
	    String query = "SELECT * FROM Obra_de_arte";
	    Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(Obras_de_arteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    try {
	    Statement st = connection.createStatement();
	    ResultSet rs = st.executeQuery(query);
                
	    String nombre = null;
            String descripcion = null;
            String estilo = null;
            String curriculum = null;
	    int valor = 0;
	
	    while (rs.next()){
	    	if(obras == null){
	    		obras= new ArrayList<Obra_de_arte>();
	    	}
	      
	        Obra_de_arte registro= new Obra_de_arte();
	        nombre = rs.getString("nombre");
	        registro.setNombre(nombre);
	        
	        descripcion = rs.getString("descripcion");
	        registro.setDescripcion(descripcion) ;
                
                estilo = rs.getString("estilo");
	        registro.setEstilo(estilo) ;
                
                valor = rs.getInt("valor");
	        registro.setValor(valor) ;
                
                curriculum = rs.getString("curriculum");
	        registro.setCurriculum(curriculum) ;
	        
	        obras.add(registro);
	    }
	    st.close();
	    
	    } catch (SQLException e) {
			System.out.println("Problemas al obtener la lista de obras");
			e.printStackTrace();
		}
	    
	    return obras;
	}

	
	/**
	 * Funcion que permite realizar la insercion de un nuevo registro en la tabla Departamento
	 * @param Departamento recibe un objeto de tipo Departamento 
	 * @return boolean retorna true si la operacion de insercion es exitosa.
	 */
	public boolean insert(Obra_de_arte t) {
		boolean result=false;
		Connection connection=null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(Obras_de_arteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	    String query = " insert into Obra_de_arte (nombre,descripcion,estilo,valor,curriculum)"  + " values (?,?,?,?,?)";
        PreparedStatement preparedStmt=null;
	    try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString (1, t.getNombre());
                        preparedStmt.setString (2, t.getDescripcion());
                        preparedStmt.setString (3, t.getEstilo());
                        preparedStmt.setInt (4, t.getValor());
                        preparedStmt.setString (5, t.getCurriculum());
                        
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
	public boolean update(Obra_de_arte t) {
		boolean result=false;
		Connection connection= null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(Obras_de_arteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		String query = "update Obra_de_arte set descripcion = ? where nombre = ?";
		PreparedStatement preparedStmt=null;
		try {
		    preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString (1, t.getNombre());
                        preparedStmt.setString (2, t.getDescripcion());
                        preparedStmt.setString (3, t.getEstilo());
                        preparedStmt.setInt (4, t.getValor());
                         preparedStmt.setString (5, t.getCurriculum());
                    
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
	public boolean delete(Obra_de_arte t) {
	   boolean result=false;
	   Connection connection = null;
            try {
                connection = Conexion.getConnection();
            } catch (URISyntaxException ex) {
                Logger.getLogger(Obras_de_arteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
	   String query = "delete from Obra_de_arte where nombre = ?";
	   PreparedStatement preparedStmt=null;
	   try {
		     preparedStmt = connection.prepareStatement(query);
		     preparedStmt.setString (1, t.getNombre());
		    result= preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	   return result;
	}
    
}
