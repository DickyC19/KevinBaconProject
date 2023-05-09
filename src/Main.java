import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");
    private static ArrayList<String> pathway = new ArrayList<>();
    private static int baconNumber = 0;
    private static final String kevin = "Kevin Bacon";
    public static void main(String[] args) {
        System.out.println("Welcome to The 6 Degrees of Kevin Bacon");
        System.out.print("Input an actor to search for (Please capitalize correctly): ");

        String actor = scan.nextLine();
        pathway.add(actor);

        findConnection();

        pathway.add(kevin);
        String path = "";
        for (String object : pathway) {
            path += (object + " -> ");
        }
        System.out.println(path.substring(0, path.length() - 4));
        System.out.println(actor + " has a Bacon Number of " + baconNumber);
    }

    private static Object findConnection() {
        ArrayList<SimpleMovie> kevinMovies = new ArrayList<>();
        ArrayList<SimpleMovie> actorMovies = new ArrayList<>();

        for (SimpleMovie movie : movies) {
            if (movie.getActors().contains(actor)) {
                actorMovies.add(movie);
            }
            if (movie.getActors().contains(kevin)) {
                kevinMovies.add(movie);
            }
        }

        for (SimpleMovie movie : kevinMovies) {
            if (actorMovies.contains(movie)) {
                pathway.add(movie.getTitle());
                baconNumber ++;
                break;
            }
        }
        findConnection();
    }
}