package kfq.springcoco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Member implements UserDetails {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Coco> cocoList;

    @OneToMany(mappedBy = "questionAuthor", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Question> questionList;

    @OneToMany(mappedBy = "answerAuthor", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Answer> answerList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Language> languageList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
