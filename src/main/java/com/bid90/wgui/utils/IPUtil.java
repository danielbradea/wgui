package com.bid90.wgui.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Optional;


/**
 * Utility class for retrieving the public IP address.
 */
public class IPUtil {

    private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

    /**
     * Retrieves the public IP address by making a request to an external service.
     *
     * @return The public IP address if it is successfully retrieved, otherwise an empty Optional
     */
    public static Optional<String> getPublicIP() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String publicIP = br.readLine().trim();
            br.close();
            return Optional.of(publicIP);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Increments an IP address by a given value.
     *
     * @param ipAddress The IP address to be incremented.
     * @param increment The value by which the IP address should be incremented.
     * @return The incremented IP address.
     */
    public static String incrementIPAddress(String ipAddress, long increment) {
        String[] parts = ipAddress.split("/");
        String[] addressParts = parts[0].split("\\.");
        int lastOctet = Integer.parseInt(addressParts[3]);
        lastOctet += increment;
        addressParts[3] = String.valueOf(lastOctet);
        return String.join(".", addressParts) + "/" + parts[1];
    }


    /**
     * Finds an available IP address by incrementing the last octet of the given IP address until an unused IP address is found.
     *
     * @param ipAddress     The original IP address.
     * @param ipAddressList List of IP addresses to check against for availability.
     * @return The available IP address with the same subnet mask as the original IP address.
     * @throws NoAvailableIpAddressException if no available IP address is found within the valid range (0 to 255).
     */
    public static String findAvailableIpAddress(String ipAddress, List<String> ipAddressList) {
        String[] parts = ipAddress.split("/");
        String[] addressParts = parts[0].split("\\.");
        int lastOctet = Integer.parseInt(addressParts[3]);

        List<Integer> octetsList = ipAddressList.stream()
                .map(s -> Integer.parseInt(s.split("/")[0].split("\\.")[3]))
                .toList();
        do {
            lastOctet += 1;
            if (lastOctet > 255)
                throw new RuntimeException("No available IP address within the valid range (0 to 255)");

        } while (octetsList.contains(lastOctet));
        addressParts[3] = String.valueOf(lastOctet);
        if(parts.length > 1){
            return String.join(".", addressParts) + "/" + parts[1];
        }
        return String.join(".", addressParts) + "/" + 32;
    }

    /**
     * Removes the CIDR suffix from an IP address.
     *
     * @param ipAddress the IP address with or without CIDR notation
     * @return the IP address without the CIDR suffix
     */
    public static String removeCIDR(String ipAddress) {
        int slashIndex = ipAddress.indexOf('/');
        if (slashIndex != -1) {
            return ipAddress.substring(0, slashIndex);
        }
        return ipAddress;
    }

    public static class NoAvailableIpAddressException extends Exception {
        public NoAvailableIpAddressException(String message) {
            super(message);
        }
    }
}
