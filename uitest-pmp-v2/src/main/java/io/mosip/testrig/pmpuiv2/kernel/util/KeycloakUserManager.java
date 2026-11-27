package io.mosip.testrig.pmpuiv2.kernel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.BaseTestCaseFunc;
import io.mosip.testrig.pmpuiv2.utility.TestRunner;

public class KeycloakUserManager extends BaseTestCaseFunc {
	public static String moduleSpecificUser = null;
	public static String passwordCred;
	public static String publicKeytemp = PmpTestUtil.generateJWKPublicKey();

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KeycloakUserManager.class);

	public static Properties propsKernel = getproperty(
			TestRunner.getResourcePath() + "/" + "config/" + TestRunner.GetKernalFilename());

	private static Keycloak getKeycloakInstance() {
		Keycloak key = null;
		try {
			key = KeycloakBuilder.builder().serverUrl(ConfigManager.getIAMUrl()).realm(ConfigManager.getIAMRealmId())
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS).clientId(ConfigManager.getAutomationClientId())
					.clientSecret(ConfigManager.getAutomationClientSecret()).build();
			logger.info("ConfigManager.getIAMUrl()" + ConfigManager.getIAMUrl());
			logger.info(key.toString() + key.realms());
		} catch (Exception e) {
			throw e;
		}
		return key;
	}

	public static Properties getproperty(String path) {
		Properties prop = new Properties();
		try {
			File file = new File(path);
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			logger.error("Exception " + e.getMessage());
		}
		return prop;
	}

	public static void createUsers() {
		List<String> needsToBeCreatedUsers = List.of(ConfigManager.getIAMUsersToCreate().split(","));
		Keycloak keycloakInstance = getKeycloakInstance();
		for (String needsToBeCreatedUser : needsToBeCreatedUsers) {
			if (registeredThroughSignUp(needsToBeCreatedUser)) {
				continue;
			}
			UserRepresentation user = new UserRepresentation();
			moduleSpecificUser = keycloakUsername(needsToBeCreatedUser);
			logger.info(moduleSpecificUser);
			user.setEnabled(true);
			user.setUsername(moduleSpecificUser);
			user.setFirstName(moduleSpecificUser);
			user.setLastName(moduleSpecificUser);
			user.setEmail("automation" + moduleSpecificUser + BaseClass.data + "@automationlabs.com");
			// Get realm
			RealmResource realmResource = keycloakInstance.realm(ConfigManager.getIAMRealmId());
			UsersResource usersRessource = realmResource.users();
			// Create user (requires manage-users role)
			Response response = null;
			response = usersRessource.create(user);
			logger.info("Repsonse: %s %s%n" + response.getStatus() + response.getStatusInfo());
			if (response.getStatus() == 409) {
				continue;
			}
			String userId = CreatedResponseUtil.getCreatedId(response);
			logger.info("User created with userId: %s%n" + userId);
			// Define password credential
			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			// passwordCred.setValue(userPassword.get(passwordIndex));
			passwordCred.setValue("mosip123");
			UserResource userResource = usersRessource.get(userId);
			userResource.resetPassword(passwordCred);
			List<RoleRepresentation> allRoles = realmResource.roles().list();
			List<RoleRepresentation> availableRoles = new ArrayList<>();
			List<String> toBeAssignedRoles = List.of(ConfigManager.getRolesForUser(needsToBeCreatedUser).split(","));
			for (String role : toBeAssignedRoles) {
				if (allRoles.stream().anyMatch((r -> r.getName().equalsIgnoreCase(role)))) {
					availableRoles.add(allRoles.stream().filter(r -> r.getName().equals(role)).findFirst().get());
				} else {
					logger.info("Role not found in keycloak: %s%n" + role);
				}
			}
			userResource.roles().realmLevel() //
					.add((availableRoles.isEmpty() ? allRoles : availableRoles));
		}
	}

	public static void createUsers(String userid, String pwd, String rolenum, Map<String, List<String>> map) {
		Keycloak keycloakInstance = getKeycloakInstance();
		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(userid);
		user.setFirstName(userid);
		user.setLastName(userid);
		user.setEmail("automation" + moduleSpecificUser + "@automationlabs.com");
		if (map != null)
			user.setAttributes(map);
		RealmResource realmResource = null;
		realmResource = keycloakInstance.realm(ConfigManager.getIAMRealmId());
		UsersResource usersRessource = realmResource.users();
		try (Response response = usersRessource.create(user)) {
			String userId = CreatedResponseUtil.getCreatedId(response);
			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(pwd);
			UserResource userResource = usersRessource.get(userId);
			userResource.resetPassword(passwordCred);
			List<RoleRepresentation> allRoles = realmResource.roles().list();
			List<RoleRepresentation> availableRoles = new ArrayList<>();
			List<String> toBeAssignedRoles = List.of(propsKernel.getProperty(rolenum).split(","));
			for (String role : toBeAssignedRoles) {
				if (allRoles.stream().anyMatch((r -> r.getName().equalsIgnoreCase(role)))) {
					if (allRoles.stream().filter(r -> r.getName().equals(role)).findFirst().isPresent())
						availableRoles.add(allRoles.stream().filter(r -> r.getName().equals(role)).findFirst().get());
				}
				userResource.roles().realmLevel() //
						.add((availableRoles.isEmpty() ? allRoles : availableRoles));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static void removeUser() {
		List<String> needsToBeRemovedUsers = List.of(ConfigManager.getIAMUsersToCreate().split(","));
		for (String needsToBeRemovedUser : needsToBeRemovedUsers) {
			removeKeycloakUser(BaseTestCaseFunc.currentModule + "-" + needsToBeRemovedUser);
		}
	}

	private static boolean registeredThroughSignUp(String shortName) {
		return "v2".equals(shortName) || "deactivate2".equals(shortName) || "deactivatedevice".equals(shortName)
				|| "deactivateftm".equals(shortName);
	}

	private static String keycloakUsername(String shortName) {
		if (shortName.equals("globaladmin") || shortName.equals("masterdata-220005") || shortName.equals("auth")
				|| shortName.equals("nocert") || shortName.equals("device") || shortName.equals("ftm")
				|| shortName.equals("ftmnocert") || shortName.equals("policyadmin") || shortName.equals("policies")
				|| shortName.equals("deactivate")) {
			return shortName;
		}
		return BaseTestCaseFunc.currentModule + "-" + shortName;
	}

	private static void removeKeycloakUser(String username) {
		Keycloak keycloakInstance = getKeycloakInstance();
		RealmResource realmResource = keycloakInstance.realm(ConfigManager.getIAMRealmId());
		UsersResource usersRessource = realmResource.users();
		List<UserRepresentation> usersFromDB = usersRessource.search(username);
		for (UserRepresentation found : usersFromDB) {
			if (username.equals(found.getUsername())) {
				usersRessource.get(found.getId()).remove();
				logger.info("User removed with name: %s%n" + username);
				return;
			}
		}
		logger.info("User not found with name: %s%n" + username);
	}

	public static String readJsonData(String path) {
		String propsPublicKey = null;
		try {
			JSONObject obj = new JSONObject(new JSONTokener(new FileReader(path)));
			String name = obj.getString("n");
//			obj.put("n", generateRandomString(340));
			propsPublicKey = obj.toString();
			logger.info("n: " + name);
			logger.info("n: " + propsPublicKey);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propsPublicKey;
	}

	public static String generateRandomString(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	public static void assignRole(String username, String roleName) {
		Keycloak keycloak = getKeycloakInstance();
		RealmResource realm = keycloak.realm(ConfigManager.getIAMRealmId());
		List<UserRepresentation> users = realm.users().search(username);
		if (users.isEmpty()) {
			logger.info("User not found: " + username);
			return;
		}
		UserResource user = realm.users().get(users.get(0).getId());
		RoleRepresentation role = realm.roles().get(roleName).toRepresentation();
		user.roles().realmLevel().add(List.of(role));
	}
}