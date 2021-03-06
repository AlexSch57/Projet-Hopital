
import java.util.ArrayList;
import java.util.Iterator;

public enum Paire_Chirurgien_Salle {

    BLOC_U7_LAWRENCE_KUTNER("BLOC_U7/LAWRENCE KUTNER", Salle.BLOC_U7, Chirurgien.LAWRENCE_KUTNER, 1),
    BLOC_U7_ROBERT_CHASE("BLOC_U7/ROBERT CHASE", Salle.BLOC_U7, Chirurgien.ROBERT_CHASE, 2),
    BLOC_E1_LAWRENCE_KUTNER("BLOC_E1/LAWRENCE KUTNER", Salle.BLOC_E1, Chirurgien.LAWRENCE_KUTNER, 3),
    BLOC_E2_ROBERT_CHASE("BLOC_E2/ROBERT CHASE", Salle.BLOC_E2, Chirurgien.ROBERT_CHASE, 4),
    BLOC_U7_MIRANDA_BAILEY("BLOC_U7/MIRANDA BAILEY", Salle.BLOC_U7, Chirurgien.MIRANDA_BAILEY, 5),
    BLOC_E1_GREGORY_HOUSE("BLOC_E1/GREGORY HOUSE", Salle.BLOC_E1, Chirurgien.GREGORY_HOUSE, 6),
    BLOC_E1_ROBERT_CHASE("BLOC_E1/ROBERT CHASE", Salle.BLOC_E1, Chirurgien.ROBERT_CHASE, 7),
    BLOC_E2_LAWRENCE_KUTNER("BLOC_E2/LAWRENCE KUTNER", Salle.BLOC_E2, Chirurgien.LAWRENCE_KUTNER, 8),
    BLOC_U7_JAMES_WILSON("BLOC_U7/JAMES WILSON", Salle.BLOC_U7, Chirurgien.JAMES_WILSON, 9),
    BLOC_E1_JAMES_WILSON("BLOC_E1/JAMES WILSON", Salle.BLOC_E1, Chirurgien.JAMES_WILSON, 10),
    BLOC_E2_ERIC_FOREMAN("BLOC_E2/ERIC FOREMAN", Salle.BLOC_E2, Chirurgien.ERIC_FOREMAN, 11),
    BLOC_E3_ROBERT_CHASE("BLOC_E3/ROBERT CHASE", Salle.BLOC_E3, Chirurgien.ROBERT_CHASE, 12),
    BLOC_E3_LISA_CUDDY("BLOC_E3/LISA CUDDY", Salle.BLOC_E3, Chirurgien.LISA_CUDDY, 13),
    BLOC_U7_LISA_CUDDY("BLOC_U7/LISA CUDDY", Salle.BLOC_U7, Chirurgien.LISA_CUDDY, 14),
    BLOC_E2_JAMES_WILSON("BLOC_E2/JAMES WILSON", Salle.BLOC_E2, Chirurgien.JAMES_WILSON, 15),
    BLOC_E3_("BLOC_E3/", Salle.BLOC_E3, Chirurgien.Chirurgien_Mystere, 16),
    BLOC_E3_LAWRENCE_KUTNER("BLOC_E3/LAWRENCE KUTNER", Salle.BLOC_E3, Chirurgien.LAWRENCE_KUTNER, 17),
    BLOC_E3_JAMES_WILSON("BLOC_E3/JAMES WILSON", Salle.BLOC_E3, Chirurgien.JAMES_WILSON, 18),
    BLOC_E1_ERIC_FOREMAN("BLOC_E1/ERIC FOREMAN", Salle.BLOC_E1, Chirurgien.ERIC_FOREMAN, 19),
    BLOC_E2_GREGORY_HOUSE("BLOC_E2/GREGORY HOUSE", Salle.BLOC_E2, Chirurgien.GREGORY_HOUSE, 20),
    BLOC_E3_ERIC_FOREMAN("BLOC_E3/ERIC FOREMAN", Salle.BLOC_E3, Chirurgien.ERIC_FOREMAN, 21),
    BLOC_E3_CHRIS_TAUB("BLOC_E3/CHRIS TAUB", Salle.BLOC_E3, Chirurgien.CHRIS_TAUB, 22),
    BLOC_E1_LISA_CUDDY("BLOC_E1/LISA CUDDY", Salle.BLOC_E1, Chirurgien.LISA_CUDDY, 23),
    BLOC_E2_LISA_CUDDY("BLOC_E2/LISA CUDDY", Salle.BLOC_E2, Chirurgien.LISA_CUDDY, 24),
    BLOC_U7_CHRIS_TAUB("BLOC_U7/CHRIS TAUB", Salle.BLOC_U7, Chirurgien.CHRIS_TAUB, 25),
    BLOC_E1_CHRIS_TAUB("BLOC_E1/CHRIS TAUB", Salle.BLOC_E1, Chirurgien.CHRIS_TAUB, 26),
    BLOC_E3_DEREK_SHEPHERD("BLOC_E3/DEREK SHEPHERD", Salle.BLOC_E3, Chirurgien.DEREK_SHEPHERD, 27),
    BLOC_E1_MIRANDA_BAILEY("BLOC_E1/MIRANDA BAILEY", Salle.BLOC_E1, Chirurgien.MIRANDA_BAILEY, 28),
    BLOC_U7_REMY_HADLEY("BLOC_U7/REMY HADLEY", Salle.BLOC_U7, Chirurgien.REMY_HADLEY, 29),
    BLOC_E3_REMY_HADLEY("BLOC_E3/REMY HADLEY", Salle.BLOC_E3, Chirurgien.REMY_HADLEY, 30),
    BLOC_E2_REMY_HADLEY("BLOC_E2/REMY HADLEY", Salle.BLOC_E2, Chirurgien.REMY_HADLEY, 31),
    BLOC_E2_CHRIS_TAUB("BLOC_E2/CHRIS TAUB", Salle.BLOC_E2, Chirurgien.CHRIS_TAUB, 32),
    BLOC_E3_GREGORY_HOUSE("BLOC_E3/GREGORY HOUSE", Salle.BLOC_E3, Chirurgien.GREGORY_HOUSE, 33),
    BLOC_E1_REMY_HADLEY("BLOC_E1/REMY HADLEY", Salle.BLOC_E1, Chirurgien.REMY_HADLEY, 34),
    BLOC_E3_MIRANDA_BAILEY("BLOC_E3/MIRANDA BAILEY", Salle.BLOC_E3, Chirurgien.MIRANDA_BAILEY, 35),
    BLOC_U3_ROBERT_CHASE("BLOC_U3/ROBERT CHASE", Salle.BLOC_U3, Chirurgien.ROBERT_CHASE, 36),
    BLOC_E2_DEREK_SHEPHERD("BLOC_E2/DEREK SHEPHERD", Salle.BLOC_E2, Chirurgien.DEREK_SHEPHERD, 37),
    BLOC_U7_MEREDITH_GREY("BLOC_U7/MEREDITH GREY", Salle.BLOC_U7, Chirurgien.MEREDITH_GREY, 38),
    BLOC_U6_CHRIS_TAUB("BLOC_U6/CHRIS TAUB", Salle.BLOC_U6, Chirurgien.CHRIS_TAUB, 39),
    BLOC_U6_JAMES_WILSON("BLOC_U6/JAMES WILSON", Salle.BLOC_U6, Chirurgien.JAMES_WILSON, 40),
    BLOC_U6_LAWRENCE_KUTNER("BLOC_U6/LAWRENCE KUTNER", Salle.BLOC_U6, Chirurgien.LAWRENCE_KUTNER, 41),
    BLOC_U6_REMY_HADLEY("BLOC_U6/REMY HADLEY", Salle.BLOC_U6, Chirurgien.REMY_HADLEY, 42),
    BLOC_U6_ROBERT_CHASE("BLOC_U6/ROBERT CHASE", Salle.BLOC_U6, Chirurgien.ROBERT_CHASE, 43),
    BLOC_U6_LISA_CUDDY("BLOC_U6/LISA CUDDY", Salle.BLOC_U6, Chirurgien.LISA_CUDDY, 44),
    BLOC_U9_CHRIS_TAUB("BLOC_U9/CHRIS TAUB", Salle.BLOC_U9, Chirurgien.CHRIS_TAUB, 45),
    BLOC_E1_("BLOC_E1/", Salle.BLOC_E1, Chirurgien.Chirurgien_Mystere, 46),
    BLOC_U10_ROBERT_CHASE("BLOC_U10/ROBERT CHASE", Salle.BLOC_U10, Chirurgien.ROBERT_CHASE, 47),
    BLOC_U6_MIRANDA_BAILEY("BLOC_U6/MIRANDA BAILEY", Salle.BLOC_U6, Chirurgien.MIRANDA_BAILEY, 48),
    BLOC_U6_MEREDITH_GREY("BLOC_U6/MEREDITH GREY", Salle.BLOC_U6, Chirurgien.MEREDITH_GREY, 49),
    BLOC_U6_GREGORY_HOUSE("BLOC_U6/GREGORY HOUSE", Salle.BLOC_U6, Chirurgien.GREGORY_HOUSE, 50),
    BLOC_U7_ERIC_FOREMAN("BLOC_U7/ERIC FOREMAN", Salle.BLOC_U7, Chirurgien.ERIC_FOREMAN, 51),
    BLOC_U7_GREGORY_HOUSE("BLOC_U7/GREGORY HOUSE", Salle.BLOC_U7, Chirurgien.GREGORY_HOUSE, 52),
    BLOC_U6_ERIC_FOREMAN("BLOC_U6/ERIC FOREMAN", Salle.BLOC_U6, Chirurgien.ERIC_FOREMAN, 53),
    BLOC_U5_ROBERT_CHASE("BLOC_U5/ROBERT CHASE", Salle.BLOC_U5, Chirurgien.ROBERT_CHASE, 54),
    BLOC_U3_ERIC_FOREMAN("BLOC_U3/ERIC FOREMAN", Salle.BLOC_U3, Chirurgien.ERIC_FOREMAN, 55),
    BLOC_U9_REMY_HADLEY("BLOC_U9/REMY HADLEY", Salle.BLOC_U9, Chirurgien.REMY_HADLEY, 56),
    BLOC_U6_PRESTON_BURKE("BLOC_U6/PRESTON BURKE", Salle.BLOC_U6, Chirurgien.PRESTON_BURKE, 57),
    BLOC_E3_MEREDITH_GREY("BLOC_E3/MEREDITH GREY", Salle.BLOC_E3, Chirurgien.MEREDITH_GREY, 58),
    BLOC_E1_MEREDITH_GREY("BLOC_E1/MEREDITH GREY", Salle.BLOC_E1, Chirurgien.MEREDITH_GREY, 59),
    BLOC_E2_MEREDITH_GREY("BLOC_E2/MEREDITH GREY", Salle.BLOC_E2, Chirurgien.MEREDITH_GREY, 60),
    BLOC_U5_LAWRENCE_KUTNER("BLOC_U5/LAWRENCE KUTNER", Salle.BLOC_U5, Chirurgien.LAWRENCE_KUTNER, 61),
    BLOC_U5_MIRANDA_BAILEY("BLOC_U5/MIRANDA BAILEY", Salle.BLOC_U5, Chirurgien.MIRANDA_BAILEY, 62),
    BLOC_U6_RICHARD_WEBBER("BLOC_U6/RICHARD WEBBER", Salle.BLOC_U6, Chirurgien.RICHARD_WEBBER, 63),
    BLOC_U5_MEREDITH_GREY("BLOC_U5/MEREDITH GREY", Salle.BLOC_U5, Chirurgien.MEREDITH_GREY, 64),
    BLOC_U5_LISA_CUDDY("BLOC_U5/LISA CUDDY", Salle.BLOC_U5, Chirurgien.LISA_CUDDY, 65),
    BLOC_U5_JAMES_WILSON("BLOC_U5/JAMES WILSON", Salle.BLOC_U5, Chirurgien.JAMES_WILSON, 66),
    BLOC_E3_RICHARD_WEBBER("BLOC_E3/RICHARD WEBBER", Salle.BLOC_E3, Chirurgien.RICHARD_WEBBER, 67),
    BLOC_U9_ROBERT_CHASE("BLOC_U9/ROBERT CHASE", Salle.BLOC_U9, Chirurgien.ROBERT_CHASE, 68),
    BLOC_U5_ERIC_FOREMAN("BLOC_U5/ERIC FOREMAN", Salle.BLOC_U5, Chirurgien.ERIC_FOREMAN, 69),
    BLOC_U3_LISA_CUDDY("BLOC_U3/LISA CUDDY", Salle.BLOC_U3, Chirurgien.LISA_CUDDY, 70),
    BLOC_E1_DEREK_SHEPHERD("BLOC_E1/DEREK SHEPHERD", Salle.BLOC_E1, Chirurgien.DEREK_SHEPHERD, 71),
    BLOC_U6_DEREK_SHEPHERD("BLOC_U6/DEREK SHEPHERD", Salle.BLOC_U6, Chirurgien.DEREK_SHEPHERD, 72),
    BLOC_E1_ALEX_KAREV("BLOC_E1/ALEX KAREV", Salle.BLOC_E1, Chirurgien.ALEX_KAREV, 73),
    BLOC_E2_MIRANDA_BAILEY("BLOC_E2/MIRANDA BAILEY", Salle.BLOC_E2, Chirurgien.MIRANDA_BAILEY, 74),
    BLOC_E2_PRESTON_BURKE("BLOC_E2/PRESTON BURKE", Salle.BLOC_E2, Chirurgien.PRESTON_BURKE, 75),
    BLOC_U5_DEREK_SHEPHERD("BLOC_U5/DEREK SHEPHERD", Salle.BLOC_U5, Chirurgien.DEREK_SHEPHERD, 76),
    BLOC_U3_PRESTON_BURKE("BLOC_U3/PRESTON BURKE", Salle.BLOC_U3, Chirurgien.PRESTON_BURKE, 77),
    BLOC_U5_PRESTON_BURKE("BLOC_U5/PRESTON BURKE", Salle.BLOC_U5, Chirurgien.PRESTON_BURKE, 78),
    BLOC_E1_PRESTON_BURKE("BLOC_E1/PRESTON BURKE", Salle.BLOC_E1, Chirurgien.PRESTON_BURKE, 79),
    BLOC_E1_RICHARD_WEBBER("BLOC_E1/RICHARD WEBBER", Salle.BLOC_E1, Chirurgien.RICHARD_WEBBER, 80),
    BLOC_E2_ALEX_KAREV("BLOC_E2/ALEX KAREV", Salle.BLOC_E2, Chirurgien.ALEX_KAREV, 81),
    BLOC_U9_GREGORY_HOUSE("BLOC_U9/GREGORY HOUSE", Salle.BLOC_U9, Chirurgien.GREGORY_HOUSE, 82),
    BLOC_U10_MEREDITH_GREY("BLOC_U10/MEREDITH GREY", Salle.BLOC_U10, Chirurgien.MEREDITH_GREY, 83),
    BLOC_U10_DEREK_SHEPHERD("BLOC_U10/DEREK SHEPHERD", Salle.BLOC_U10, Chirurgien.DEREK_SHEPHERD, 84),
    BLOC_U10_ALEX_KAREV("BLOC_U10/ALEX KAREV", Salle.BLOC_U10, Chirurgien.ALEX_KAREV, 85),
    BLOC_U10_RICHARD_WEBBER("BLOC_U10/RICHARD WEBBER", Salle.BLOC_U10, Chirurgien.RICHARD_WEBBER, 86),
    BLOC_U10_GREGORY_HOUSE("BLOC_U10/GREGORY HOUSE", Salle.BLOC_U10, Chirurgien.GREGORY_HOUSE, 87),
    BLOC_U10_LAWRENCE_KUTNER("BLOC_U10/LAWRENCE KUTNER", Salle.BLOC_U10, Chirurgien.LAWRENCE_KUTNER, 88),
    BLOC_E3_PRESTON_BURKE("BLOC_E3/PRESTON BURKE", Salle.BLOC_E3, Chirurgien.PRESTON_BURKE, 89),
    BLOC_E2_RICHARD_WEBBER("BLOC_E2/RICHARD WEBBER", Salle.BLOC_E2, Chirurgien.RICHARD_WEBBER, 90),
    BLOC_U5_CHRIS_TAUB("BLOC_U5/CHRIS TAUB", Salle.BLOC_U5, Chirurgien.CHRIS_TAUB, 91),
    BLOC_U7_PRESTON_BURKE("BLOC_U7/PRESTON BURKE", Salle.BLOC_U7, Chirurgien.PRESTON_BURKE, 92),
    BLOC_U5_RICHARD_WEBBER("BLOC_U5/RICHARD WEBBER", Salle.BLOC_U5, Chirurgien.RICHARD_WEBBER, 93),
    BLOC_U7_RICHARD_WEBBER("BLOC_U7/RICHARD WEBBER", Salle.BLOC_U7, Chirurgien.RICHARD_WEBBER, 94),
    BLOC_E2_OWEN_HUNT("BLOC_E2/OWEN HUNT", Salle.BLOC_E2, Chirurgien.OWEN_HUNT, 95),
    BLOC_E1_OWEN_HUNT("BLOC_E1/OWEN HUNT", Salle.BLOC_E1, Chirurgien.OWEN_HUNT, 96),
    BLOC_U7_OWEN_HUNT("BLOC_U7/OWEN HUNT", Salle.BLOC_U7, Chirurgien.OWEN_HUNT, 97),
    BLOC_U6_OWEN_HUNT("BLOC_U6/OWEN HUNT", Salle.BLOC_U6, Chirurgien.OWEN_HUNT, 98),
    BLOC_E3_OWEN_HUNT("BLOC_E3/OWEN HUNT", Salle.BLOC_E3, Chirurgien.OWEN_HUNT, 99),
    BLOC_E2_("BLOC_E2/", Salle.BLOC_E2, Chirurgien.Chirurgien_Mystere, 100),
    BLOC_U5_OWEN_HUNT("BLOC_U5/OWEN HUNT", Salle.BLOC_U5, Chirurgien.OWEN_HUNT, 101),
    BLOC_U7_DEREK_SHEPHERD("BLOC_U7/DEREK SHEPHERD", Salle.BLOC_U7, Chirurgien.DEREK_SHEPHERD, 102),
    BLOC_U4_PRESTON_BURKE("BLOC_U4/PRESTON BURKE", Salle.BLOC_U4, Chirurgien.PRESTON_BURKE, 103),
    BLOC_U8_DEREK_SHEPHERD("BLOC_U8/DEREK SHEPHERD", Salle.BLOC_U8, Chirurgien.DEREK_SHEPHERD, 104),
    BLOC_U4_CHRIS_TAUB("BLOC_U4/CHRIS TAUB", Salle.BLOC_U4, Chirurgien.CHRIS_TAUB, 105),
    BLOC_U4_DEREK_SHEPHERD("BLOC_U4/DEREK SHEPHERD", Salle.BLOC_U4, Chirurgien.DEREK_SHEPHERD, 106),
    BLOC_U4_MEREDITH_GREY("BLOC_U4/MEREDITH GREY", Salle.BLOC_U4, Chirurgien.MEREDITH_GREY, 107),
    BLOC_U3_DEREK_SHEPHERD("BLOC_U3/DEREK SHEPHERD", Salle.BLOC_U3, Chirurgien.DEREK_SHEPHERD, 108),
    BLOC_U4_MIRANDA_BAILEY("BLOC_U4/MIRANDA BAILEY", Salle.BLOC_U4, Chirurgien.MIRANDA_BAILEY, 109),
    BLOC_E3_ALEX_KAREV("BLOC_E3/ALEX KAREV", Salle.BLOC_E3, Chirurgien.ALEX_KAREV, 110),
    BLOC_U6_KATHERINE_HEIGL("BLOC_U6/KATHERINE HEIGL", Salle.BLOC_U6, Chirurgien.KATHERINE_HEIGL, 111),
    BLOC_U5_KATHERINE_HEIGL("BLOC_U5/KATHERINE HEIGL", Salle.BLOC_U5, Chirurgien.KATHERINE_HEIGL, 112),
    BLOC_E2_KATHERINE_HEIGL("BLOC_E2/KATHERINE HEIGL", Salle.BLOC_E2, Chirurgien.KATHERINE_HEIGL, 113),
    BLOC_E1_KATHERINE_HEIGL("BLOC_E1/KATHERINE HEIGL", Salle.BLOC_E1, Chirurgien.KATHERINE_HEIGL, 114);

    private String name = "";
    private int value;
    private Chirurgien c;
    private Salle s;

    Paire_Chirurgien_Salle(String name, Salle s, Chirurgien c, int value) {
        this.name = name;
        this.value = value;
        this.c = c;
        this.s = s;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }

    public Chirurgien getChirurgien() {
        return c;
    }

    public Salle getSalle() {
        return s;
    }

    public static ArrayList<Salle> getSallesDuChirurgien(Chirurgien c) {
        ArrayList<Salle> lesSalles = new ArrayList<>();
        for (Paire_Chirurgien_Salle pcs : Paire_Chirurgien_Salle.values()) {
            if (pcs.getChirurgien().equals(c)) {
                if (!(lesSalles.contains(pcs.getSalle()))) {
                    lesSalles.add(pcs.getSalle());
                }
            }
        }
        return lesSalles;
    }

    public static ArrayList<Chirurgien> getChirurgiensDeLaSalle(Salle s) {

        ArrayList<Chirurgien> lesChirurgiens = new ArrayList<>();
        boolean salleAvecJoker = false;

        for (Paire_Chirurgien_Salle pcs : Paire_Chirurgien_Salle.values()) {
            if (pcs.getSalle().equals(s)) {
                if (pcs.getChirurgien().getNom().equals("Joker")) {
                    salleAvecJoker = true;
                }
                if (!(lesChirurgiens.contains(pcs.getChirurgien()))) {
                    lesChirurgiens.add(pcs.getChirurgien());
                }
            }
        }

        if (salleAvecJoker) {
            for (Paire_Chirurgien_Salle pcs : Paire_Chirurgien_Salle.values()) {
                if (!(lesChirurgiens.contains(pcs.getChirurgien()))) {
                    lesChirurgiens.add(pcs.getChirurgien());
                }
            }

            Iterator<Chirurgien> it = lesChirurgiens.iterator();
            while (it.hasNext()) {
                Chirurgien c = it.next();
                if (c.getNom().equals("Joker")) {
                    it.remove();
                }
            }
        }

        return lesChirurgiens;
    }
}
