package book.project.First.entity.projection;

public interface UserProjection {
    String getUsername();
    String getPasswordHash();
    boolean getIsAdmin();
}