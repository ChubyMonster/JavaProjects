package model;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String libelle;
    private String sexe;
    private String telPerso;
    private String telPro;
    private String email;
    private int numCategorie;
    private int numVille;

    // Constructor, getters, setters
    public Contact(int id, String nom, String prenom, String libelle, String sexe, String telPerso, String telPro, String email, int numCategorie, int numVille) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.libelle = libelle;
        this.sexe = sexe;
        this.telPerso = telPerso;
        this.telPro = telPro;
        this.email = email;
        this.numCategorie = numCategorie;
        this.numVille = numVille;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getTelPerso() {
		return telPerso;
	}

	public void setTelPerso(String telPerso) {
		this.telPerso = telPerso;
	}

	public String getTelPro() {
		return telPro;
	}

	public void setTelPro(String telPro) {
		this.telPro = telPro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumCategorie() {
		return numCategorie;
	}

	public void setNumCategorie(int numCategorie) {
		this.numCategorie = numCategorie;
	}

	public int getNumVille() {
		return numVille;
	}

	public void setNumVille(int numVille) {
		this.numVille = numVille;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
