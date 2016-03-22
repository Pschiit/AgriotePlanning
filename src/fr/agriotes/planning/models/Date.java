package fr.agriotes.planning.models;

public class Date extends java.sql.Date {
    private int annee;
    private int mois;
    private int jour;
    
    public Date(int i, int i1, int i2) {
        super(i-1900, i1 -1, i2);
        annee = i;
        mois = i1;
        jour = i2;
    }
    
    public static Date FromSQLDate(java.sql.Date date){
        return new Date(date.getYear() + 1900,date.getMonth() + 1, date.getDate());
    }
    
    public java.sql.Date toSQLDate(){
        return new java.sql.Date(getYear(),getMonth(),getDate());
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }
        
    public String toString(){
        return jour + " - " + mois + " - " + annee;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.annee;
        hash = 43 * hash + this.mois;
        hash = 43 * hash + this.jour;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Date other = (Date) obj;
        if (this.annee != other.annee) {
            return false;
        }
        if (this.mois != other.mois) {
            return false;
        }
        if (this.jour != other.jour) {
            return false;
        }
        return true;
    }
}
