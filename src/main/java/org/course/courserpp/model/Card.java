package org.course.courserpp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String number;
    private long balance;
    private String CVC;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "application_for_cash_score",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "application_for_cash_id")
    )
    private Set<ApplicationForCash> applicationForCashes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "replenishment_operation_score",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "replenishment_operation_id")
    )
    private Set<ReplenishmentOperation> replenishmentOperations = new HashSet<>();


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Card card = (Card) object;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
