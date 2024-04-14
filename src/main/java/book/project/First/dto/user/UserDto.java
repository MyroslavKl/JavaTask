package book.project.First.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private boolean isAdmin;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private String info;
}