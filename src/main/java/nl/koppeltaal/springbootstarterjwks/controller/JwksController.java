/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright Headease B.V. (c) 2020.
 */

package nl.koppeltaal.springbootstarterjwks.controller;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import nl.koppeltaal.springbootstarterjwks.config.JwksConfiguration;
import nl.koppeltaal.springbootstarterjwks.util.KeyUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.lang.JoseException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(".well-known/jwks.json")
@ConditionalOnClass(JwksConfiguration.class)
public class JwksController {

	private final JwksConfiguration jwksConfiguration;

	public JwksController(JwksConfiguration jwksConfiguration) {
		this.jwksConfiguration = jwksConfiguration;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String get() throws InvalidKeySpecException, NoSuchAlgorithmException, JoseException {

		KeyPair rsaKeyPair = KeyUtils.getRsaKeyPair(jwksConfiguration.getSigningPublicKey(), jwksConfiguration.getSigningPrivateKey());

		JsonWebKey jsonWebKey = JsonWebKey.Factory.newJwk(rsaKeyPair.getPublic());
		jsonWebKey.setUse("sig");
		jsonWebKey.setKeyId(jsonWebKey.calculateBase64urlEncodedThumbprint("MD5"));
		JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKey);

		return jsonWebKeySet.toJson();
	}
}
