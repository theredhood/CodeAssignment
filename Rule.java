import java.io.*;
import java.util.*;
public class Rule {
    int[] port;
    long[] ip;
    public Rule(int[] port, long[] ip) {
        this.port = port;
        this.ip = ip;
    }
    public boolean allow(int port, long ip) {
        if (port>this.port[1] || port<this.port[0]) return false;
        if (ip<this.ip[0] || ip>this.ip[1]) return false;
        return true;
    }
}