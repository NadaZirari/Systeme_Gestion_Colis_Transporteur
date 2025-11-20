package entity;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private Role role;
    private Boolean active = true;
    
    // Champs spécifiques au transporteur (null si ADMIN)
    private StatutTransporteur statut;
    private Specialite specialite;
}