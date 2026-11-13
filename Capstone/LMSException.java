//Exceptions that are thrown for the entire project
public class LMSException extends Exception{

    public LMSException(String s) {super(s);}

    // If string length is not exactly 11 (and does not contain dash in specific indices?) throw this exception .
    public static class InvalidIdFormatException extends LMSException {
        public InvalidIdFormatException() {
            super("ID format is wrong, should be: ##-####-###");
        }
    }

    // Use 'isalpha' && '-' inside a conditional statement. If conditions are not met, throw this exception.
    public static class InvalidNameFormatException extends LMSException {
        public InvalidNameFormatException() {
            super("Name must not contain special characters or numbers (other than '-').");
        }
    }

    // If the profile does not exist within the list when using "LogIn", throw this exception.
    public static class ProfileAlreadyExistsException extends LMSException {
        public ProfileAlreadyExistsException() {
            super("Profile already exists.");
        }
    }

    // If the profile does exist within the list when using "CreateNew", throw this exception.
    public static class ProfileDNEException extends LMSException {
        public ProfileDNEException() {
            super("Profile does not exist.");
        }
    }

    // If the credentials of the user do not match but does exist, throw this exception.
    public static class CredentialsMismatchException extends LMSException {
        public CredentialsMismatchException() {
            super("Username, ID, or Password do not match.");
        }
    }
}
