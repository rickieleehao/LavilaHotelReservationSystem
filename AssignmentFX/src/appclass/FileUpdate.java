package appclass;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileUpdate {

    private FileUpdate() {
    }

    public static void updateReservation(ArrayList<Reservation> reservationList) throws IOException {
        PrintWriter revFile = new PrintWriter("reservation.txt");

        for (Reservation reservation : reservationList) {
            revFile.write(reservation.getID() + "," + reservation.getGuest().getIC() + "," + reservation.getRoomNumber()
                    + "," + reservation.getCheckinDate() + "," + reservation.getCheckoutDate() + ","
                    + reservation.getAdultPax() + "," + reservation.getChildPax() + "," + reservation.getStatus()
                    + "\n");
        }

        revFile.close();
    }
}
