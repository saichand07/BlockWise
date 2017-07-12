package org.eclipse.californium.examples;

import java.math.BigInteger;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.OSCoapServer;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.network.stack.objectsecurity.CryptoContext;
import org.eclipse.californium.core.network.stack.objectsecurity.CryptoContextDB;
import org.eclipse.californium.core.network.stack.objectsecurity.HashMapCryptoContextDB;
import org.eclipse.californium.core.network.stack.objectsecurity.osexcepitons.OSTIDException;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class OSCOAPExampleServer {
	public static CryptoContextDB serverDBA;
	public static CryptoContextDB clientDBA;
	public static BigInteger cid_bi;

	public static void main(String[] args) {

		serverDBA = new HashMapCryptoContextDB();
		byte[] saltClient = { 0x47, 0x47, 0x47, 0x47, 0x47, 0x47, 0x47 };
		byte[] keyClient = { 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41,
				0x41 };
		cid_bi = new BigInteger("2");
		byte[] cidA = cid_bi.toByteArray();
		byte[] saltServer = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02 };
		byte[] keyServer = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x02 };
		CryptoContext serverContextA = new CryptoContext(cid_bi, saltServer, saltClient, keyServer, keyClient);
		try {
			serverDBA.addContext(cidA, "coap://localhost/", serverContextA);
		} catch (OSTIDException e) {
			e.printStackTrace();
			System.exit(1);
		}
		OSCoapServer server = new OSCoapServer(serverDBA, 5683);
		OSCOAPExampleServer instance = new OSCOAPExampleServer();
		server.add(instance.new RandomNumberResource());
		server.start();

	}

	class RandomNumberResource extends CoapResource {
		private int rVal = 0;

		class UpdateTask extends TimerTask {

			CoapResource rRes;

			public UpdateTask(CoapResource res) {
				this.rRes = res;
			}

			@Override
			public void run() {

				rVal = new Random().nextInt(20);
				if (rVal > 5)
					rRes.changed();
			}

		}

		public RandomNumberResource() {
			super("helloNumber");
			setObservable(true);
			setObserveType(Type.NON);
			getAttributes().setObservable();
			Timer time = new Timer();
			time.schedule(new UpdateTask(this), 0, 1000);
		}

		@Override
		public void handleGET(CoapExchange response) {
			response.respond(rVal + " ");
		}

	}
}
