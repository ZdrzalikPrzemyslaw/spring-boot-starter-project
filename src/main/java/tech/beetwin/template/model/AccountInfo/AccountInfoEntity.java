package tech.beetwin.template.model.AccountInfo;

import tech.beetwin.template.model.AbstractEntity;
import tech.beetwin.template.model.AccessLevel.AccessLevelsEntity;
import tech.beetwin.template.model.OauthAccountInfo.OauthAccountInfoEntity;
import tech.beetwin.template.model.TableMetadata.TableMetadataEntity;
import tech.beetwin.template.model.UserInfo.UserInfoEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account_info", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
})

public class AccountInfoEntity extends AbstractEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_info_sequence")
    @SequenceGenerator(name = "account_info_sequence", sequenceName = "account_info_sequence", allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Basic
    @Column(name = "password", nullable = false,
//            columnDefinition = "bpchar",
            length = 128)
    private String password;
    @Basic
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
    // TODO: 12/11/2022 Zmienić confirmed domyślne na false po dodaniu maili
    @Basic
    @Column(name = "confirmed", nullable = false)
    private boolean confirmed = true;
    @Basic
    @Column(name = "confirmation_token", nullable = true, length = 128)
    private String confirmationToken;
    @Basic
    @Column(name = "last_login", nullable = true)
    private LocalDateTime lastLogin;
    @Basic
    @Column(name = "last_login_ip", nullable = true, length = 256)
    private String lastLoginIp;
    @Basic
    @Column(name = "last_fail_login", nullable = true)
    private LocalDateTime lastFailLogin;
    @Basic
    @Column(name = "last_fail_login_ip", nullable = true, length = 256)
    private String lastFailLoginIp;
    @Basic
    @Column(name = "login_failures_since_last_login", nullable = true)
    private Integer loginFailuresSinceLastLogin = 0;
    @Basic
    @Column(name = "last_password_change_ip", nullable = true, length = 256)
    private String lastPasswordChangeIp;
    @Basic
    @Column(name = "last_password_change", nullable = true)
    private LocalDateTime lastPasswordChange;
    @Basic
    @Column(name = "password_verification_token", nullable = true, length = 128)
    private String passwordVerificationToken;
    @Basic
    @Column(name = "password_verification_token_expiration", nullable = true)
    private LocalDateTime passwordVerificationTokenExpiration;

    @OneToMany(mappedBy = "accountInfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccessLevelsEntity> accessLevels = new ArrayList<>();

    @OneToOne(optional = false, mappedBy = "accountInfoId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserInfoEntity userInfoEntity;

    @OneToMany(mappedBy = "accountInfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OauthAccountInfoEntity> oauthAccountInfoEntityList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public AccountInfoEntity setId(long id) {
        this.id = id;
        return this;
    }

    public AccountInfoEntity() {
        super();
    }

    public AccountInfoEntity(String email, String password, UserInfoEntity userInfoEntity) {
        this.email = email;
        this.password = password;
        this.userInfoEntity = userInfoEntity;
    }

    public String getEmail() {
        return email;
    }

    public AccountInfoEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountInfoEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AccountInfoEntity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public AccountInfoEntity setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public AccountInfoEntity setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
        return this;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public AccountInfoEntity setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public AccountInfoEntity setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public LocalDateTime getLastFailLogin() {
        return lastFailLogin;
    }

    public AccountInfoEntity setLastFailLogin(LocalDateTime lastFailLogin) {
        this.lastFailLogin = lastFailLogin;
        return this;
    }

    public String getLastFailLoginIp() {
        return lastFailLoginIp;
    }

    public AccountInfoEntity setLastFailLoginIp(String lastFailLoginIp) {
        this.lastFailLoginIp = lastFailLoginIp;
        return this;
    }

    public Integer getLoginFailuresSinceLastLogin() {
        return loginFailuresSinceLastLogin;
    }

    public AccountInfoEntity setLoginFailuresSinceLastLogin(Integer loginFailuresSinceLastLogin) {
        this.loginFailuresSinceLastLogin = loginFailuresSinceLastLogin;
        return this;
    }

    public String getLastPasswordChangeIp() {
        return lastPasswordChangeIp;
    }

    public AccountInfoEntity setLastPasswordChangeIp(String lastPasswordChangeIp) {
        this.lastPasswordChangeIp = lastPasswordChangeIp;
        return this;
    }

    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }

    public AccountInfoEntity setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
        return this;
    }

    public String getPasswordVerificationToken() {
        return passwordVerificationToken;
    }

    public AccountInfoEntity setPasswordVerificationToken(String passwordVerificationToken) {
        this.passwordVerificationToken = passwordVerificationToken;
        return this;
    }

    public LocalDateTime getPasswordVerificationTokenExpiration() {
        return passwordVerificationTokenExpiration;
    }

    public AccountInfoEntity setPasswordVerificationTokenExpiration(LocalDateTime passwordVerificationTokenExpiration) {
        this.passwordVerificationTokenExpiration = passwordVerificationTokenExpiration;
        return this;
    }

    public List<AccessLevelsEntity> getAccessLevels() {
        return accessLevels;
    }

    public String getAccessLevelsAsString(){
        return accessLevels.stream().map(AccessLevelsEntity::getLevel).toList().toString();
    }

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public AccountInfoEntity setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
        return this;
    }

    @Override
    public AccountInfoEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        super.setTableMetadata(tableMetadata);
        return this;
    }

    @Override
    public AccountInfoEntity setVersion(Long version) {
        super.setVersion(version);
        return this;
    }

    public List<OauthAccountInfoEntity> getOauthAccountInfoEntityList() {
        return oauthAccountInfoEntityList;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}