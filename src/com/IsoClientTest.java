package com;

import java.io.IOException;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.iso.packager.XMLPackager;

public class IsoClientTest {

    public static void main(String[] args) throws ISOException, IOException {
        ISOPackager pgk = new ISO87APackager();
	ServerChannel channel = new ASCIIChannel("127.0.0.1", 10012,pgk);
        channel.connect();
        ISOMsg request = new ISOMsg();

        request.setMTI("0200");
        request.set(2, "16");
        request.set(2, "5421287475388412");
        request.set(3, "000000");
        request.set(4, "400.0");
        request.set(7, "0716070815");
        request.set(11, "844515");

        channel.send(request);

        ISOMsg response = channel.receive();

        response.dump(System.out, "response:");

    }

}