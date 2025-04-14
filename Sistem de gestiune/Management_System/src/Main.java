import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


class Guest {

    String lastName, firstName, email, phoneNumber;

    public Guest(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null && this.getClass() != obj.getClass())
            return false;

        Guest guest = (Guest) obj;
        if (super.equals(guest) && this.lastName.equals(guest.lastName) && this.firstName.equals(guest.firstName)
                && this.email.equals(guest.email) && this.phoneNumber.equals(guest.phoneNumber)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Guest lastName=" + lastName + ", firstName=" + firstName + ", email=" + email;
    }

    public String fullName() {
        return "[" + lastName + " " + firstName + "]";
    }
}

class GuestsList {
    int guestsCapacity;
    ArrayList<Guest> guests = new ArrayList<>();
    ArrayList<Guest> waitingList = new ArrayList<>();

    public GuestsList(int guestsCapacity) {
        this.guestsCapacity = guestsCapacity;
    }

    /**
     * Add a new, unique guest to the list.
     *
     * @param g the guest to be added
     * @return '-1' if the guest is already present, '0' if is a guest, or the
     *         number on the waiting list
     */
    public int add(Guest g) {
        if (isOnTheListAlready(g)) {
            return -1;
        }

        if (guests.size() < guestsCapacity) {
            guests.add(g);
            System.out.println(g.fullName() + " Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
            return 0;
        } else {
            waitingList.add(g);
            int position = waitingList.size();
            System.out.println(g.fullName() + " Te-ai inscris cu succes in lista de asteptare si ai primit numarul de" +
                    " ordine " + position + ". Te vom notifica daca un loc devine disponibil.");
            return position;
        }
    }

    /**
     * Check if someone is already registered ( as a guest, or on the waiting
     * list).
     *
     * @param g the guest we are searching for
     * @return true if present, false if not
     */
    private boolean isOnTheListAlready(Guest g) {
        return guests.contains(g) || waitingList.contains(g);
    }

    /**
     * Search for a guest based on first and last name. Return the first result.
     *
     * @param firstName first name of the guest
     * @param lastName  last name of the guest
     * @return the guest if found, null if not
     */
    public Guest search(String firstName, String lastName) {
        for (Guest g : guests) {
            if (g.getFirstName().equalsIgnoreCase(firstName) && g.getLastName().equalsIgnoreCase(lastName)) {
                return g;
            }
        }
        for (Guest g : waitingList) {
            if (g.getFirstName().equalsIgnoreCase(firstName) && g.getLastName().equalsIgnoreCase(lastName)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Search for a guest based on email or phone number. Return the first result.
     *
     * @param opt option to use for searching: 2 for email, 3 for phone number
     * @param match what is searched for
     * @return the guest if found, null if not
     */
    public Guest search(int opt, String match) {
        for (Guest g : guests) {
            if ((opt == 2 && g.getEmail().equalsIgnoreCase(match)) || (opt == 3 && g.getPhoneNumber().equals(match))) {
                return g;
            }
        }
        for (Guest g : waitingList) {
            if ((opt == 2 && g.getEmail().equalsIgnoreCase(match)) || (opt == 3 && g.getPhoneNumber().equals(match))) {
                return g;
            }
        }
        return null;
    }

    /**
     * Remove a guest based on first and last name. Remove the first result.
     *
     * @param firstName first name of the guest
     * @param lastName  last name of the guest
     * @return true if removed, false if not
     */
    public boolean remove(String firstName, String lastName) {
        for (Guest g : guests) {
            if (g.getFirstName().equalsIgnoreCase(firstName) && g.getLastName().equalsIgnoreCase(lastName)) {
                guests.remove(g);
                moveFromWaitingList();
                return true;
            }
        }
        for (Guest g : waitingList) {
            if (g.getFirstName().equalsIgnoreCase(firstName) && g.getLastName().equalsIgnoreCase(lastName)) {
                waitingList.remove(g);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a guest based on email or phone number. Remove the first result.
     *
     * @param opt   option to use for searching: 2 for email, 3 for phone number
     * @param match the match we are searching for
     * @return true if removed, false if not
     */
    public boolean remove(int opt, String match) {
        for (Guest g : guests) {
            if ((opt == 2 && g.getEmail().equalsIgnoreCase(match)) || (opt == 3 && g.getPhoneNumber().equals(match))) {
                guests.remove(g);
                moveFromWaitingList();
                return true;
            }
        }
        for (Guest g : waitingList) {
            if ((opt == 2 && g.getEmail().equalsIgnoreCase(match)) || (opt == 3 && g.getPhoneNumber().equals(match))) {
                waitingList.remove(g);
                return true;
            }
        }
        return false;
    }

    private void moveFromWaitingList() {
        if (!waitingList.isEmpty() && guests.size() < guestsCapacity) {
            Guest nextGuest = waitingList.remove(0);
            guests.add(nextGuest);
            System.out.println(nextGuest.fullName()+" Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
        }
    }

    // Show the list of guests.
    public void showGuestsList() {
        int i = 1;
        for (Guest g : guests) {
            System.out.println(i++ + ". Nume: " + g.getLastName() + " " + g.getFirstName() +
                    ", Email: " + g.getEmail() + ", Telefon: " + g.getPhoneNumber());
        }
    }

    // Show the people on the waiting list.
    public void showWaitingList() {
        int i = 1;
        for (Guest g : waitingList) {
            System.out.println(i++ + ". Nume: " + g.getLastName() + " " + g.getFirstName() +
                    ", Email: " + g.getEmail() + ", Telefon: " + g.getPhoneNumber());
        }

        if (waitingList.isEmpty())
            System.out.println("Lista de asteptare este goala...");
    }

    /**
     * Show how many free spots are left.
     *
     * @return the number of spots left for guests
     */
    public int numberOfAvailableSpots() {
        return guestsCapacity - guests.size();
    }

    /**
     * Show how many guests there are.
     *
     * @return the number of guests
     */
    public int numberOfGuests() {
        return guests.size();
    }

    /**
     * Show how many people are on the waiting list.
     *
     * @return number of people on the waiting list
     */
    public int numberOfPeopleWaiting() {
        return waitingList.size();
    }

    /**
     * Show how many people there are in total, including guests.
     *
     * @return how many people there are in total
     */
    public int numberOfPeopleTotal() {
        return guests.size() + waitingList.size();
    }

    /**
     * Find all people based on a partial value search.
     *
     * @param match the match we are looking for
     * @return a list of people matching the criteria
     */
    public List<Guest> partialSearch(String match) {
        List<Guest> result = new ArrayList<>();
        for (Guest g : guests) {
            if (g.getFirstName().contains(match) || g.getLastName().contains(match) || g.getEmail().contains(match)
                    || g.getPhoneNumber().contains(match)) {
                result.add(g);
            }
        }
        for (Guest g : waitingList) {
            if (g.getFirstName().contains(match) || g.getLastName().contains(match) || g.getEmail().contains(match)
                    || g.getPhoneNumber().contains(match)) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Guests: " + guests.toString() + "\nWaiting List: " + waitingList.toString();
    }
}

public class Main {
    private static void showCommands() {
        System.out.println("help         - Afiseaza aceasta lista de comenzi");
        System.out.println("add          - Adauga o noua persoana (inscriere)");
        System.out.println("check        - Verifica daca o persoana este inscrisa la eveniment");
        System.out.println("remove       - Sterge o persoana existenta din lista");
        System.out.println("update       - Actualizeaza detaliile unei persoane");
        System.out.println("guests       - Lista de persoane care participa la eveniment");
        System.out.println("waitlist     - Persoanele din lista de asteptare");
        System.out.println("available    - Numarul de locuri libere");
        System.out.println("guests_no    - Numarul de persoane care participa la eveniment");
        System.out.println("waitlist_no  - Numarul de persoane din lista de asteptare");
        System.out.println("subscribe_no - Numarul total de persoane inscrise");
        System.out.println("search       - Cauta toti invitatii conform sirului de caractere introdus");
        System.out.println("save         - Salveaza lista cu invitati");
        System.out.println("restore      - Completeaza lista cu informatii salvate anterior");
        System.out.println("reset        - Sterge informatiile salvate despre invitati");
        System.out.println("quit         - Inchide aplicatia");
    }

    private static void addNewGuest(Scanner sc, GuestsList list) {
        String firstName = sc.nextLine();
        String lastName = sc.nextLine();
        String email = sc.nextLine();
        String phone = sc.nextLine();

        Guest newGuest = new Guest(firstName, lastName, email, phone);
        list.add(newGuest);
    }

    private static void checkGuest(Scanner sc, GuestsList list) {
        String option = sc.nextLine().trim();

        Guest foundGuest = null;

        switch (option) {
            case "1":
                String lastName = sc.nextLine();
                String firstName = sc.nextLine();
                foundGuest = list.search(firstName, lastName);
                break;
            case "2":
                String email = sc.nextLine();
                foundGuest = list.search(2, email);
                break;
            case "3":
                String phoneNumber = sc.nextLine();
                foundGuest = list.search(3, phoneNumber);
                break;
            default:
                System.out.println("Optiune invalida. Te rugăm să introduci '1', '2' sau '3'.");
                return;
        }

        if (foundGuest != null) {
            System.out.println("Nume: " + foundGuest.getLastName() + " " + foundGuest.getFirstName() +
                    ", Email: " + foundGuest.getEmail() + ", Telefon: " + foundGuest.getPhoneNumber());
        } else {
            System.out.println("Persoana nu este pe lista.");
        }
    }

    private static void removeGuest(Scanner sc, GuestsList list) {
        String option = sc.nextLine().trim();

        Guest guest = null;

        switch (option) {
            case "1":
                String lastName = sc.nextLine();
                String firstName = sc.nextLine();
                guest = list.search(firstName, lastName);
                break;
            case "2":
            case "3":
                String field = sc.nextLine();
                guest = list.search(Integer.parseInt(option), field);
                break;
            default:
                System.out.println("Optiune invalida.");
                return;
        }


        if (guest != null)
            list.remove(guest.getFirstName(), guest.getLastName());
    }

    private static void updateGuest(Scanner sc, GuestsList list) {
        String option = sc.nextLine().trim();

        Guest guest = null;

        switch (option) {
            case "1":
                String lastName = sc.nextLine();
                String firstName = sc.nextLine();
                guest = list.search(firstName, lastName);
                break;
            case "2":
                String email = sc.nextLine();
                guest = list.search(2, email);
                break;
            case "3":
                String phoneNumber = sc.nextLine();
                guest = list.search(3, phoneNumber);
                break;
            default:
                System.out.println("Optiune invalida.");
                return;
        }

        if (guest != null) {
            String fieldOption = sc.nextLine().trim();

            switch (fieldOption) {
                case "1":
                    guest.setLastName(sc.nextLine());
                    break;
                case "2":
                    guest.setFirstName(sc.nextLine());
                    break;
                case "3":
                    guest.setEmail(sc.nextLine());
                    break;
                case "4":
                    guest.setPhoneNumber(sc.nextLine());
                    break;
                default:
                    System.out.println("Optiune invalida.");
            }
        } else {
            System.out.println("Persoana nu a fost gasita.");
        }
    }

    private static void searchList(Scanner sc, GuestsList list) {
        String match = sc.nextLine();
        List<Guest> results = list.partialSearch(match);
        for (Guest g : results) {
            System.out.println("Nume: " + g.getLastName() + " " + g.getFirstName() + ", Email: " + g.getEmail() +
                    ", Telefon: " + g.getPhoneNumber());
        }

        if (results.isEmpty())
            System.out.println("Nothing found");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();

        GuestsList list = new GuestsList(size);

        boolean running = true;
        while (running) {
            String command = scanner.nextLine();

            switch (command) {
                case "help":
                    showCommands();
                    break;
                case "add":
                    addNewGuest(scanner, list);
                    break;
                case "check":
                    checkGuest(scanner, list);
                    break;
                case "remove":
                    removeGuest(scanner, list);
                    break;
                case "update":
                    updateGuest(scanner, list);
                    break;
                case "guests":
                    list.showGuestsList();
                    break;
                case "waitlist":
                    list.showWaitingList();
                    break;
                case "available":
                    System.out.println("Numarul de locuri ramase: " + list.numberOfAvailableSpots());
                    break;
                case "guests_no":
                    System.out.println("Numarul de participanti: " + list.numberOfGuests());
                    break;
                case "waitlist_no":
                    System.out.println("Dimensiunea listei de asteptare: " + list.numberOfPeopleWaiting());
                    break;
                case "subscribe_no":
                    System.out.println("Numarul total de persoane: " + list.numberOfPeopleTotal());
                    break;
                case "search":
                    searchList(scanner, list);
                    break;
                case "quit":
                    System.out.println("Aplicatia se inchide...");
                    scanner.close();
                    running = false;
                    break;
                default:
                    System.out.println("Comanda introdusa nu este valida.");
                    System.out.println("Incercati inca o data.");

            }
        }
    }
}