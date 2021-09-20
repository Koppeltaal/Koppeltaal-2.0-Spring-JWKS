/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright Headease B.V. (c) 2020.
 */

package nl.koppeltaal.springbootstarterjwks.controller;

import nl.koppeltaal.springbootstarterjwks.config.JwksConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * For those lazy girls and boys that just want the plain old public key as PEM or TXT.
 */
@Controller
public class PublicKeyController {
	private final JwksConfiguration jwksConfiguration;

	public PublicKeyController(JwksConfiguration jwksConfiguration) {
		this.jwksConfiguration = jwksConfiguration;
	}

	@RequestMapping(value = "/public_key.txt", produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody
	String txt() {
		return jwksConfiguration.getSigningPublicKey();
	}

	@RequestMapping(value = "/public_key.pem", produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody
	String pem() {
		return "-----BEGIN PUBLIC KEY-----\n"+ jwksConfiguration.getSigningPublicKey()+"\n-----END PUBLIC KEY-----";
	}

}
