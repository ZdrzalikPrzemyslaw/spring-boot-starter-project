package tech.beetwin.template.model.UserInfo;

import tech.beetwin.template.model.AbstractEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.template.model.TableMetadata.TableMetadataEntity;

import javax.persistence.*;

@Entity
@Table(name = "user_info", schema = "public", uniqueConstraints = {
})
public class UserInfoEntity extends AbstractEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_sequence")
    @SequenceGenerator(name = "user_info_sequence", sequenceName = "user_info_sequence", allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @OneToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_info_id", referencedColumnName = "id", updatable = false, nullable = false)
    private AccountInfoEntity accountInfoId;
    @Basic
    @Column(name = "first_name", nullable = true, length = 64)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = true, length = 64)
    private String lastName;

    public Long getId() {
        return id;
    }

    public UserInfoEntity setId(long id) {
        this.id = id;
        return this;
    }

    public AccountInfoEntity getAccountInfoId() {
        return accountInfoId;
    }

    public UserInfoEntity setAccountInfoId(AccountInfoEntity accountInfoId) {
        this.accountInfoId = accountInfoId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInfoEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }


    @Override
    public UserInfoEntity setVersion(Long version) {
         super.setVersion(version);
         return this;
    }

    public UserInfoEntity() {
        super();
    }

    @Override
    public UserInfoEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        super.setTableMetadata(tableMetadata);
        return this;
    }

    public UserInfoEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
