
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Classe représentant une Chirurgie
 *
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Chirurgie implements Comparable {

    private String id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Salle salle;
    private Chirurgien chirurgien;

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setChirurgien(Chirurgien chirurgien) {
        this.chirurgien = chirurgien;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Chirurgien getChirurgien() {
        return chirurgien;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Chirurgie(String id, LocalDate date, LocalTime heureDebut, LocalTime heureFin, Salle salle, Chirurgien chirurgien) {
        this.id = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.chirurgien = chirurgien;
    }

    public Chirurgie() {
        this.id = null;
        this.date = null;
        this.heureDebut = null;
        this.heureFin = null;
        this.salle = null;
        this.chirurgien = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return Long : Correspondant à la durée entre l'heure de début et l'heure de fin de la chirurgie courante
     */
    public Long getDuree() {
        if (this.heureDebut.isBefore(this.heureFin)) {
            return Duration.between(this.heureDebut, this.heureFin).toMinutes();
        }
        else {
            return Duration.ofMinutes(1440).minus(Duration.between(this.heureFin, this.heureDebut)).toMinutes();
        }
    }

    /**
     *
     * @param lt1 : objet de type LocalTime, qui correspond à une heure de début de Chirurgie
     * @param lt2 : objet de type LocalTime, qui correspond à une heure de fin de Chirurgie
     * @return Long : Correspondant à la durée entre lt1 et lt2
     */
    public static Long getDuree(LocalTime lt1, LocalTime lt2) {
        if (lt1.isBefore(lt2)) {
            return Duration.between(lt1, lt2).toMinutes();
        }
        else {
            return Duration.ofMinutes(1440).minus(Duration.between(lt2, lt1)).toMinutes();
        }
    }

    public String toString() {
        String today = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH).format(this.getDate());
        String ajoutSecondeDebut = "";
        String ajoutSecondeFin = "";
        if (this.getHeureDebut().getSecond() == 0) {
            ajoutSecondeDebut = ":00";
        }
        if (this.getHeureFin().getSecond() == 0) {
            ajoutSecondeFin = ":00";
        }
        return this.getId() + ";" + today + ";"
                + this.getHeureDebut().toString() + ajoutSecondeDebut + ";" + this.getHeureFin().toString() + ajoutSecondeFin + ";" + this.getSalle().toString() + ";" + this.getChirurgien().toString();
    }

    /**
     *
     * @param c un Objet Chirurgie, différent de la chirurgie actuel
     * @return return un booléen : - true si la chirurgie c se déroule en même temps que la chirurgie courante
     * (partiellement ou complètement) - false sinon
     */
    public boolean estParallele(Chirurgie c) {
        if (this.getDate().equals(c.getDate())) {
            if (this.getHeureDebut().equals(c.getHeureDebut())) {
                return true;
            }
            if (this.getHeureDebut().equals(c.getHeureFin())) {
                return true;
            }

            if (this.getHeureFin().equals(c.getHeureDebut())) {
                return true;
            }

            if ((c.getHeureDebut().isAfter(this.getHeureDebut()))
                    && (getDuree(this.getHeureDebut(), c.getHeureDebut()) < this.getDuree())) {
                return true;
            }
            if ((this.getHeureDebut().isAfter(c.getHeureDebut()))
                    && (getDuree(c.getHeureDebut(), this.getHeureDebut()) < c.getDuree())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param o : Objet de type Object, qui sera cast en Chirurgie
     * @return true si la date, l'heure de début, l'heure de fin, le chirurgien et la salle de la chirurgie courante et
     * de la chirurgie en paramètre sont équivalente - false sinon
     */
    public boolean equals(Object o) {
        if (this.getClass().equals(o.getClass())) {
            Chirurgie c = (Chirurgie) o;
            if ((this.date.equals(c.date)) && (this.heureDebut.equals(c.heureDebut)) && (this.heureFin.equals(c.heureFin)) && (this.chirurgien.equals(c.chirurgien))
                    && (this.salle.equals(c.salle))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param obj : Objet de type Object, qui sera cast en Chirurgie Methode permettant le tri des Chirurgies par jour,
     * et par heure, de la plus ancienne à la plus récente
     * @return -1 si l'objet courant est plus ancien que l'objet passé en paramètre 1 si l'objet courant est plus récent
     * que l'objet passé en paramètre 0 si l'objet passé en paramètre n'est pas une Chirurgie.
     */
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof Chirurgie) {
            Chirurgie c = (Chirurgie) obj;
            if (this.getDate().isBefore(c.getDate())) {
                return -1;
            }
            else if (this.getDate().equals(c.getDate())) {
                if (this.getHeureDebut().isBefore(c.getHeureDebut())) {
                    return -1;
                }
                else if (this.getHeureDebut().equals(c.getHeureDebut())) {
                    if (this.getHeureFin().isBefore(c.getHeureFin())) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
                else {
                    return 1;
                }
            }
            else {
                return 1;
            }
        }
        else {
            return 0;
        }
    }

}
