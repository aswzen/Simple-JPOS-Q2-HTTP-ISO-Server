/**
 * Simple ISO8583 Client Server with Q2 
 *
 * Jan 5, 2018 9:12:44 AM
 *
 * (C)opyright 2018 by Agus Sigit Wisnubroto (aswzen@gmail.com).
 * Only for basic understanding. All rights reserved.
 *
 */

package com;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOFilter.VetoException;
import org.jpos.q2.QBeanSupport;

public class IsoServerQ2 extends QBeanSupport implements ISORequestListener {

	public boolean process(ISOSource ISOSou, ISOMsg ISOMes) {
		try {        
			if (ISOMes.getMTI().equalsIgnoreCase("1800")) {
				ProcNetworkManagement(ISOSou, ISOMes);
			}
			if (ISOMes.getMTI().equalsIgnoreCase("0200")) {
				ProcTransaction(ISOSou, ISOMes);
			}
			ISOMes.dump(System.out, "");
			
		} catch (IOException ex) {
			Logger.getLogger(IsoServer.class.getName()).log(Level.ERROR, null, ex);
		} catch (VetoException ex) {
			Logger.getLogger(IsoServer.class.getName()).log(Level.ERROR, null, ex);
		} catch (ISOException ex) {
			Logger.getLogger(IsoServer.class.getName()).log(Level.ERROR, null, ex);
		}

		return false;
	}

	private void ProcNetworkManagement(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
		System.out.println("MOD      : ECHO TEST ");

		ISOMsg reply = (ISOMsg) isoMsg.clone();
		reply.setMTI("1810");
		isoSrc.send(reply);
	}

	private void ProcTransaction(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
		System.out.println("MOD      : TRANSACTION REQUEST ");
		
		ISOMsg reply = (ISOMsg) isoMsg.clone();
		reply.setMTI("0210");
		reply.set(39, "00"); // respon berhasil
		reply.set(88, "9850000");
		isoSrc.send(reply);
	}

}
