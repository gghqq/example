package concurrency.chapter2;












public class BankWindow
{
    public static void main(String[] args) {
        TicketRunnable ticketRunnable = new TicketRunnable();
        (new Thread(ticketRunnable, "一号柜台")).start();
        (new Thread(ticketRunnable, "二号柜台")).start();
        (new Thread(ticketRunnable, "三号柜台")).start();
    }
}

