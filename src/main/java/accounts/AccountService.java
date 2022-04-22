package accounts;

import dbservice.DBService;
import dbservice.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
//    private final Map<String, UserProfile> loginToUserProfile;
    private final Map<String, UsersDataSet> httpSessionToUserProfile;
    private final DBService dbService;

    public AccountService(DBService dbService) {
//        loginToUserProfile = new HashMap<>();
        this.dbService = dbService;
        httpSessionToUserProfile = new HashMap<>();
    }

    public void addNewUser(UsersDataSet user) {
//        loginToUserProfile.put(user.getLogin(), user);
        dbService.addUser(user.getName());
    }

    public UsersDataSet getUserByLogin(String login) {
//        return loginToUserProfile.get(login);
        return dbService.getUserByLogin(login);
    }

    public void addNewSession(String sessionId, UsersDataSet user) {
        httpSessionToUserProfile.put(sessionId, user);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return httpSessionToUserProfile.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        httpSessionToUserProfile.remove(sessionId);
    }
}
