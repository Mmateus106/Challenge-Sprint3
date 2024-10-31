package br.com.VomHive.VomHive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "TBL_PROFILE")
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Size(min = 4, max = 100, message = "O nome de usuário deve ter no mínimo 4 letras, e no máximo 100")
    private String nmUser;

    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
    @NotNull(message = "A senha não pode ser nula")
    @NotEmpty(message = "A senha não pode estar vazia")
    private String passUser;

    private String permission_status;

    private Status status;

    @PastOrPresent
    private LocalDate dtRegister;

    // Construtor padrão é necessário para JPA
    public Profile() {
    }

    // Construtor com parâmetros
    public Profile(Long idUser, String nmUser, String passUser, String permission_status, Status status, LocalDate dtRegister) {
        this.idUser = idUser;
        this.nmUser = nmUser;
        this.passUser = passUser;
        this.permission_status = permission_status;
        this.status = status;
        this.dtRegister = dtRegister;
    }
}
