package br.com.VomHive.VomHive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "TBL_COMPANY")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompany;

    @Size(min = 4, max = 255, message = "O nome da nova comapanhia deve ter, ao menos, 4 caracteres e, no máximo, 255")
    private String nmCompany;

    private String cnpj;

    private String email;

    //Relacionamento profiles
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_profile")
    private Profile profiles;

    @PastOrPresent
    private Date dtRegister;

    public Company() {
    }

    public Company(Long idCompany, String nmCompany, String cnpj, String email, Profile profiles, Date dtRegister) {
        this.idCompany = idCompany;
        this.nmCompany = nmCompany;
        this.cnpj = cnpj;
        this.email = email;
        this.profiles = profiles;
        this.dtRegister = dtRegister;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getNmCompany() {
        return nmCompany;
    }

    public void setNmCompany(String nmCompany) {
        this.nmCompany = nmCompany;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfiles() {
        return profiles;
    }

    public void setProfiles(Profile profiles) {
        this.profiles = profiles;
    }

    public Date getDtRegister() {
        return dtRegister;
    }

    public void setDtRegister(Date dtRegister) {
        this.dtRegister = dtRegister;
    }
}
