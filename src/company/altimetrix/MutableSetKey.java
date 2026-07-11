package company.altimetrix;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MutableSetKey {

    public static void main(String[] args) {
        Set<Device> set = new HashSet<>();
        Device device = new Device(8080, "10.10.10.10");
        set.add(device);

        System.out.println(set.contains(device));

        //update value
        device.setPort(1000);

        System.out.println(set.contains(device));

    }
}


class Device {
    private int port;
    private String ipAddress;

    public Device(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return port == device.port && Objects.equals(ipAddress, device.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, ipAddress);
    }
}