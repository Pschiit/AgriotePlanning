package fr.agriotes.planning.models;

public class Date extends java.sql.Date {

    public enum MOIS {
        NONE,JANVIER, FEVRIER, MARS, AVRIl, MAI, JUIN, JUILLET, AOUT, SEPTEMBRE, OCTOBRE, NOVEMBRE, DECEMBRE
    }

    public enum JOUR {
        DIMANCHE,LUNDI,MARDI,MERCREDI,JEUDI,VENDREDI,SAMEDI
    }
    
    private int annee;
    private int mois;
    private int jour;

    public Date(int annee, int mois, int jour) {
        super(annee - 1900, mois - 1, jour);
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
    }

    public static Date FromSQLDate(java.sql.Date date) {
        return new Date(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }

    public java.sql.Date toSQLDate() {
        return new java.sql.Date(getYear(), getMonth(), getDate());
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

    public String toString() {
        return (jour < 10 ? "0" + jour : jour ) + "/" + (mois < 10 ? "0" + mois : mois )+ "/" + annee;
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

    public boolean isBisextile() {
        return (annee % 400 == 0) || ((annee % 4 == 0) && (annee % 100 != 0));
    }

    public boolean isFerie() {
        if (mois == 1 && jour == 1
                || (mois == 4 && (jour == 1 || jour == 8))
                || (mois == 7 && jour == 14)
                || (mois == 8 && jour == 15)
                || (mois == 11 && (jour == 1 || jour == 11))
                || (mois == 12 && jour == 25)) {
            return true;
        }
        Date pacques = calculLundiPacques(this.annee);
        int moisPacques = pacques.mois;
        int jourPacques = pacques.jour;
        int jourAscencion = jourPacques + 38;
        int moisAscencion = moisPacques;
        int jourPentecote = jourPacques + 49;
        int moisPentecote = moisPacques;
        if ((mois == moisPacques && jour == jourPacques)
                || (mois == moisAscencion && jour == jourAscencion)
                || (mois == moisPentecote && jour == jourPentecote)) {
            return true;
        }
        return false;
    }

    public Date calculLundiPacques(int annee) {
        int a = annee / 100;
        int b = annee % 100;
        int c = (3 * (a + 25)) / 4;
        int d = (3 * (a + 25)) % 4;
        int e = (8 * (a + 11)) / 25;
        int f = (5 * a + b) % 19;
        int g = (19 * f + c - e) % 30;
        int h = (f + 11 * g) / 319;
        int j = (60 * (5 - d) + b) / 4;
        int k = (60 * (5 - d) + b) % 4;
        int m = (2 * j - k - g + h) % 7;
        int n = (g - h + m + 114) / 31;
        int p = (g - h + m + 114) % 31;
        int jour = p + 1;
        int mois = n;

        Date date = new Date(annee, mois, jour);
        return date;
    }
}
