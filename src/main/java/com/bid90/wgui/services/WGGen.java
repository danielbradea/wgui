package com.bid90.wgui.services;

import com.bid90.wgui.models.Client;
import com.bid90.wgui.models.Server;
import com.bid90.wgui.models.Settings;
import com.bid90.wgui.utils.IPUtil;

import java.util.List;


/**
 * Utility class for generating WireGuard configuration.
 */
public class WGGen {

    /**
     * Generates the WireGuard server configuration based on the provided server and client list.
     *
     * @param server     The server object containing server configuration.
     * @param clientList The list of clients to include in the configuration.
     * @return The generated WireGuard server configuration as a string.
     */
    public static String wgServer(Server server, List<Client> clientList) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("[Interface]\n");
        stringBuilder.append(lineKeyValue("Address", server.getAddress()));
        stringBuilder.append(lineKeyValue("ListenPort", server.getListenPort().toString()));
        stringBuilder.append(lineKeyValue("PrivateKey", server.getKeyPair().getPrivateKey()));
        if (check(server.getPostUp())) {
            stringBuilder.append(lineKeyValue("PostUp", server.getPostUp()));
        }
        if (check(server.getPostDown())) {
            stringBuilder.append(lineKeyValue("PostDown", server.getPostUp()));
        }
        if (check(server.getPreUp())) {
            stringBuilder.append(lineKeyValue("PreUp", server.getPreUp()));
        }
        if (check(server.getPreDown())) {
            stringBuilder.append(lineKeyValue("PreDown", server.getPreDown()));
        }
        stringBuilder.append("\n\n");
        clientList.stream().filter(Client::getEnabled)
                .forEach(client -> {
                    stringBuilder.append("[Peer]\n");
                    stringBuilder.append(lineKeyValueComment("ClientId", client.getId().toString()));
                    stringBuilder.append(lineKeyValueComment("ClientName", client.getName()));
                    stringBuilder.append(lineKeyValueComment("UserId", client.getUser().getId().toString()));
                    stringBuilder.append(lineKeyValueComment("CreatedAt", client.getCreatedAt().toString()));
                    stringBuilder.append(lineKeyValueComment("UpdatedAt", client.getUpdatedAt().toString()));
                    stringBuilder.append(lineKeyValue("PublicKey", client.getKeyPair().getPublicKey()));
                    stringBuilder.append(lineKeyValue("PresharedKey", client.getKeyPair().getPresharedKey()));
                    stringBuilder.append(lineKeyValue("AllowedIPs", client.getAddress()));
                    stringBuilder.append("\n\n");
                });

        return stringBuilder.toString();

    }

    /**
     * Generates the WireGuard client configuration based on the provided server, client, and settings.
     *
     * @param server   The server object containing server configuration.
     * @param client   The client object containing client configuration.
     * @param settings The settings object containing additional configuration settings.
     * @return The generated WireGuard client configuration as a string.
     */
    public static String wgClient(Server server, Client client, Settings settings) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("[Interface]\n");
        stringBuilder.append(lineKeyValue("Address", IPUtil.removeCIDR(client.getAddress())));
        stringBuilder.append(lineKeyValue("ListenPort", server.getListenPort().toString()));
        stringBuilder.append(lineKeyValue("PrivateKey", client.getKeyPair().getPrivateKey()));
        stringBuilder.append("\n");

        stringBuilder.append("[Peer]\n");
        stringBuilder.append(lineKeyValue("PublicKey", server.getKeyPair().getPublicKey()));
        stringBuilder.append(lineKeyValue("PresharedKey", client.getKeyPair().getPresharedKey()));
        stringBuilder.append(lineKeyValue("AllowedIPs", client.getAllowedIPs()));
        stringBuilder.append(lineKeyValue("Endpoint", settings.getEndpointAddress() + ":" + server.getListenPort()));

        return stringBuilder.toString();
    }

    private static String lineKeyValue(String key, String value) {
        return key + " = " + value + "\n";
    }

    private static String lineKeyValueComment(String key, String value) {
        return "#" + key + ":" + value + "\n";
    }

    private static boolean check(String s) {
        return s != null && !s.isEmpty() && !s.isBlank();
    }
}
