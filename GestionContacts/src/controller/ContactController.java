package controller;

import model.Contact;
import model.ContactDAO;

import java.util.Collections;
import java.util.List;

public class ContactController {

    private ContactDAO dao;

    public ContactController() {
        dao = new ContactDAO();
    }
    
    public List<Contact> fetchContacts() {
    	try {
            return dao.getAllContacts();
        } catch (Exception e) {
            System.err.println("Failed to fetch contacts: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }

    public boolean removeContact(int id) {
    	 if (id <= 0) return false; 
    	    return dao.deleteContact(id);
    }
}
