/*
 * Actualització de fitxers amb un fitxer d'actualitzacions que conté algunes
 * modificacions dels registres originals. Solucio basada en la fusio simètrica
 *
 * Amb un menú simple es gestionen les opcions d'inserir articles i actualitzacions,
 * veure els continguts dels 4 fitxers del problema i fer el procés d'actualització.
 *
 * El control d'errors no està fet
 *
 * Per representar els fitxers d'articles i d'actualitzacions s'usarà el fluxe
 * d'objectes.
 * Hi ha mètodes que serveixen per inserir un grup d'articles i un grup actualitzacions
 * per ambdós l'usuari ha d'entrar els registres ordenats per codi, si no ho
 * fa així el programa no funcionará. 
 * Aquests mètodes són similars, la justificació del perquè siguin dos diferents és
 * que els registres d'articles i modificacions poden ser completament diferents
 * mentre que continguin el camp de codi.
 * De forma similar hi ha dos mètodes que mostren el contingut dels fitxers
 * fent un recorregut dels mateixos
 */
package pkg090_actualitzaciofixers;

/**
 *
 * @author miquelmascarooliver
 */
import DefArticles.Actualitzacio;
import DefArticles.Article;
import java.io.*;

public class ActualitzacioFitxers {

    public static void main(String[] args) {
        boolean sortir = false;
        int opcio = 0;
        while (!sortir) {
            menu();
            opcio = llegirNum("\n\n\tInserir opció: ");
            switch (opcio) {
                case 1:
                    insereixArticlesOrdre();
                    break;
                case 2:
                    insereixActualitzacionsOrdre();
                case 3:
                    actualitza();
                    break;
                case 4:
                    mostraArticles("articles.dat");
                    break;
                case 5:
                    mostraActualitzacions("actualitzacions.dat");
                    break;
                case 6:
                    mostraArticles("nouarticles.dat");
                    break;
                case 7:
                    mostraActualitzacions("anomalies.dat");
                    break;
                case 0:
                    sortir = true;
                    break;
            }

        }
    }

    private static void menu() {
        System.out.println("\n\nACTUALITZACIÓ DE FITXERS D'ARTICLES");
        System.out.println("\n\t1. Inserir articles");
        System.out.println("\t2. Inserir modificacions");
        System.out.println("\t3. Actualitza articles");
        System.out.println("\t4. Veure articles");
        System.out.println("\t5. Veure modificacions");
        System.out.println("\t6. Veure nous articles");
        System.out.println("\t7. Veure anomalies");
        System.out.println("\t0. Sortir");
    }

    private static void insereixArticlesOrdre() {
        try {
            int n = llegirNum("\nQuans registres vols entrar? ");
            FileOutputStream sortida = new FileOutputStream("articles.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);
            System.out.println("Insereix les dades de " + n + " articles"
                    + "\nEls codis de menor a major");
            for (int i = 1; i <= n; i++) {
                Article a = new Article();
                String s = llegirCadena("Nom article: ");
                int cod = llegirNum("Codi article: ");
                a.setNom(s);
                a.setCodi(cod);
                oos.writeObject(a);
            }
            oos.writeObject(Article.CENTINELA);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.fillInStackTrace());
        }
    }

    private static void mostraArticles(String nom) {
        try {
            FileInputStream entrada = new FileInputStream(nom);
            ObjectInputStream ois = new ObjectInputStream(entrada);

            Article a = (Article) ois.readObject();
            while (!a.esCentinela()) {
                System.out.println(a);
                a = (Article) ois.readObject();
            }
            ois.close();
            System.out.println("A reveure");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void insereixActualitzacionsOrdre() {
        try {
            int n = llegirNum("\nQuans registres vols entrar? ");
            FileOutputStream sortida = new FileOutputStream("actualitzacions.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);
            System.out.println("Insereix les dades de " + n + " actualitzacions"
                    + "\nEls codis de menor a major");
            for (int i = 1; i <= n; i++) {
                Actualitzacio m = new Actualitzacio();
                String s = llegirCadena("Nom de l'article: ");
                int cod = llegirNum("Codi de l'article: ");
                m.setNom(s);
                m.setCodi(cod);
                m.setAct();
                oos.writeObject(m);
            }
            oos.writeObject(Actualitzacio.CENTINELA);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.fillInStackTrace());
        }
    }

    private static void mostraActualitzacions(String nom) {
        try {
            FileInputStream entrada = new FileInputStream(nom);
            ObjectInputStream ois = new ObjectInputStream(entrada);

            Actualitzacio m = (Actualitzacio) ois.readObject();
            while (!m.esCentinela()) {
                System.out.println(m);
                m = (Actualitzacio) ois.readObject();
            }
            ois.close();
            System.out.println("A reveure");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void actualitza() {
        try {
            FileInputStream e1 = new FileInputStream("articles.dat");
            ObjectInputStream master = new ObjectInputStream(e1);
            FileInputStream e2 = new FileInputStream("actualitzacions.dat");
            ObjectInputStream updt = new ObjectInputStream(e2);
            FileOutputStream s1 = new FileOutputStream("nouarticles.dat");
            ObjectOutputStream nmaster = new ObjectOutputStream(s1);
            FileOutputStream s2 = new FileOutputStream("anomalies.dat");
            ObjectOutputStream anom = new ObjectOutputStream(s2);
            Article a = (Article) master.readObject();
            Actualitzacio m = (Actualitzacio) updt.readObject();
            
             while (!a.esCentinela()) {
                while (m.getCodi() < a.getCodi()) {
                    anom.writeObject(m);
                    m = (Actualitzacio) updt.readObject();
                }
                while(a.getCodi() == m.getCodi()){
                    a.modifica(m);
                    m = (Actualitzacio) updt.readObject();
                }
                
                nmaster.writeObject(a);
                a = (Article) master.readObject();
               
            }
            while (!m.esCentinela()) {
                nmaster.writeObject(m);
                m = (Actualitzacio) updt.readObject();
            }
           
            
            
            
            
            nmaster.writeObject(Article.CENTINELA);
            anom.writeObject(Actualitzacio.CENTINELA);
            master.close();
            nmaster.close();
            updt.close();
            anom.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }    
    
    private static String llegirCadena(String msg) {
        String s = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            s = in.readLine();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return s;

    }

    private static int llegirNum(String msg) {
        int x = 0;
        try {
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            s = in.readLine();
            x = Integer.parseInt(s);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return x;
    }
}
