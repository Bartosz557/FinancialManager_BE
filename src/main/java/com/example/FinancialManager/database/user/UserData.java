package com.example.FinancialManager.database.user;

import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.accountDetails.LimitDetails;
import com.example.FinancialManager.database.transactions.RecurringExpenses;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class UserData implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long userID;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean configured = false;
    private Boolean enabled = true;
    @OneToOne(mappedBy = "userDataAD", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AccountDetails accountDetails;
    @OneToMany(mappedBy = "userDataRE", cascade = CascadeType.REMOVE)
    private List<RecurringExpenses> recurringExpensesList;
    @OneToMany(mappedBy = "userDataLD", cascade = CascadeType.REMOVE)
    private List<LimitDetails> limitDetails;


    public UserData(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}