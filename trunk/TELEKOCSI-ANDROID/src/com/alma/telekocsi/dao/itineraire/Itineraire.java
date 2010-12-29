package com.alma.telekocsi.dao.itineraire;


/**
 * @author ALMA
 * 
 * Represente juste un chemin entre un point de depart 
 * et un point d'arrivee.
 */
public class Itineraire {

	private String id;
	
	private String lieuDepart;
	private String lieuDestination;
	private int placeDispo;
	private String horaireDepart;
	private String variableDepart;
	private String horaireArrivee;
	private boolean autoroute;
	private String frequenceTrajet;
	private int nbrePoint;
	private String commentaire;
	private String idProfil;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	

	public String getLieuDepart() {
		return lieuDepart;
	}

	public void setLieuDepart(String lieuDepart) {
		this.lieuDepart = lieuDepart;
	}

	public String getLieuDestination() {
		return lieuDestination;
	}

	public void setLieuDestination(String lieuDestination) {
		this.lieuDestination = lieuDestination;
	}

	public int getPlaceDispo() {
		return placeDispo;
	}

	public void setPlaceDispo(int placeDispo) {
		this.placeDispo = placeDispo;
	}

	public String getHoraireDepart() {
		return horaireDepart;
	}

	public void setHoraireDepart(String horaireDepart) {
		this.horaireDepart = horaireDepart;
	}

	public String getVariableDepart() {
		return variableDepart;
	}

	public void setVariableDepart(String variableDepart) {
		this.variableDepart = variableDepart;
	}

	public String getHoraireArrivee() {
		return horaireArrivee;
	}

	public void setHoraireArrivee(String horaireArrivee) {
		this.horaireArrivee = horaireArrivee;
	}

	public boolean isAutoroute() {
		return autoroute;
	}

	public void setAutoroute(boolean autoroute) {
		this.autoroute = autoroute;
	}

	public String getFrequenceTrajet() {
		return frequenceTrajet;
	}

	public void setFrequenceTrajet(String frequenceTrajet) {
		this.frequenceTrajet = frequenceTrajet;
	}

	public int getNbrePoint() {
		return nbrePoint;
	}

	public void setNbrePoint(int nbrePoint) {
		this.nbrePoint = nbrePoint;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getIdProfil() {
		return idProfil;
	}

	public void setIdProfil(String idProfil) {
		this.idProfil = idProfil;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null 
				&& (o instanceof Itineraire) 
				&& this.id != null 
				&& this.id.equals(((Itineraire) o).getId());
	}
	
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getLieuDepart());
		stringBuilder.append(" -> ");
		stringBuilder.append(getLieuDestination());
		
		return stringBuilder.toString();
	}
	
	
	public String toStringDetail() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Id : " + getId());
		stringBuilder.append("; Commentaire : " + getCommentaire());
		stringBuilder.append("; Frequence trajet : " + getFrequenceTrajet());
		stringBuilder.append("; Horaire arrivée : " + getHoraireArrivee());
		stringBuilder.append("; Horaire départ : " + getHoraireDepart());
		stringBuilder.append("; Lieu départ : " + getLieuDepart());
		stringBuilder.append("; Lieu destination : " + getLieuDestination());
		stringBuilder.append("; Nombre de points : " + getNbrePoint());
		stringBuilder.append("; Place disponibles : " + getPlaceDispo());
		stringBuilder.append("; Variable de départ : " + getVariableDepart());
		stringBuilder.append("; Autoroute : " + isAutoroute());
		stringBuilder.append("; Profil : " + getIdProfil());
		
		return stringBuilder.toString();
	}
	
}
