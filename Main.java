public class Main {
    public static void main(String[] args){
        Firewall f = new Firewall("Rules.csv");
        f.MergeRules();
        //f.printRules();
        System.out.println("test");
        System.out.println("(correct result) rules: result");
        System.out.println("(true)inbound, tcp, 80, 192.168.1.2: " + f.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
        System.out.println("(false)outbound, tcp, 80, 192.168.10.11: " + f.accept_packet("outbound", "tcp", 80, "192.168.10.11"));
        System.out.println("(true)inbound, udp, 53, 192.168.1.1: " + f.accept_packet("inbound", "udp", 53, "192.168.1.1"));
        System.out.println("(true)outbound, udp, 1500, 52.12.48.92: " + f.accept_packet("outbound", "udp", 1500, "52.12.48.92"));

    }
}