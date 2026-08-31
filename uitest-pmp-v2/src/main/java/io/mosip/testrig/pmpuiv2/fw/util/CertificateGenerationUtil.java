package io.mosip.testrig.pmpuiv2.fw.util;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateGenerationUtil {

	private static final Logger logger = LoggerFactory.getLogger(CertificateGenerationUtil.class);
	private static final BouncyCastleProvider BC_PROVIDER = new BouncyCastleProvider();
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	private static final String CERT_FOLDER = "pmp_uiv2_cert";

	private CertificateGenerationUtil() {
	}

	private static final class GeneratedCert {
		private final KeyPair keyPair;
		private final X500Name subjectName;
		private final X509Certificate certificate;

		private GeneratedCert(KeyPair keyPair, X500Name subjectName, X509Certificate certificate) {
			this.keyPair = keyPair;
			this.subjectName = subjectName;
			this.certificate = certificate;
		}
	}

	public static void generateAllCertificates() {
		Instant now = Instant.now();

		// Auth / Device / FTM / PartnerAdmin share one Root -> Intermediate -> Leaf chain
		generateChain(rootName("CA"), intermediateName("SUBCA"), leafName("AABBCC"), "RootCA.cer",
				"IntermediateCA.cer", "Client.cer", now);

		// MISP domain
		generateChain(rootName("CA"), intermediateName("SUBCA"), leafName("AABBCC"), "MispRootCA.cer",
				"MispIntermediateCA.cer", "MipsClient.cer", now);

		// Deactivated-user scenario
		generateChain(rootName("CA"), intermediateName("SUBCA"), leafName("AABBCC"), "deactivateUserRootCA.cer",
				"deactivateUserIntermediateCA.cer", "deactivateUserClient.cer", now);

		generateRootAndIntermediateOnly(rootName("CA"), intermediateName("SUBCA"), "deactivateFtmRootCA.cer",
				"deactivateFtmIntermediateCA.cer", now);

		// Policy user scenario
		generateChain(rootName("CA"), intermediateName("SUBCA"), leafName("AABBCC"), "policyUserRootCA.cer",
				"policyUserIntermediateCA.cer", "policyUserClient.cer", now);

		// Policy admin scenario: only Root + Intermediate get uploaded to the trust store
		generateRootAndIntermediateOnly(rootName("CA"), intermediateName("SUBCA"), "policyadminca.cer",
				"policyadminsubca.cer", now);

		// Foreign/unrelated CA used to test cross-domain certificate mismatch
		X500Name foreignDn = new X500NameBuilder(BCStyle.INSTANCE).addRDN(BCStyle.C, "IN").addRDN(BCStyle.ST, "Karantaka")
				.addRDN(BCStyle.L, "Bangalore").addRDN(BCStyle.O, "MOSIPTEST").addRDN(BCStyle.CN, "mosiptest.org")
				.build();
		generateStandaloneSelfSigned(foreignDn, Date.from(now), Date.from(now.plus(365L * 100, ChronoUnit.DAYS)),
				"FTM_ca.cer");

		// Deliberately expired Root CA for negative validity tests
		X500Name expiredDn = rootName("expired");
		generateStandaloneSelfSigned(expiredDn, Date.from(now.minus(730, ChronoUnit.DAYS)),
				Date.from(now.minus(365, ChronoUnit.DAYS)), "expiredRoot.cer");

		logger.info("Generated fresh certificate chains for all PMP UI partner scenarios");
	}

	private static X500Name rootName(String label) {
		return new X500NameBuilder(BCStyle.INSTANCE).addRDN(BCStyle.C, "aa").addRDN(BCStyle.ST, "aa")
				.addRDN(BCStyle.L, "aa").addRDN(BCStyle.O, label).addRDN(BCStyle.OU, label).addRDN(BCStyle.CN, label)
				.build();
	}

	private static X500Name intermediateName(String label) {
		return rootName(label);
	}

	private static X500Name leafName(String label) {
		return rootName(label);
	}

	private static void generateChain(X500Name rootDn, X500Name interDn, X500Name leafDn, String rootFile,
			String interFile, String leafFile, Instant now) {
		Date rootNotBefore = Date.from(now);
		Date rootNotAfter = Date.from(now.plus(365L * 5, ChronoUnit.DAYS));
		Date childNotBefore = Date.from(now);
		Date childNotAfter = Date.from(now.plus(365L * 3, ChronoUnit.DAYS));

		GeneratedCert root = generateSelfSignedCa(rootDn, rootNotBefore, rootNotAfter);
		writePem(root.certificate, rootFile);

		GeneratedCert intermediate = generateSignedCert(interDn, root, childNotBefore, childNotAfter, true);
		writePem(intermediate.certificate, interFile);

		GeneratedCert leaf = generateSignedCert(leafDn, intermediate, childNotBefore, childNotAfter, false);
		writePem(leaf.certificate, leafFile);
	}

	private static void generateRootAndIntermediateOnly(X500Name rootDn, X500Name interDn, String rootFile,
			String interFile, Instant now) {
		Date rootNotBefore = Date.from(now);
		Date rootNotAfter = Date.from(now.plus(365L * 5, ChronoUnit.DAYS));
		Date childNotBefore = Date.from(now);
		Date childNotAfter = Date.from(now.plus(365L * 3, ChronoUnit.DAYS));

		GeneratedCert root = generateSelfSignedCa(rootDn, rootNotBefore, rootNotAfter);
		writePem(root.certificate, rootFile);

		GeneratedCert intermediate = generateSignedCert(interDn, root, childNotBefore, childNotAfter, true);
		writePem(intermediate.certificate, interFile);
	}

	private static void generateStandaloneSelfSigned(X500Name dn, Date notBefore, Date notAfter, String file) {
		GeneratedCert cert = generateSelfSignedCa(dn, notBefore, notAfter);
		writePem(cert.certificate, file);
	}

	private static GeneratedCert generateSelfSignedCa(X500Name dn, Date notBefore, Date notAfter) {
		KeyPair keyPair = generateRsaKeyPair();
		X509Certificate cert = sign(dn, keyPair.getPublic(), dn, keyPair.getPrivate(), notBefore, notAfter, true);
		return new GeneratedCert(keyPair, dn, cert);
	}

	private static GeneratedCert generateSignedCert(X500Name subjectDn, GeneratedCert issuer, Date notBefore,
			Date notAfter, boolean isCa) {
		KeyPair keyPair = generateRsaKeyPair();
		X509Certificate cert = sign(subjectDn, keyPair.getPublic(), issuer.subjectName, issuer.keyPair.getPrivate(),
				notBefore, notAfter, isCa);
		return new GeneratedCert(keyPair, subjectDn, cert);
	}

	private static KeyPair generateRsaKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Failed to generate RSA key pair", e);
		}
	}

	private static X509Certificate sign(X500Name subject, PublicKey subjectPublicKey, X500Name issuer,
			PrivateKey issuerPrivateKey, Date notBefore, Date notAfter, boolean isCa) {
		try {
			BigInteger serial = new BigInteger(64, new SecureRandom());
			JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(issuer, serial, notBefore,
					notAfter, subject, subjectPublicKey);

			certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(isCa));
			if (isCa) {
				certBuilder.addExtension(Extension.keyUsage, true,
						new KeyUsage(KeyUsage.keyCertSign | KeyUsage.cRLSign));
			} else {
				certBuilder.addExtension(Extension.keyUsage, true,
						new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
			}

			ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).setProvider(BC_PROVIDER)
					.build(issuerPrivateKey);

			X509CertificateHolder holder = certBuilder.build(signer);
			return new JcaX509CertificateConverter().setProvider(BC_PROVIDER).getCertificate(holder);
		} catch (OperatorCreationException | CertificateException | CertIOException e) {
			throw new IllegalStateException("Failed to generate certificate for subject " + subject, e);
		}
	}

	private static void writePem(X509Certificate certificate, String fileName) {
		String path = PmpTestUtil.getResourceFilePath(CERT_FOLDER, fileName);
		try (PemWriter pemWriter = new PemWriter(new FileWriter(path))) {
			pemWriter.writeObject(new PemObject("CERTIFICATE", certificate.getEncoded()));
		} catch (IOException | CertificateEncodingException e) {
			throw new IllegalStateException("Failed to write certificate file: " + path, e);
		}
		logger.info("Generated certificate: {}", path);
	}
}
