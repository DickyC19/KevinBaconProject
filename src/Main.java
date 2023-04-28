import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int baconNumber = 0;
        ArrayList<String> pathway = new ArrayList<>();
        ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");

        System.out.println("Welcome to The 6 Degrees of Kevin Bacon");
        System.out.print("Input an actor to search for (Please capitalize correctly): ");
        final String kevin = "Kevin Bacon";
        ArrayList<SimpleMovie> kevinMovies = new ArrayList<>();
        String actor = scan.nextLine();
        pathway.add(actor);
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
                break;
            }
        }

        pathway.add(kevin);
        String path = "";
        for (String object : pathway) {
            path += (object + " -> ");
        }
        System.out.println(path.substring(0, path.length() - 4));
        System.out.println(actor + " has a Bacon Number of " + baconNumber);
    }

    private static Object findConnection(ArrayList<SimpleMovie> movies) {

    }
}