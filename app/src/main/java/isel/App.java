/*
MIT License

Copyright (c) 2024, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel;

import isel.sisinf.jpa.Bicicleta;
import isel.sisinf.jpa.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repository.JPAContext;


import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

interface DbWorker
{
    void doWork();
}
class UI {
    private enum Option {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createCostumer,
        listExistingBikes,
        checkBikeAvailability,
        obtainBookings,
        makeBooking,
        cancelBooking,
        about
    }

    private static UI __instance = null;

    private HashMap<Option, DbWorker> __dbMethods;

    private UI() {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option, DbWorker>();
        __dbMethods.put(Option.createCostumer, () -> UI.this.createCostumer());
        __dbMethods.put(Option.listExistingBikes, () -> UI.this.listExistingBikes());
        __dbMethods.put(Option.checkBikeAvailability, () -> UI.this.checkBikeAvailability());
        __dbMethods.put(Option.obtainBookings, new DbWorker() {
            public void doWork() {
                UI.this.obtainBookings();
            }
        });
        __dbMethods.put(Option.makeBooking, new DbWorker() {
            public void doWork() {
                UI.this.makeBooking();
            }
        });
        __dbMethods.put(Option.cancelBooking, new DbWorker() {
            public void doWork() {
                UI.this.cancelBooking();
            }
        });
        __dbMethods.put(Option.about, new DbWorker() {
            public void doWork() {
                UI.this.about();
            }
        });

    }

    public static UI getInstance() {
        // DO NOT CHANGE ANYTHING!
        if (__instance == null) {
            __instance = new UI();
        }
        return __instance;
    }

    private Option DisplayMenu() {
        Option option = Option.Unknown;
        Scanner s = new Scanner(System.in);
        try {
            // DO NOT CHANGE ANYTHING!
            System.out.println("Bicycle reservation");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Costumer");
            System.out.println("3. List Existing Bikes");
            System.out.println("4. Check Bike Availability");
            System.out.println("5. Current Bookings");
            System.out.println("6. Make a booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. About");
            System.out.print(">");

            int result = s.nextInt();
            option = Option.values()[result];

        } catch (RuntimeException ex) {
            //nothing to do.
        } finally {

        }
        return option;

    }

    private static void clearConsole() throws Exception {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");

    }

    public void Run() throws Exception {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            } catch (NullPointerException ex) {
                //Nothing to do. The option was not a valid one. Read another.
            }

        } while (userInput != Option.Exit);
    }

    /**
     * To implement from this point forward. Do not need to change the code above.
     * -------------------------------------------------------------------------------
     * IMPORTANT:
     * --- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
     * --- Other Methods and properties can be added to support implementation -------
     * -------------------------------------------------------------------------------
     */

    private void createCostumer() {
        try (JPAContext ctx = new JPAContext()) {

            Cliente c = getClientFromConsole();

            ctx.getClientesRepo().create(c);
        } catch (Exception e) {
            System.out.println("Error: " + e.getCause().toString() + " - " + e.getMessage());
        }
        System.out.println("createCostumer()");
    }

    private Cliente getClientFromConsole() {

        Scanner scanner = new Scanner(System.in);
        Cliente cliente = new Cliente();

        System.out.println("Nome do cliente: ");
        cliente.setNome(scanner.nextLine());

        System.out.println("Morada do cliente: ");
        cliente.setMorada(scanner.nextLine());

        System.out.println("Email do cliente: ");
        cliente.setEmail(scanner.nextLine());

        System.out.println("Telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());

        System.out.println("CC/Passaporte do cliente: ");
        cliente.setCcPassaporte(scanner.nextLine());

        System.out.println("Nacionalidade do cliente: ");
        cliente.setNacionalidade(scanner.nextLine());

        cliente.setAtivo(true);

        return cliente;

    }

    private void listExistingBikes() {
        try (JPAContext ctx = new JPAContext()) {
            List<Bicicleta> bikes = ctx.getBiciclesRepo().getBicycles();
            for (Bicicleta b : bikes) {
                System.out.println(b.toString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getCause().toString() + " - " + e.getMessage());
        }

    }

    private void checkBikeAvailability() {
        try (JPAContext ctx = new JPAContext()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter bike ID: ");
            int id = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter start date (yyyy-mm-dd hh:mm:ss): ");
            Timestamp dataInicio = Timestamp.valueOf(scanner.nextLine());

            System.out.print("Enter end date (yyyy-mm-dd hh:mm:ss): ");
            Timestamp dataFim = Timestamp.valueOf(scanner.nextLine());

            Boolean result =  ctx.getBiciclesRepo().podeSerReservado(id, dataInicio, dataFim);

            System.out.println("Pode ser reservado: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getCause().toString() + " - " + e.getMessage());
        }
    }



    private void obtainBookings() {
        // TODO
        System.out.println("obtainBookings()");
    }

    private void makeBooking()
    {
        // TODO
        System.out.println("makeBooking()");
        
    }

    private void cancelBooking()
    {
        // TODO
        System.out.println("cancelBooking");
        
    }
    private void about()
    {
        // TODO: Add your Group ID & member names
        System.out.println("DAL version:"+isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+isel.sisinf.model.Core.version());
        
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}