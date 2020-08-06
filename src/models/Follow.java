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
            name = "getAllFollows",
            query = "SELECT f FROM Follow AS f ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getFollowsCount",
            query = "SELECT COUNT(f) FROM Follow AS f"
            )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower_id",nullable = false)
    private Employee follower_id;

    @ManyToOne
    @JoinColumn(name = "followed_id",nullable = false)
    private Employee followed_id;

    @Column(name = "created_at",nullable = false)
    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(Employee follower_id){
        this.follower_id = follower_id;
    }

    public Employee getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(Employee followed_id) {
        this.followed_id = followed_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

}
