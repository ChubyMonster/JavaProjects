package controller;

import model.Contact;
import model.ContactDAO;

import java.util.List;

public class ContactController {

    private ContactDAO dao;

    public ContactController() {
        dao = new ContactDAO();
    }
    
    public List<Contact> fetchContacts() {
        return dao.getAllContacts();
    }

    public boolean removeContact(int id) {
        return dao.deleteContact(id);
    }
}
