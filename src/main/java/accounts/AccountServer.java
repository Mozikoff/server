package accounts;

public class AccountServer implements AccountServerI {
    private int usersCount;
    private int usersLimit;

    public AccountServer(int usersLimit) {
        this.usersLimit = usersLimit;
        this.usersCount = 0;
    }

    @Override
    public void addNewUser() { usersCount++; }

    @Override
    public void removeUser() { usersCount--; }

    @Override
    public int getUsersCount() {
        return usersCount;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
