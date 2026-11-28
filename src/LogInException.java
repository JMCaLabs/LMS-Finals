//Exceptions that are thrown from the Log In UI
public abstract class LogInException extends Exception{

    public LogInException(String s) {super(s);}

    // If any field is left empty when using "CreateNew" or "LogIn", throw this exception.
    public static class EmptyFieldsException extends LogInException {
        public EmptyFieldsException() {
            super("Please fill in all fields.");
        }
    }

    // If string length is not exactly 11 (and does not contain dash in specific indices?) throw this exception .
    public static class InvalidIdFormatException extends LogInException {
        public InvalidIdFormatException() {
            super("ID format is invalid, should be: ##-####-###");
        }
    }

    // Use 'isalpha' && '-' inside a conditional statement. If conditions are not met, throw this exception.
    public static class InvalidNameFormatException extends LogInException {
        public InvalidNameFormatException() {
            super("Name must not contain special characters or numbers (other than '-').");
        }
    }

    // If the profile does exist within the list when using "LogIn", throw this exception.
    public static class UserAlreadyExistsException extends LogInException {
        public UserAlreadyExistsException() {
            super("User already exists.");
        }
    }

    // If the profile does not exist within the list when using "CreateNew", throw this exception.
    public static class UserDNEException extends LogInException {
        public UserDNEException() {
            super("User does not exist.");
        }
    }

    // If the credentials of the user do not match but does exist, throw this exception.
    public static class CredentialsMismatchException extends LogInException {
        public CredentialsMismatchException() {
            super("ID or Password do not match.");
        }
    }

    // If "Password" and "Confirm Password" are different when using "CreateNew", throw this exception.
    public static class PasswordsDoNotMatchException extends LogInException {
        public PasswordsDoNotMatchException() {
            super("Passwords do not match.");
        }
    }

    // If neither Student nor Teacher is selected when using "CreateNew", throw this exception.
    public static class UserTypeNotSelectedException extends LogInException {
        public UserTypeNotSelectedException() {
            super("Please select Student or Teacher.");
        }
    }
}
