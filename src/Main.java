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

        findConnection(actor);

        pathway.add(kevin);
        String path = "";
        for (String object : pathway) {
            path += (object + " -> ");
        }
        System.out.println(path.substring(0, path.length() - 4));
        System.out.println(actor + " has a Bacon Number of " + baconNumber);
    }

    private static Object findConnection(String actor) {
        ArrayList<Object> kevinMovies = new ArrayList<>();
        ArrayList<Object> actorMovies = new ArrayList<>();

        for (SimpleMovie movie : movies) {
            if (movie.getActors().contains(actor)) {
                actorMovies.add(movie);
            }
            if (movie.getActors().contains(kevin)) {
                kevinMovies.add(movie);
            }
        }

        for (Object movie : kevinMovies) {
            if (actorMovies.contains(movie)) {
                pathway.add(((SimpleMovie) movie).getTitle());
                baconNumber ++;
                return movie;
            }
        }

        return findConnection(actorMovies, kevinMovies);
    }

    private static Object findConnection(ArrayList<Object> obj1, ArrayList<Object> obj2) {

        // maybe done?
        if (obj1.get(0) instanceof SimpleMovie) {
            ArrayList<Object> kevinMovies = new ArrayList<>();
            ArrayList<Object> actorMovies = new ArrayList<>();

            for (Object movie : obj1) {
                actorMovies.addAll(((SimpleMovie) movie).getActors());
            }
            for (Object movie : obj2) {
                kevinMovies.addAll(((SimpleMovie) movie).getActors());
            }

            for (Object movie : kevinMovies) {
                if (actorMovies.contains(movie)) {
                    pathway.add(((SimpleMovie) movie).getTitle());
                    baconNumber ++;
                    return movie;
                }
            }
            findConnection(actorMovies, kevinMovies);



        } else {


            ArrayList<String> kevinMoviesActors = new ArrayList<>();
            ArrayList<String> moviesActors = new ArrayList<>();
            obj1 = (String) obj1;
            for (String )
            for (SimpleMovie movie : movies) {
                if (movie.getTitle().equals) {
                    for (String actors : movie.getActors()) {
                        moviesActors.add(actors);
                    }
                }
            }
        }

    }


}