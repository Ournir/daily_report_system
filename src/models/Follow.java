package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
            name = "getFollowsCount",
            query = "SELECT COUNT(f) FROM Follow AS f"
            ),
    @NamedQuery(
            name = "getAllFollowsReports",
            query = "SELECT f FROM Follow AS f WHERE f.follower.id = :employee"
            )
//    @NamedQuery(
//            name = "removeFollowData",
//            query = "DELETE f FROM Follow AS f WHERE f.follower.id = :employee AND f.followed.id = :uid"
//            )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower_id",nullable = false)
    private Employee follower;

    @ManyToOne
    @JoinColumn(name = "followed_id",nullable = false)
    private Employee followed;

    @Column(name = "created_at",nullable = false)
    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower){
        this.follower = follower;
    }

    public Employee getFollowed() {
        return followed;
    }

    public void setFollowed(Employee followed) {
        this.followed = followed;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }


}
