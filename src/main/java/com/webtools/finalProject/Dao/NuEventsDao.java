package com.webtools.finalProject.Dao;

import java.sql.Connection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.webtools.finalProject.Exception.UserException;
import com.webtools.finalProject.Pojo.NuEvents;
import com.webtools.finalProject.Pojo.User;

@Component
public class NuEventsDao extends DAO {
	
	public List<NuEvents> getProducts() throws UserException{
		
		List<NuEvents> myEntities = null;
		try {
			myEntities = getSession().createQuery("FROM NuEvents", NuEvents.class).getResultList();
			System.out.println(myEntities);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
        return myEntities;
    }
	
	public NuEvents getSelectedProduct(Integer packageId) throws UserException{
			
			List<NuEvents> myEntities = null;
			NuEvents nuEvent = null;
			try {
				//myEntities = getSession().createQuery("FROM User where packageId= :packageId", TravelPackages.class).getResultList();
				Query<NuEvents> query =  getSession().createQuery("FROM NuEvents where packageId= :packageId", NuEvents.class);
			    query.setParameter("packageId", packageId);
			    nuEvent = query.uniqueResult();
			    
				System.out.println(nuEvent);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
	        return nuEvent;
	    }
	
	public void save(NuEvents nuEvents) throws Exception {
        try {
            //save Travel Package object in the database
        	begin();
        	getSession().persist(nuEvents);
        	commit();
        } catch (HibernateException e) {
            rollback();       
            e.printStackTrace();
            
            throw new UserException("Exception while creating nuEvents: " + e.getMessage());
    }
 }
	
	public void delete(NuEvents nuEvents) throws UserException {
		 try {
	         //delete Travel Package object in the database
	     	begin();
	     	getSession().remove(nuEvents);
	     	commit();
	     	close();
	     } catch (HibernateException e) {
	         rollback();
	         throw new UserException("Exception while deleting Package: " + e.getMessage());
	     }
	}
	
	public void update(Integer packageID, String packageName, String packageDescription, Integer packagePrice, String imageURL ) throws UserException {
		
		NuEvents nuEvents = getSelectedProduct(packageID);
		
		if(nuEvents!=null) {
			try {
		         //Updates Travel Package object in the database
		     	begin();
		     	nuEvents.setPackageName(packageName);
		     	nuEvents.setPackageDescription(packageDescription);
		     	nuEvents.setPackagePrice(packagePrice);
		     	nuEvents.setImage(imageURL);
		     	getSession().merge(nuEvents);
		     	commit();
		     	close();
		     } catch (HibernateException e) {
		         rollback();
		         throw new UserException("Exception while deleting Package: " + e.getMessage());
		     }
		}
		 
	}
	
	public List<NuEvents>	getSearchedProducts(String enteredText) throws UserException{
		
		List<NuEvents> myEntities = null;
		try {
			myEntities = getSession().createQuery("FROM NuEvents where packageDescription LIKE '%"+enteredText+"%'", NuEvents.class).getResultList();
			System.out.println("Searched Products:" + myEntities);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
        return myEntities;
    }
	
	public List<NuEvents> getSortedProducts() throws UserException{
			
			List<NuEvents> myEntities = null;
			try {
				myEntities = getSession().createQuery("FROM NuEvents ORDER BY packageDescription ASC", NuEvents.class).getResultList();
				System.out.println(myEntities);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
	        return myEntities;
	    }
	
	public List<NuEvents> getPaginationResults(Integer pageNumber) {
		 List<NuEvents> nuEvents = null;
		 begin();
		 try {
			 Query<NuEvents> query = getSession().createQuery("FROM NuEvents", NuEvents.class);
			 query.setFirstResult((4*(pageNumber-1)));
			 query.setMaxResults(4*pageNumber);
			 nuEvents = query.list();
			 commit();
			 close();
			 System.out.println(nuEvents);
		 }
		 catch(Exception e) {
			 System.out.println(e);
			 e.printStackTrace();
		 }
		return nuEvents;
	}
	
	public List<NuEvents> getTravelPackagesForUser(int userId) {
		List<NuEvents> nuEvents = null;
		begin();
		try {
			
		    String hql = "SELECT tp FROM User u JOIN u.nuEvents tp WHERE u.id = :userId";
		    Query<NuEvents> query = getSession().createQuery(hql, NuEvents.class);
		    query.setParameter("userId", userId);
		    nuEvents = query.list();
		    commit();
			close();
			System.out.println(nuEvents);
		}catch(Exception e) {
			 System.out.println(e);
			 e.printStackTrace();
		 }
		return nuEvents;
		
	}

}
