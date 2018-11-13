import java.io.*;
import java.util.*;
public class Firewall {
    private Map<String, Set<Rule>> map = new HashMap<>();
    private long ipToLong(String ip) {
        long result = 0L;
        String[] ips = ip.split("\\.");
        for(int i = 0; i < 4; i++)
            result = result * 256 + Integer.parseInt(ips[i]);
        return result;
    }

    private Rule mergeIp(Rule a, Rule b) {
        long[] temp = new long[2];
        temp[0] = Math.min(a.ip[0], b.ip[0]);
        temp[1] = Math.max(a.ip[1], b.ip[1]);
        return new Rule(a.port, temp);

    }

    private Rule mergePort(Rule a, Rule b) {
        int[] temp = new int[2];
        temp[0] = Math.min(a.port[0], b.port[0]);
        temp[1] = Math.max(a.port[1], b.port[1]);
        return new Rule(temp, a.ip);
    }

    public void MergeRules() {
        for (Map.Entry<String, Set<Rule>> entry: map.entrySet()) {
            Set<Rule> rules = entry.getValue();
            //List<Rule> addressList = new ArrayList<>(set);
            for(Rule a : rules){
                for(Rule b : rules){
                    if(a.equals(b))
                        continue;
                    else if (a.port[0] != b.port[0] || a.port[1] != b.port[1]){
                         if (a.ip[0]<=b.ip[0] &&  a.ip[1]>=b.ip[0] || a.ip[0]<=b.ip[1] && b.ip[1]<=a.ip[1]){
                            Rule merge = mergePort(a,b);
                            rules.remove(a);
                            rules.remove(b);
                            rules.add(merge);
                         }
                    }
                    else if (a.ip[0] != b.ip[0] || a.ip[1] != b.ip[1]){
                        if (a.ip[0]<=b.ip[0] &&  a.ip[1]>=b.ip[0] || a.ip[0]<=b.ip[1] && b.ip[1]<=a.ip[1]){
                            Rule merge = mergeIp(a,b);
                            rules.remove(a);
                            rules.remove(b);
                            rules.add(merge);
                        }
                    }
                }
            }
            map.put(entry.getKey(), rules);
        }
    }
    public boolean accept_packet(String direction, String protocal, int port, String ip) {
        String key = direction + "," + protocal;
        long ipLong = ipToLong(ip);
        if (map.get(key) == null) 
            return false;
        for (Rule address : map.get(key)) {
            if (address.allow(port, ipLong)) 
                return true;
        }
        return false;
    }
    public Firewall(String filePath) {
        File csv = new File(filePath);  
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                String[] rule = line.split("\\,");
                String key = rule[0] + "," + rule[1];
                int[] port = new int[2];
                if (rule[2].indexOf('-') >= 0) {
                    String[] ports = rule[2].split("\\-");
                    port[0] = Integer.parseInt(ports[0]);
                    port[1] = Integer.parseInt(ports[1]);
                } else {
                    port[0] = Integer.parseInt(rule[2]);
                    port[1] = port[0];
                }
                long[] ip = new long[2];
                if (rule[3].indexOf('-') >= 0) {
                    String[] ips = rule[3].split("\\-");
                    ip[0] = ipToLong(ips[0]);
                    ip[1] = ipToLong(ips[1]);
                } else {
                    ip[0] = ipToLong(rule[3]);
                    ip[1] = ip[0];
                }
                map.computeIfAbsent(key, k -> new HashSet<>());
                map.get(key).add(new Rule(port, ip));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printRules(){
        for (Map.Entry<String, Set<Rule>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<Rule> rules = entry.getValue();
            for(Rule rule : rules)
                System.out.println(key +"   " + rule.port[0] +"to"+rule.port[1] + "   " + rule.ip[0]+"to"+rule.ip[1]);
    
        }
    }
}