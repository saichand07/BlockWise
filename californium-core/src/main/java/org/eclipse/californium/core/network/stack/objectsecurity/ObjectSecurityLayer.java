package org.eclipse.californium.core.network.stack.objectsecurity;

import org.eclipse.californium.core.coap.*;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.network.stack.AbstractLayer;

/**
 * Created by joakim on 04/02/16.
 */
public class ObjectSecurityLayer extends AbstractLayer {

    @Override
    public void sendRequest(Exchange exchange, Request request) {
       OptionSet options = request.getOptions();

        int algId = 1;
        int key = 1;
        OSCID cid = new OSCID(key, algId);
        ObjectSecurityOption op = new ObjectSecurityOption(cid,request);
        options.addOption(op);
        System.out.println("Bytes: " );
        //byte[] serialized2 = op.getRequestMac0AuthenticatedData(request);
        //System.out.println(bytesToHex(serialized2));
        super.sendRequest(exchange, request);
    }
    @Override
    public void sendResponse(Exchange exchange, Response response) {
        super.sendResponse(exchange,response);
    }

    @Override
    public void sendEmptyMessage(Exchange exchange, EmptyMessage message) {
        super.sendEmptyMessage(exchange,message);
    }

    @Override
    public void receiveRequest(Exchange exchange, Request request) {
        OptionSet options = request.getOptions();

        if (options.hasOption(OptionNumberRegistry.OBJECT_SECURITY)) {
            ObjectSecurityOption osOpt;
            System.out.println("Incoming OSOption!");
            for (Option o : options.asSortedList()) {

                osOpt =null; //h'mta ut och verifiera
            }
        }
        super.receiveRequest(exchange, request);
    }

    @Override
    public void receiveResponse(Exchange exchange, Response response) {
        super.receiveResponse(exchange,response);
    }

    @Override
    public void receiveEmptyMessage(Exchange exchange, EmptyMessage message) {
        super.receiveEmptyMessage(exchange, message);
    }

    //TODO remove development method:
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }




}
