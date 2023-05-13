import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static final ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");
    private static ArrayList<Object> pathway = new ArrayList<>();
    private static int baconNumber = 0;
    private static final String kevin = "Kevin Bacon";
    public static void main(String[] args) {
        System.out.println("Welcome to The 6 Degrees of Kevin Bacon");

        String actor = "aldsjaklf";

        while (!actor.equals("q")) {
            String path = "";
            pathway = new ArrayList<>();
            baconNumber = 0;

            System.out.print("\nInput an actor to search for (Please capitalize correctly) (Type \"q\" to quit): ");

            actor = scan.nextLine();
            pathway.add(actor);

            ArrayList<String> allActors = new ArrayList<>();
            assert movies != null;
            for (SimpleMovie movie : movies) {
                allActors.addAll(movie.getActors());
            }

            while (!allActors.contains(actor)) {
                System.out.print("There is no actor by that name\nPlease input an actor to search for: ");
                actor = scan.nextLine();
            }

            if (!actor.equals(kevin)) {
                findConnection(actor);
            }

            pathway.add(kevin);

            for (Object object : pathway) {
                if (object instanceof SimpleMovie) {
                    path += (((SimpleMovie) object).getTitle() + " -> ");
                } else {
                    path += (object + " -> ");
                }
            }

            System.out.println(path.substring(0, path.length() - 4));
            System.out.println(actor + " has a Bacon Number of " + baconNumber);
        }
    }

    private static void findConnection(String actor) {
        ArrayList<Object> kevinMovies = new ArrayList<>();
        ArrayList<Object> actorMovies = new ArrayList<>();

        assert movies != null;
        // setting up the initial arrayLists
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
                pathway.add(movie);
                baconNumber ++;
            }
        }

        // finishes developing the connection
        if (pathway.size() >= 1) {
            Object connection = findConnection(actorMovies, kevinMovies);
            baconNumber ++;
            for (Object movie : actorMovies) {
                if (((SimpleMovie) movie).getActors().contains((String) connection)) {
                    pathway.add(1, movie);
                    break;
                }
            }
            for (Object movie : kevinMovies) {
                if (((SimpleMovie) movie).getActors().contains(pathway.get(pathway.size() - 1))) {
                    pathway.add(movie);
                    break;
                }
            }
        }
    }

    private static Object findConnection(ArrayList<Object> obj1, ArrayList<Object> obj2) {
        baconNumber ++;
        ArrayList<Object> actorMovies = new ArrayList<>();
        ArrayList<Object> kevinMovies = new ArrayList<>();

        // finds the connection
        if (obj1.get(0) instanceof SimpleMovie) {

            for (Object movie : obj1) {
                actorMovies.addAll(((SimpleMovie) movie).getActors());
            }
            for (Object movie : obj2) {
                kevinMovies.addAll(((SimpleMovie) movie).getActors());
            }
            for (Object actor : kevinMovies) {
                if (actorMovies.contains(actor)) {
                    pathway.add(actor);
                    return actor;
                }
            }

        } else { //if the parameter for findConnection is an actor

            for (Object actor : obj1) {
                for (SimpleMovie movie : movies) {
                    if (movie.getActors().contains((String) actor) && !actorMovies.contains(movie)) {
                        actorMovies.add(movie);
                    }
                }
            }
            for (Object actor : obj2) {
                for (SimpleMovie movie : movies) {
                    if (movie.getActors().contains((String) actor) && !kevinMovies.contains(movie)) {
                        kevinMovies.add(movie);
                    }
                }
            }

            for (Object movie : kevinMovies) {
                if (actorMovies.contains(movie)) {
                    pathway.add(movie);
                    return movie;
                }
            }
        }

        Object connection = findConnection(actorMovies, kevinMovies);

        // develops the pathway to connection
        if (connection instanceof SimpleMovie) {

            Object connectedMovie = pathway.get(pathway.size() - 1);

            // first half of the pathway
            for (Object movie : obj1) {
                boolean broken = false;
                for (Object actor : actorMovies) {
                     if (((SimpleMovie) movie).getActors().contains((String) actor) && (((SimpleMovie) connection).getActors().contains(actor))) {
                         pathway.add(1, actor);
                         broken = true;
                         break;
                     }
                }
                if (broken) {
                    break;
                }
            }

            // second half of the pathway
            for (Object movie : obj2) {
                for (Object actor : kevinMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && (((SimpleMovie) connectedMovie).getActors().contains(actor))) {
                        pathway.add(actor);
                        return pathway.get(1);
                    }
                }
            }
        } else {

            // first half of the pathway
            for (Object actor : obj1) {
                boolean broken = false;
                for (Object movie : actorMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && ((SimpleMovie) movie).getActors().contains((String) connection)) {
                        pathway.add(1,  movie);
                        broken = true;
                        break;
                    }
                }
                if (broken) {
                    break;
                }
            }

            // second half of the pathway
            for (Object actor : obj2) {
                for (Object movie : actorMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && ((SimpleMovie) movie).getActors().contains(pathway.get(pathway.size() - 1))) {
                        pathway.add(movie);
                        return pathway.get(1);
                    }
                }
            }
        }
        return pathway.get(1); // random return
    }
}