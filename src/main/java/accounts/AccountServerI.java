package accounts;

public interface AccountServerI {
    void addNewUser();

    void removeUser();

    int getUsersCount();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);
}
