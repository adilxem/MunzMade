package com.adil.MunzMade.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{

    @Id
    private String id;

    @NotNull
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @Getter(value = AccessLevel.NONE)
    private String password;

    @Column(length = 10000)
    private String profilePic;
    private String phoneNumber;

    @Getter(value = AccessLevel.NONE)
    private boolean enabled = true;

    private boolean emailVerified = false;
    private boolean phoneVerified = false;


    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Customer> customers = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return roles;
    }

    @Override
    public boolean isEnabled() {

        return this.enabled;
    }



    @Override
    public String getUsername() {

        return this.email;
    }


    @Override
    public String getPassword() {

        return this.password;
    }
}
