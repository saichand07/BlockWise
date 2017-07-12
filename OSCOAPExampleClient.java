package org.eclipse.californium.examples;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.OSCoapClient;
import org.eclipse.californium.core.network.stack.objectsecurity.CryptoContext;
import org.eclipse.californium.core.network.stack.objectsecurity.CryptoContextDB;
import org.eclipse.californium.core.network.stack.objectsecurity.HashMapCryptoContextDB;
import org.eclipse.californium.core.network.stack.objectsecurity.osexcepitons.OSTIDException;

public class OSCOAPExampleClient {
	public static CryptoContextDB clientDBA;
	public static BigInteger cid_bi;

	public static void main(String[] args) {

		clientDBA = new HashMapCryptoContextDB();
		byte[] saltClient = { 0x47, 0x47, 0x47, 0x47, 0x47, 0x47, 0x47 };
		byte[] keyClient = { 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41,
				0x41 };
		cid_bi = new BigInteger("2");
		byte[] cidA = cid_bi.toByteArray();
		byte[] saltServer = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02 };
		byte[] keyServer = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x02 };
		CryptoContext clientContextA = new CryptoContext(cid_bi, saltClient, saltServer, keyClient, keyServer);
		try {
			clientDBA.addContext(cidA, "coap://localhost/", clientContextA);
		} catch (OSTIDException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String uri = "coap://127.0.0.1/helloNumber";
		OSCoapClient client = new OSCoapClient(uri, clientDBA);
		CoapObserveRelation relation = client.observe(new CoapHandler() {

			@Override
			public void onLoad(CoapResponse response) {
				
				System.out.println("response:"+response.getResponseText());

			}

			@Override
			public void onError() {
				System.out.println("Failed");

			}
		});
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		} catch (IOException e) {
		}
		relation.proactiveCancel();

	}
}
