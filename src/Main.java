import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");
    private static ArrayList<Object> pathway = new ArrayList<>();
    private static int baconNumber = 0;
    private static final String kevin = "Kevin Bacon";
    public static void main(String[] args) {
        String path = "";
        System.out.println("Welcome to The 6 Degrees of Kevin Bacon");
        System.out.print("Input an actor to search for (Please capitalize correctly): ");

        String actor = scan.nextLine();
        pathway.add(actor);

        ArrayList<String> allActors = new ArrayList<>();
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

    private static Object findConnection(String actor) {
        System.out.println(1);
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
                pathway.add(movie);
                baconNumber ++;
                return movie;
            }
        }


        findConnection(actorMovies, kevinMovies);

    }

    private static Object findConnection(ArrayList<Object> obj1, ArrayList<Object> obj2) {
        System.out.println(2);
        baconNumber ++;
        ArrayList<Object> actorMovies = new ArrayList<>();
        ArrayList<Object> kevinMovies = new ArrayList<>();

        if (obj1.get(0) instanceof SimpleMovie) {
            System.out.println(3);
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
            System.out.println(4);
            baconNumber ++;
            for (Object actor : obj1) {
                for (SimpleMovie movie : movies) {
                    if (movie.getActors().contains((String) actor) && !actorMovies.contains(movie)) {
                        actorMovies.add(movie);
                    }
                }
            }
            for (Object actor : obj2) {
                for (SimpleMovie movie : movies) {
                    if (movie.getActors().contains((String) actor) && !actorMovies.contains(movie)) {
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

        Object thing1 = findConnection(actorMovies, kevinMovies);
        if (thing1 instanceof SimpleMovie) {
            System.out.println(5);
            for (Object movie : obj1) {
                for (Object actor : actorMovies) {
                     if (((SimpleMovie) movie).getActors().contains((String) actor) && (((SimpleMovie) thing1).getActors().contains(actor))) {
                         pathway.add(1, actor);
                     }
                }
            }
            for (Object movie : obj2) {
                for (Object actor : kevinMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && (((SimpleMovie) thing1).getActors().contains(actor))) {
                        pathway.add(actor);
                    }
                }
            }
        } else {
            System.out.println(6);
            // obj1 is actor, actormovies is movies
            for (Object actor : obj1) {
                for (Object movie : actorMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && ((SimpleMovie) movie).getActors().contains((String) thing1)) {
                        pathway.add(1,  movie);
                    }
                }
            }
            for (Object actor : obj2) {
                for (Object movie : actorMovies) {
                    if (((SimpleMovie) movie).getActors().contains((String) actor) && ((SimpleMovie) movie).getActors().contains((String) thing1)) {
                        pathway.add(movie);
                    }
                }
            }
        }
        return pathway.get(1);
    }





}