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
			UserRepresentation user = new UserRepresentation();
			if (needsToBeCreatedUser.equals("globaladmin")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("masterdata-220005")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("auth")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("nocert")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("device")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("ftm")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("ftmnocert")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("policyadmin")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("policies")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else if (needsToBeCreatedUser.equals("deactivate")) {
				moduleSpecificUser = needsToBeCreatedUser;
			} else {
				moduleSpecificUser = BaseTestCaseFunc.currentModule + "-" + needsToBeCreatedUser;
			}
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
		Keycloak keycloakInstance = getKeycloakInstance();
		for (String needsToBeRemovedUser : needsToBeRemovedUsers) {
			String moduleSpecificUserToBeRemoved = BaseTestCaseFunc.currentModule + "-" + needsToBeRemovedUser;
			RealmResource realmResource = keycloakInstance.realm(ConfigManager.getIAMRealmId());
			UsersResource usersRessource = realmResource.users();
			List<UserRepresentation> usersFromDB = usersRessource.search(moduleSpecificUserToBeRemoved);
			if (!usersFromDB.isEmpty()) {
				UserResource userResource = usersRessource.get(usersFromDB.get(0).getId());
				userResource.remove();
				logger.info("User removed with name: %s%n" + moduleSpecificUserToBeRemoved);
			} else {
				logger.info("User not found with name: %s%n" + moduleSpecificUserToBeRemoved);
			}
		}
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
		UserResource user = findUserResource(realm, username);
		if (user == null) {
			logger.info("User not found: " + username);
			return;
		}
		RoleRepresentation role = realm.roles().get(roleName).toRepresentation();
		user.roles().realmLevel().add(List.of(role));
	}

	/**
	 * Creates the Keycloak user if missing, otherwise resets password and ensures roles.
	 * Returns true when user is usable for UI login.
	 */
	public static boolean ensureUserExists(String username, String password, String... roleNames) {
		try {
			Keycloak keycloak = getKeycloakInstance();
			RealmResource realm = keycloak.realm(ConfigManager.getIAMRealmId());
			UsersResource usersResource = realm.users();
			UserResource userResource = findUserResource(realm, username);

			if (userResource == null) {
				UserRepresentation user = new UserRepresentation();
				user.setEnabled(true);
				user.setUsername(username);
				user.setFirstName(username);
				user.setLastName(username);
				user.setEmail("automation-" + username + "@automationlabs.com");
				try (Response response = usersResource.create(user)) {
					logger.info("Create user response for {}: {} {}", username, response.getStatus(),
							response.getStatusInfo());
					if (response.getStatus() != 201 && response.getStatus() != 204) {
						logger.error("Unable to create Keycloak user: " + username);
						return false;
					}
					String userId = CreatedResponseUtil.getCreatedId(response);
					userResource = usersResource.get(userId);
					logger.info("Created Keycloak user: " + username);
				}
			} else {
				logger.info("Keycloak user already exists: " + username);
			}

			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(password);
			userResource.resetPassword(passwordCred);

			if (roleNames != null) {
				for (String roleName : roleNames) {
					try {
						RoleRepresentation role = realm.roles().get(roleName).toRepresentation();
						userResource.roles().realmLevel().add(List.of(role));
					} catch (Exception roleEx) {
						logger.warn("Unable to assign role {} to {}: {}", roleName, username, roleEx.getMessage());
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("ensureUserExists failed for {}: {}", username, e.getMessage(), e);
			return false;
		}
	}

	private static UserResource findUserResource(RealmResource realm, String username) {
		UsersResource usersResource = realm.users();
		List<UserRepresentation> users = usersResource.search(username, true);
		if (users == null || users.isEmpty()) {
			users = usersResource.search(username);
		}
		if (users == null || users.isEmpty()) {
			return null;
		}
		for (UserRepresentation representation : users) {
			if (username.equalsIgnoreCase(representation.getUsername())) {
				return usersResource.get(representation.getId());
			}
		}
		return usersResource.get(users.get(0).getId());
	}

	public static void removeUser(String username) {
		Keycloak keycloakInstance = getKeycloakInstance();
		RealmResource realmResource = keycloakInstance.realm(ConfigManager.getIAMRealmId());
		UsersResource usersRessource = realmResource.users();
		List<UserRepresentation> usersFromDB = usersRessource.search(username, true);
		if (usersFromDB == null || usersFromDB.isEmpty()) {
			usersFromDB = usersRessource.search(username);
		}
		if (usersFromDB != null && !usersFromDB.isEmpty()) {
			for (UserRepresentation userRepresentation : usersFromDB) {
				if (username.equalsIgnoreCase(userRepresentation.getUsername())) {
					usersRessource.get(userRepresentation.getId()).remove();
					logger.info("User removed with name: " + username);
					return;
				}
			}
			usersRessource.get(usersFromDB.get(0).getId()).remove();
			logger.info("User removed with name: " + usersFromDB.get(0).getUsername());
		} else {
			logger.info("User not found with name: " + username);
		}
	}

	public static void removeManualAdjudicationPartnerTestUsers() {
		String[] users = { "pmpui-ma", "pmpui-ma2", "ma_autouser", "pmpui-ma4", "mapart01" };
		for (String user : users) {
			try {
				removeUser(user);
			} catch (Exception e) {
				logger.warn("Unable to remove MA test user " + user + ": " + e.getMessage());
			}
		}
	}
}