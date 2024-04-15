package book.project.First.service.auth;

import book.project.First.annotation.Auth;
import book.project.First.dto.user.UserCredentials;

public interface AuthService {
    void signIn(UserCredentials userCredentials);
    void verifyAuthority(Auth auth);
}
