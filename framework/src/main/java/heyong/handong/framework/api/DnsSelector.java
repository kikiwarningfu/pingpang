package heyong.handong.framework.api;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Dns;

public class DnsSelector implements Dns {

    public enum Mode {
        SYSTEM,
        IPV6_FIRST,
        IPV4_FIRST,
        IPV6_ONLY,
        IPV4_ONLY
    }

    private Map<String, List<InetAddress>> overrides =new HashMap<>();

    private Mode mode;

    public DnsSelector(Mode mode) {
        this.mode = mode;
    }

    public static Dns byName(String ipMode) {
        Mode selectedMode;
        switch (ipMode) {
            case "ipv6":
                selectedMode = Mode.IPV6_FIRST;
                break;
            case "ipv4":
                selectedMode = Mode.IPV4_FIRST;
                break;
            case "ipv6only":
                selectedMode = Mode.IPV6_ONLY;
                break;
            case "ipv4only":
                selectedMode = Mode.IPV4_ONLY;
                break;
            default:
                selectedMode = Mode.SYSTEM;
                break;
        }

        return new DnsSelector(selectedMode);
    }

    @Override public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        List<InetAddress> addresses = overrides.get(hostname.toLowerCase());

        if (addresses != null) {
            return addresses;
        }

        addresses = Dns.SYSTEM.lookup(hostname);

        switch (mode) {
            case IPV6_FIRST: {
                Collections.sort(addresses, new Comparator<InetAddress>() {
                    @Override
                    public int compare(InetAddress lhs, InetAddress rhs) {
                        if (lhs instanceof Inet6Address && rhs instanceof Inet4Address) {
                            return 1;
                        } else if (lhs instanceof Inet4Address && rhs instanceof Inet6Address) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                break;
            }
            case IPV4_FIRST: {
                Collections.sort(addresses, new Comparator<InetAddress>() {
                    @Override
                    public int compare(InetAddress lhs, InetAddress rhs) {
                        if (lhs instanceof Inet6Address && rhs instanceof Inet4Address) {
                            return -1;
                        } else if (lhs instanceof Inet4Address && rhs instanceof Inet6Address) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                break;
            }
            case IPV6_ONLY: {
                List<InetAddress> finalAddresses = new ArrayList<>();
                for (int i = 0; i < addresses.size(); i++) {
                    if (addresses.get(i) instanceof Inet6Address) {
                        finalAddresses.add(addresses.get(i));
                    }
                }
                addresses = finalAddresses;
                break;
            }
            case IPV4_ONLY: {
                List<InetAddress> finalAddresses = new ArrayList<>();
                for (int i = 0; i < addresses.size(); i++) {
                    if (addresses.get(i) instanceof Inet4Address) {
                        finalAddresses.add(addresses.get(i));
                    }
                }
                addresses = finalAddresses;
                break;
            }
            default:
                break;
        }

        return addresses;
    }

    public void addOverride(String hostname, InetAddress address) {
        List<InetAddress> addresses = new ArrayList<>();
        addresses.add(address);
        overrides.put(hostname.toLowerCase(), addresses);
    }
}
