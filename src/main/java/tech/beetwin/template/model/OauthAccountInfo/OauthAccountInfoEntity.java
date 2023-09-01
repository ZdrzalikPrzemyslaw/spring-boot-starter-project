package tech.beetwin.template.model.OauthAccountInfo;

import tech.beetwin.template.model.AbstractEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;

import javax.persistence.*;

@Entity
@Table(name = "oauth_account_info", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider_user_id"}),
})
public class OauthAccountInfoEntity  extends AbstractEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth_account_info_sequence")
    @SequenceGenerator(name = "oauth_account_info_sequence", sequenceName = "oauth_account_info_sequence", allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "provider", nullable = true, length = 32)
    private String provider;
    @Basic
    @Column(name = "provider_user_id", nullable = true, length = 256)
    private String providerUserId;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_info_id", referencedColumnName = "id", updatable = false, nullable = false)
    private AccountInfoEntity accountInfoId;

    public OauthAccountInfoEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public AccountInfoEntity getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(AccountInfoEntity accountInfoId) {
        this.accountInfoId = accountInfoId;
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