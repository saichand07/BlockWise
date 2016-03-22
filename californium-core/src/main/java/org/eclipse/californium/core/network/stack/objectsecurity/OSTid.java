package org.eclipse.californium.core.network.stack.objectsecurity;

import COSE.AlgorithmID;
import com.upokecenter.cbor.CBORObject;

import java.math.BigInteger;

/**
 * Created by joakim on 2016-02-23.
 */
public class OSTid {


    //TODO, BigInteger encodes to 2-complement in .toByteArray(), should not be a problem, but test it
    private BigInteger cid;  //8 bytes but java lacks support for 8 bit unsigned values
    private BigInteger senderSeq;    //1-8 bytes, contains the next unused value
    private BigInteger receiverSeq;    //1-8 bytes, contains the next unused value
    private BigInteger senderSalt;
    private BigInteger receiverSalt;
    private int replayProtectionWin = 0;
    private byte[] keySender = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
    private byte[] keyReceiver= {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};

    public OSTid(BigInteger cid){
        this.cid = cid;
        //this.senderSeq = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        //this.receiverSeq = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        this.senderSeq = BigInteger.ZERO;
        this.receiverSeq = BigInteger.ZERO;
    }

    public byte[] getSenderKey(){
        return keySender;
    }

    public byte[] getReceiverKey(){
        return keyReceiver;
    }

    public CBORObject getAlg() {
        return AlgorithmID.AES_CCM_16_64_128.AsCBOR();
    }

    public byte[] getCid(){
        return cid.toByteArray();
    }

    public byte[] getSenderSeq(){
        byte[] array = senderSeq.toByteArray();
        /*
        //remove leading zeroes
        int numLeadingZeroes = 0;
        while (numLeadingZeroes < array.length && array[numLeadingZeroes] != 0){
            numLeadingZeroes++;
        }
        byte[] newArr = new byte[array.length - numLeadingZeroes];
        System.arraycopy(array, numLeadingZeroes, newArr, 0, newArr.length);

        return newArr;
        */ return array;
    }

    public void increaseSenderSeq(){
        //senderSeq = senderSeq.add(BigInteger.ONE);
    }

    public void increaseReceiverSeq(){
        //gitreceiverSeq = receiverSeq.add(BigInteger.ONE);
    }

    public byte[] getReceiverSeq(){
        byte[] array = receiverSeq.toByteArray();
/*
        //remove leading zeroes
        int numLeadingZeroes = 0;
        while (numLeadingZeroes < array.length && array[numLeadingZeroes] != 0){
            numLeadingZeroes++;
        }
        byte[] newArr = new byte[array.length - numLeadingZeroes];
        System.arraycopy(array, numLeadingZeroes, newArr, 0, newArr.length);

        return newArr;
        */return array;
    }

    public BigInteger getSenderSalt() {
        return senderSalt;
    }

    public BigInteger getReceiverSalt() {
        return receiverSalt;
    }

    @Override
    public boolean equals(Object o){
        if (!( o instanceof OSTid)) return false;
        OSTid other = (OSTid)o;
        return other.cid.equals(this.cid);
    }

}
