/*******************************************************************************
 * Copyright (c) 2018 Edgeworx, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Saeid Baghbidi
 * Kilton Hopkins
 *  Ashita Nagar
 *******************************************************************************/
package org.eclipse.iofog.message_bus;

import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.MessageHandler;
import org.eclipse.iofog.local_api.MessageCallback;

import static org.eclipse.iofog.utils.logging.LoggingService.logWarning;

/**
 * listener for real-time receiving
 * 
 * @author saeid
 *
 */
public class MessageListener implements MessageHandler {
	private static final String MODULE_NAME = "Message Listener";

	private final MessageCallback callback;
	
	MessageListener(MessageCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public void onMessage(ClientMessage msg) {
		try {
			msg.acknowledge();
		} catch (Exception exp) {
			logWarning(MODULE_NAME, exp.getMessage());
		}
		
		Message message = new Message(msg.getBytesProperty("message"));
		callback.sendRealtimeMessage(message);
	}

}
